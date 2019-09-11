package com.fanduel.sportsbook.biometricauth

interface BiometricCallBack {

    fun onSdkVersionNotSupported()

    fun onBiometricAuthenticationInternalError(error: String?)

    fun onBiometricAuthenticationPermissionNotGranted()

    fun onBiometricAuthenticationNotSupported()

    fun onBiometricAuthenticationNotAvailable()

    fun onAuthenticationCancelled()

}