package com.fanduel.sportsbook.biometricauth



import android.content.Context
import android.content.SharedPreferences
import android.hardware.fingerprint.FingerprintManager

import android.os.Bundle

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.DialogFragment

/**
 * A dialog which uses fingerprint APIs to authenticate the user, and falls back to password
 * authentication if fingerprint is not available.
 */
class FingerprintAuthenticationDialogFragment : DialogFragment(),
    TextView.OnEditorActionListener,
    FingerprintUiHelper.Callback {

    private lateinit var cancelButton: Button
    private lateinit var fingerprintContainer: View
    private lateinit var callback: Callback
    private lateinit var cryptoObject: FingerprintManager.CryptoObject
    private lateinit var fingerprintUiHelper: FingerprintUiHelper
    private lateinit var inputMethodManager: InputMethodManager


    private var stage = Stage.FINGERPRINT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        retainInstance = true
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setTitle(getString(R.string.sign_in))
        return inflater.inflate(R.layout.fingerprint_dialog_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButton = view.findViewById(R.id.cancel_button)
        fingerprintContainer = view.findViewById(R.id.fingerprint_container)
        cancelButton.setOnClickListener { dismiss() }

       //fingerprintUiHelper = FingerprintUiHelper(
            //activity.getSystemService(FingerprintManager::class.java),
           // view.findViewById(R.id.fingerprint_icon),
           // view.findViewById(R.id.fingerprint_status),
           // this
       // )


        // If fingerprint authentication is not available, switch immediately to the backup
        // (password) screen.
        if (!fingerprintUiHelper.isFingerprintAuthAvailable) {
            // goToBackup()
        }
    }

    override fun onResume() {
        super.onResume()
        if (stage == Stage.FINGERPRINT) {
            fingerprintUiHelper.startListening(cryptoObject)
        }
    }

    override fun onPause() {
        super.onPause()
        fingerprintUiHelper.stopListening()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inputMethodManager = context.getSystemService(InputMethodManager::class.java)

    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun setCryptoObject(cryptoObject: FingerprintManager.CryptoObject) {
        this.cryptoObject = cryptoObject
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
        return false
    }

    override fun onAuthenticated() {
        // Callback from FingerprintUiHelper. Let the activity know that authentication succeeded.
        callback.onPurchased(withFingerprint = true, crypto = cryptoObject)
        dismiss()
    }

    override fun onError() {

    }

    interface Callback {
        fun onPurchased(withFingerprint: Boolean, crypto: FingerprintManager.CryptoObject? = null)
        fun createKey(keyName: String, invalidatedByBiometricEnrollment: Boolean = true)
    }
}
