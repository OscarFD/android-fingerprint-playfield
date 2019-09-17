package com.fanduel.sportsbook.biometricauth

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

class BiometricUtilsImp : BiometricUtils {

    override fun isBiometricPromptEnabled(): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
    }

    override fun isPermissionGranted(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.USE_FINGERPRINT
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun isHardwareSupported(context: Context): Boolean {
        val fingerprintManager = FingerprintManagerCompat.from(context)
        return fingerprintManager.isHardwareDetected
    }

    override fun isFingerprintAvailable(context: Context): Boolean {

        val fingerprintManager = FingerprintManagerCompat.from(context)
        return fingerprintManager.hasEnrolledFingerprints()
    }
}