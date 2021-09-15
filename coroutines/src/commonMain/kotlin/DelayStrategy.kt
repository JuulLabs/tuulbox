import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transformLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.asTimeSource
import kotlin.math.E
import kotlin.math.pow
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

public interface DelayStrategy {
    public suspend fun await(iteration: Int, elapsedMillis: Long)
}

public class FixedDelay(private val delayMillis: Long) : DelayStrategy {
    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        delay(delayMillis - elapsedMillis)
    }
}

public class ExponentialBackoff(
    private val minimumMillis: Long,
    private val maximumMillis: Long
) : DelayStrategy {
    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        delay(calculateExponentialBackoffDelay(iteration, elapsedMillis, minimumMillis, maximumMillis))
    }
}

internal fun calculateExponentialBackoffDelay(
    iteration: Int,
    elapsedMillis: Long,
    minimumMillis: Long,
    maximumMillis: Long
) = (E.pow(iteration / E).toLong() * 1000L).coerceIn(minimumMillis..maximumMillis) - elapsedMillis

@OptIn(ExperimentalTime::class)
public class Dynamic<T> : DelayStrategy {

    private val trigger: Flow<T>
    private val selector: (T) -> DelayStrategy
    private val timeSource: TimeSource

    public constructor(
        trigger: Flow<T>,
        selector: (T) -> DelayStrategy
    ) {
        this.trigger = trigger
        this.selector = selector
        timeSource = Clock.System.asTimeSource()
    }

    internal constructor(
        trigger: Flow<T>,
        selector: (T) -> DelayStrategy,
        timeSource: TimeSource
    ) {
        this.trigger = trigger
        this.selector = selector
        this.timeSource = timeSource
    }

    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        val mark = timeSource.markNow()
        trigger.transformLatest {
            selector(it).await(iteration, mark.elapsedNow().inWholeMilliseconds)
            emit(Unit)
        }.first()
    }
}
