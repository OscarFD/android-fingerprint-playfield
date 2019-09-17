@file:JvmName("Constants")

package com.fanduel.sportsbook.biometricauth


@JvmField val DEFAULT_KEY_NAME = "default_key"

/**
 * Enumeration to indicate which authentication method the user is trying to authenticate with.
 */
enum class Stage { FINGERPRINT, NEW_FINGERPRINT_ENROLLED, PASSWORD }
