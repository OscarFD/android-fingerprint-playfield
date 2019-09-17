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

class MainActivity : AppCompatActivity(), FingerprintAuthenticationDialogFragment.Callback {

    lateinit var button: Button

    private lateinit var keyGenerator: KeyGenerator
    private lateinit var keyStore: KeyStore
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupKeyStoreAndKeyGenerator()
        val (defaultCipher: Cipher, cipherNotInvalidated: Cipher) = setupCiphers()


        button = findViewById(R.id.button)
//
        createKey(DEFAULT_KEY_NAME)
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
            val onCancelled =
                { -> println(" ==========================> onCancelled in MainActivity") }

            val onAunthenticated =
                { -> println(" ==========================> onAuthenticated in MainActivity") }

            val onRejected =
                { -> println(" ==========================> onAuthenticated in MainActivity") }

            val biometricManager = BiometricFDManager.Builder()
                .setContext(this.applicationContext)
                .setFragmentActivity(getSupportFragmentManager().getFragment(Bundle(), ""))
                .setTitle("test")
                .setSubtitle("mmm subtitle")
                .setDescription("Awesome description")
                .setOnAuthenticated(onAunthenticated)
                .setOnCancel("no, thanks", onCancelled)
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


    private fun setupCiphers(): Pair<Cipher, Cipher> {
        val defaultCipher: Cipher
        val cipherNotInvalidated: Cipher
        try {
            val cipherString =
                "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"
            defaultCipher = Cipher.getInstance(cipherString)
            cipherNotInvalidated = Cipher.getInstance(cipherString)
        } catch (e: Exception) {
            when (e) {
                is NoSuchAlgorithmException,
                is NoSuchPaddingException ->
                    throw RuntimeException("Failed to get an instance of Cipher", e)
                else -> throw e
            }
        }
        return Pair(defaultCipher, cipherNotInvalidated)
    }

    private fun initCipher(cipher: Cipher, keyName: String): Boolean {
        try {
            keyStore.load(null)
            cipher.init(Cipher.ENCRYPT_MODE, keyStore.getKey(keyName, null) as SecretKey)
            return true
        } catch (e: Exception) {
            when (e) {
                is KeyPermanentlyInvalidatedException -> return false
                is KeyStoreException,
                is CertificateException,
                is UnrecoverableKeyException,
                is IOException,
                is NoSuchAlgorithmException,
                is InvalidKeyException -> throw RuntimeException("Failed to init Cipher", e)
                else -> throw e
            }
        }
    }

    private fun setupKeyStoreAndKeyGenerator() {
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to get an instance of KeyStore", e)
        }

        try {
            keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        } catch (e: Exception) {
            when (e) {
                is NoSuchAlgorithmException,
                is NoSuchProviderException ->
                    throw RuntimeException("Failed to get an instance of KeyGenerator", e)
                else -> throw e
            }
        }
    }

    companion object {
        private val ANDROID_KEY_STORE = "AndroidKeyStore"
        private val DIALOG_FRAGMENT_TAG = "myFragment"
        private val KEY_NAME_NOT_INVALIDATED = "key_not_invalidated"
        private val SECRET_MESSAGE = "Very secret message"
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onPurchased(withFingerprint: Boolean, crypto: FingerprintManager.CryptoObject?) {
        if (withFingerprint) {
            // If the user authenticated with fingerprint, verify using cryptography and then show
            // the confirmation message.
            if (crypto != null) {
                //tryEncrypt(crypto.cipher)
            }
        }
    }

    override fun createKey(keyName: String, invalidatedByBiometricEnrollment: Boolean) {
        try {
            keyStore.load(null)

            val keyProperties = KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            val builder = KeyGenParameterSpec.Builder(keyName, keyProperties)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment)

            keyGenerator.run {
                init(builder.build())
                generateKey()
            }
        } catch (e: Exception) {
            when (e) {
                is NoSuchAlgorithmException,
                is InvalidAlgorithmParameterException,
                is CertificateException,
                is IOException -> throw RuntimeException(e)
                else -> throw e
            }
        }
    }


}
