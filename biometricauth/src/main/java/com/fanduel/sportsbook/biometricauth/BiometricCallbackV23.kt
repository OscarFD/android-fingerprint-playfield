package com.fanduel.sportsbook.biometricauth

import android.hardware.fingerprint.FingerprintManager
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import javax.crypto.KeyGenerator

class BiometricCallbackV23: FingerprintAuthenticationDialogFragment.Callback  {

    private lateinit var keyGenerator: KeyGenerator
    private lateinit var keyStore: KeyStore

    override fun onPurchased(withFingerprint: Boolean, crypto: FingerprintManager.CryptoObject?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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