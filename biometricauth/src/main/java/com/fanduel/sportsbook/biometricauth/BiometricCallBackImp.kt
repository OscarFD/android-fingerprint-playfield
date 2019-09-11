package com.fanduel.sportsbook.biometricauth

class BiometricCallBackImp(var onError: (() -> Map<Int, String?>)): BiometricCallBack {

    override fun onSdkVersionNotSupported() {
        System.err.println("Error onSdkVersionNotSupported!!")
    }

    override fun onBiometricAuthenticationInternalError(error: String?) {
        println(" ==========================> Error onBiometricAuthenticationInternalError!!")
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        println(" ==========================> Error onBiometricAuthenticationPermissionNotGranted!!")
    }

    override fun onBiometricAuthenticationNotSupported() {
        println(" ==========================> Error onBiometricAuthenticationNotSupported!!")
    }

    override fun onBiometricAuthenticationNotAvailable() {
        println(" ==========================> Error onBiometricAuthenticationNotAvailable!!")
    }

    override fun onAuthenticationCancelled() {
        println(" ==========================> onAuthenticationCancelled")
    }
}