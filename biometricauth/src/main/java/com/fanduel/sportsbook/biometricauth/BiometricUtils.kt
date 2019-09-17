package com.fanduel.sportsbook.biometricauth

import android.content.Context

interface BiometricUtils {

    fun isBiometricPromptEnabled(): Boolean

    fun isPermissionGranted(context: Context): Boolean

    fun isHardwareSupported(context: Context): Boolean

    fun isFingerprintAvailable(context: Context): Boolean

    fun isFaceIDAvailable(context: Context): Boolean


}