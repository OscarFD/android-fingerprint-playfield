package com.fanduel.sportsbook.biometricauth

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.CancellationSignal
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


data class CancelFingerPrintPrompt(var text: String? = null, var action: (() -> Unit)? = null)

enum class AuthenticationResult {
    UNKNOWN_REASON, // Error on building the prompt/dialog
    SDK_VERSION_NOT_SUPPORTED, // Error on Sdk Version Not Supported
    PERMISSION_NOT_GRANTED, // Erro permissions not granted
    BIOMETRIC_AUTH_NOT_SUPPORTED, // Error Biometric Authentication Not Supported
    BIOMETRIC_AUTH_NOT_AVAILABLE, // Error  Biometric Authentication Not Available
    AUTH_PROMPT_SUCCESS // Authentication prompt successfully presented
}

class BiometricFDManager(
    private val context: Context?,
    private val title: String?,
    private val subtitle: String?,
    private val description: String?,
    private val onAuthenticated: (() -> Unit)?,
    private val onCancel: CancelFingerPrintPrompt?,
    private val onRejected: (() -> Unit)?,
    private val fragment: Fragment?
) {

    private val biometricUtils = BiometricUtilsImp()
    private val cancellationSignal = CancellationSignal()
    private val runnable = object : Runnable {

        override fun run() {}
    }


    data class Builder(
        private var context: Context? = null,
        private var title: String? = null,
        private var subtitle: String? = null,
        private var description: String? = null,
        private var onAuthenticated: (() -> Unit)? = null,
        private var onCancel: CancelFingerPrintPrompt? = null,
        private var onRejected: (() -> Unit)? = null,
        private var fragment: Fragment? = null
    ) {

        fun setContext(context: Context) = apply { this.context = context }
        fun setTitle(title: String) = apply { this.title = title }
        fun setSubtitle(subtitle: String) = apply { this.subtitle = subtitle }
        fun setDescription(description: String) = apply { this.description = description }
        fun setOnCancel(text: String, callback: (() -> Unit)?) = apply {
            this.onCancel = CancelFingerPrintPrompt(text = text, action = callback)
        }

        fun setOnAuthenticated(callback: (() -> Unit)?) = apply {
            this.onAuthenticated = callback
        }

        fun setOnRejected(callback: (() -> Unit)?) = apply {
            this.onRejected = callback
        }

        fun setFragmentActivity(fragment: Fragment?) = apply { this.fragment = fragment }

        fun build() =
            BiometricFDManager(
                context,
                title,
                subtitle,
                description,
                onAuthenticated,
                onCancel,
                onRejected,
                fragment
            )


    }

    fun biometricAuthAvailable(): Boolean {
        return biometricUtils.isBiometricPromptEnabled()
    }

    //TODO - Tests needed
    fun authenticate(): AuthenticationResult {
        if (context == null || title == null || onCancel?.text == null) {
            println("Biometric Dialog prompt building error")
            return AuthenticationResult.UNKNOWN_REASON
        }

        if (!biometricUtils.isBiometricPromptEnabled()) {
            println("Error onBiometricAuthentication: SDK version not supported")
            return AuthenticationResult.SDK_VERSION_NOT_SUPPORTED
        }

        if (!biometricUtils.isPermissionGranted(context)) {
            println("Error onBiometricAuthentication: PermissionNotGranted")
            return AuthenticationResult.PERMISSION_NOT_GRANTED
        }

        if (!biometricUtils.isHardwareSupported(context)) {
            println("Error onBiometricAuthentication: Biometric Auth not supported")
            return AuthenticationResult.BIOMETRIC_AUTH_NOT_SUPPORTED
        }

        if (!biometricUtils.isFingerprintAvailable(context)) {
            println("Error onBiometricAuthentication: Biometric Auth not available")
            return AuthenticationResult.BIOMETRIC_AUTH_NOT_AVAILABLE
        }

//        if (biometricUtils.isBiometricPromptEnabled()) {
//            displayBiometricPromptV28()
//        } else {
//            displayBiometricPromptV23()
//        }

        displayBiometricPrompt()
        return AuthenticationResult.AUTH_PROMPT_SUCCESS
    }

    fun displayBiometricPrompt() {

//         val test = BiometricPrompt(fragment, BiometricCallBacks(onAuthenticated, onRejected, onRejected))
//
//            val prompt = test.PromptInfo.Builder()
//            .setTitle(title.toString())
//            .setSubtitle(subtitle.toString())
//            .setDescription(description.toString())
//            .setConfirmationRequired(true)
//            .setDeviceCredentialAllowed(true)
//            .setNegativeButtonText(
//                onCancel?.text.toString()
//            )
//            .build()
//
//       test.authenticate(prompt)
    }

    @TargetApi(Build.VERSION_CODES.P)
    fun displayBiometricPromptV28() {

        // BiometricPrompt(fragment, onAuthenticated?.invoke(), BiometricPromptV28Imp())




//        BiometricPrompt.Builder(context)
//            .setTitle(title.toString())
//            .setSubtitle(subtitle.toString())
//            .setDescription(description.toString())
//            .setNegativeButton(
//                onCancel?.text.toString(),
//                context!!.mainExecutor,
//                DialogInterface.OnClickListener { dialogInterface, i -> onCancel?.action?.invoke() }
//            )
//            .build()
//            .authenticate(
//                cancellationSignal,
//                context.mainExecutor,
//                BiometricCallBacks(
//                    onAuthenticate = onAuthenticated,
//                    onAuthenticationFailed = onRejected
//                )
//            )
    }

    fun displayBiometricPromptV23() {}

}