package com.juul.tuulbox.logging

internal expect val defaultTagGenerator: TagGenerator

/** Creates tag strings for implicitly-tagged [Log] calls. */
@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("TagGenerator", "com.juul.khronicle.TagGenerator"),
)
public interface TagGenerator {

    /** Return a tag to use when the [Log] call has no explicit tag. */
    public fun getTag(): String
}
