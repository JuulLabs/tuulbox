package com.juul.tuulbox.logging

import kotlin.native.concurrent.SharedImmutable

@SharedImmutable
internal actual val defaultTagGenerator: TagGenerator = ConstantTagGenerator("Unknown")
