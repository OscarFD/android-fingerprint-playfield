package com.fanduel.sportsbook.biometricauth

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.P)
class BiometricCallBackV28(var onAuthenticate: (() -> Unit)? = null, var onAuthenticationError: (() -> Unit)? = null, var onAuthenticationHelp: (() -> Unit)? = null, var onAuthenticationFailed: (() -> Unit)? = null): BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        super.onAuthenticationError(errorCode, errString)
        println(" ==========================> onAuthenticationError")
        onAuthenticationError?.invoke()
    }

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        println(" ==========================> onAuthenticationSucceeded")
        onAuthenticate?.invoke()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpCode, helpString)
        println(" ==========================> onAuthenticationHelp")
        onAuthenticationHelp?.invoke()
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        println(" ==========================> onAuthenticationFailed")
        onAuthenticationFailed?.invoke()
    }
}