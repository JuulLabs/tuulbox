package com.juul.tuulbox.logging

/**
 * Marker interface to skip a class in the stack trace for the default [TagGenerator] on JVM. On
 * other targets, this interface does nothing.
 */
public interface HideFromStackTraceTag
