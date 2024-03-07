package com.juul.tuulbox.logging

/**
 * Marker interface to skip a class in the stack trace for the default [TagGenerator] on JVM. On
 * other targets, this interface does nothing.
 */
@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("HideFromStackTraceTag", "com.juul.khronicle.HideFromStackTraceTag"),
)
public interface HideFromStackTraceTag
