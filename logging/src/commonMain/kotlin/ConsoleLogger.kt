package com.juul.tuulbox.logging

/** Logger for the console. */
@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("ConsoleLogger", "com.juul.khronicle.ConsoleLogger"),
)
public expect object ConsoleLogger : Logger
