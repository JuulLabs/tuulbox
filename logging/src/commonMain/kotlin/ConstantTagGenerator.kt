package com.juul.tuulbox.logging

/** Constant tag generator, for when performance is more important than useful implicit tags. */
@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("ConstantTagGenerator", "com.juul.khronicle.ConstantTagGenerator"),
)
public class ConstantTagGenerator(private val tag: String) : TagGenerator {
    override fun getTag(): String = tag
}
