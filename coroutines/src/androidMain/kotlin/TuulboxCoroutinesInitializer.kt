package com.juul.tuulbox.coroutines

import android.content.Context
import androidx.startup.Initializer

internal lateinit var applicationContext: Context
    private set

public object TuulboxCoroutines

public class TuulboxCoroutinesInitializer : Initializer<TuulboxCoroutines> {
    override fun create(context: Context): TuulboxCoroutines {
        applicationContext = context
        return TuulboxCoroutines
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
