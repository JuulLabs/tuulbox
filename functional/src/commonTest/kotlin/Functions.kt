package com.juul.tuulbox.functional

// Functions in this file are written in lambda form to avoid :: all over the place when writing tests

val identity = { value: Any? -> value }

val increment = { value: Int -> value + 1 }

val double = { value: Int -> value * 2 }

val stringConcat2 = { first: String, second: String ->
    "$first$second"
}

val stringConcat3 = { first: String, second: String, third: String ->
    "$first$second$third"
}

val stringConcat4 = { first: String, second: String, third: String, fourth: String ->
    "$first$second$third$fourth"
}

val stringConcat5 = { first: String, second: String, third: String, fourth: String, fifth: String ->
    "$first$second$third$fourth$fifth"
}

val stringConcat6 = { first: String, second: String, third: String, fourth: String, fifth: String, sixth: String ->
    "$first$second$third$fourth$fifth$sixth"
}
