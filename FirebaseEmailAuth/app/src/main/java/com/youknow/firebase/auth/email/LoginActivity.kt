package com.youknow.firebase.auth.email

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignIn.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://youknow.page.link/sign-in")
            .setHandleCodeInApp(true)
            .setAndroidPackageName(
                "com.youknow.firebase.auth.email",
                true, /* installIfNotAvailable */
                "12" /* minimumVersion */
            )
            .build()

        FirebaseAuth.getInstance()
            .sendSignInLinkToEmail(etEmail.text.toString(), actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
    }
}