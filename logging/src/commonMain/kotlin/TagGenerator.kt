package com.juul.tuulbox.logging

internal expect val defaultTagGenerator: TagGenerator

/** Creates tag strings for implicitly-tagged [Log] calls. */
public interface TagGenerator {

    /** Return a tag to use when the [Log] call has no explicit tag. */
    public fun getTag(): String
}
