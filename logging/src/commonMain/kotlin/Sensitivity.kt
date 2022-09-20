package com.juul.tuulbox.logging

/**
 * Standardized metadata for data sensitivity levels.
 *
 * - [NotSensitive]: Marks a log as definitely free of PII or other sensitive data.
 * - [Sensitive]: Marks a log as definitely containing PII or other sensitive data.
 */
public enum class Sensitivity {
    NotSensitive,
    Sensitive,
    ;

    public companion object : Key<Sensitivity>
}
