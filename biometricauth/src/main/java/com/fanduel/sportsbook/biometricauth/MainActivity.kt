package com.fanduel.sportsbook.biometricauth

import android.content.SharedPreferences
import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class MainActivity : AppCompatActivity() {

    lateinit var button: Button

    private lateinit var keyGenerator: KeyGenerator
    private lateinit var keyStore: KeyStore
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        button.setOnClickListener {
            //BiometricFDManager().biometricAuthAvailable()

            /*
                Example of use in device > Android M < Android P
             */

//            val fragment = FingerprintAuthenticationDialogFragment()
//            fragment.setCryptoObject(FingerprintManager.CryptoObject(defaultCipher))
//            fragment.setCallback(BiometricCallbackV23())
//
//            // Set up the crypto object for later, which will be authenticated by fingerprint usage.
//            if (initCipher(defaultCipher,  DEFAULT_KEY_NAME)) {
//
//                // Show the fingerprint dialog. The user has the option to use the fingerprint with
//                // crypto, or can fall back to using a server-side verified password.
//
//            }
//            fragment.show(fragmentManager, DIALOG_FRAGMENT_TAG)

            /*
                END - Example of use in device > Android M < Android P
             */

            /*
                Example of use in Android P
             */
//
            val onError =
                { -> println(" ==========================> onCancelled in MainActivity") }

            val onAunthenticated =
                { -> println(" ==========================> onAuthenticated in MainActivity") }

            val onRejected =
                { -> println(" ==========================> onAuthenticated in MainActivity") }

            val biometricManager = BiometricFDManager.Builder()
                .setContext(this.applicationContext)
                .setTitle("test")
                .setSubtitle("mmm subtitle")
                .setDescription("Awesome description")
                .setOnAuthenticated(onAunthenticated)
                .setOnError(onError)
                .setOnRejected(onRejected)
                .build()
//
            biometricManager.authenticate()
//
//            when (biometricManager.authenticate()) {
//                AuthenticationResult.PERMISSION_NOT_GRANTED -> {println(" ==========================> PERMISSION_NOT_GRANTED in MainActivity") }
//                AuthenticationResult.BIOMETRIC_AUTH_NOT_AVAILABLE -> {println(" ==========================> BIOMETRIC_AUTH_NOT_AVAILABLE in MainActivity")}
//                AuthenticationResult.BIOMETRIC_AUTH_NOT_SUPPORTED -> {println(" ==========================> BIOMETRIC_AUTH_NOT_SUPPORTED in MainActivity")}
//                AuthenticationResult.UNKNOWN_REASON -> { println(" ==========================> UNKNOWN_REASON in MainActivity") }
//                AuthenticationResult.SDK_VERSION_NOT_SUPPORTED -> { println(" ==========================> SDK_VERSION_NOT_SUPPORTED in MainActivity") }
//            }

        }
    }
}
