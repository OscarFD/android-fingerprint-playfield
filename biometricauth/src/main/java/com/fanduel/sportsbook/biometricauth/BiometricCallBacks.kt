package com.fanduel.sportsbook.biometricauth

import androidx.biometric.BiometricPrompt


class BiometricCallBacks(var onAuthenticate: (() -> Unit)? = null, var onAuthenticationError: (() -> Map<Int, CharSequence>), var onAuthenticationFailed: (() -> Unit)? = null): BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        // TODO 1 - tengo que hacer esto visible/publico de tal manera que se pueda ejecutar desde cualquier sitio
        super.onAuthenticationError(errorCode, errString)
        val error = mutableMapOf<Int, CharSequence>()
        error.put(errorCode, errString)
        onAuthenticationError.invoke()
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