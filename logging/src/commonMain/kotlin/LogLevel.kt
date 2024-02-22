package com.juul.tuulbox.logging

@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("LogLevel", "com.juul.khronicle.LogLevel"),
)
public enum class LogLevel {
    Verbose,
    Debug,
    Info,
    Warn,
    Error,
    Assert,
}
