package com.fanduel.sportsbook.biometricauth

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        button.setOnClickListener {

            val onCancelled =
                { -> println(" ==========================> onCancelled in MainActivity") }

            val onAunthenticated =
                { -> println(" ==========================> onAuthenticated in MainActivity") }

            val onRejected =
                { -> println(" ==========================> onAuthenticated in MainActivity") }

            val biometricManager = BiometricManager.Builder()
                .setContext(this.applicationContext)
                .setTitle("test")
                .setSubtitle("mmm subtitle")
                .setDescription("Awesome description")
                .setOnAuthenticated(onAunthenticated)
                .setOnCancel("no, thanks", onCancelled)
                .setOnRejected(onRejected)
                .build()

            //biometricManager.authenticate()

            when (biometricManager.authenticate()) {
                AuthenticationResult.PERMISSION_NOT_GRANTED -> {println(" ==========================> PERMISSION_NOT_GRANTED in MainActivity") }
                AuthenticationResult.BIOMETRIC_AUTH_NOT_AVAILABLE -> {println(" ==========================> BIOMETRIC_AUTH_NOT_AVAILABLE in MainActivity")}
                AuthenticationResult.BIOMETRIC_AUTH_NOT_SUPPORTED -> {println(" ==========================> BIOMETRIC_AUTH_NOT_SUPPORTED in MainActivity")}
                AuthenticationResult.UNKNOWN_REASON -> { println(" ==========================> UNKNOWN_REASON in MainActivity") }
                AuthenticationResult.SDK_VERSION_NOT_SUPPORTED -> { println(" ==========================> SDK_VERSION_NOT_SUPPORTED in MainActivity") }
            }
        }
    }
}
