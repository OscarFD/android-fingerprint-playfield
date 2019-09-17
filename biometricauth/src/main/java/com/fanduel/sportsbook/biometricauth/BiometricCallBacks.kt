package com.fanduel.sportsbook.biometricauth

import androidx.biometric.BiometricPrompt


class BiometricCallBacks(var onAuthenticate: (() -> Unit)? = null, var onAuthenticationError: (() -> Unit)? = null, var onAuthenticationFailed: (() -> Unit)? = null): BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        super.onAuthenticationError(errorCode, errString)
        onAuthenticationError?.invoke()
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        super.onAuthenticationSucceeded(result)
        onAuthenticate?.invoke()
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        println(" ==========================> onAuthenticationFailed")
        onAuthenticationFailed?.invoke()
    }
}