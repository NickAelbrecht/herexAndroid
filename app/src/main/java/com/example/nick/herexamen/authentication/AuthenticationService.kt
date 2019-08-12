package com.example.nick.herexamen.authentication

import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.nick.herexamen.MainActivity
import com.example.nick.herexamen.R
import com.example.nick.herexamen.fragments.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class AuthenticationService(private var activity: MainActivity) {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var TAG = "AuthenticationService"

    fun checkSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun createUser(
        naam: String,
        email: String,
        paswoord: String,
        confirmPaswoord: String,
        fragment: Fragment
    ): FirebaseUser? {
        var gebruiker: FirebaseUser? = null
        if (!validateForm(email, paswoord, confirmPaswoord, naam, 1)) {
            return null
        }
        auth.createUserWithEmailAndPassword(email, paswoord)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(naam)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }
                    activity.updateUi(user, fragment)
                    gebruiker = user
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this.activity.baseContext, "Authentication failed: ${task.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                    gebruiker = null
                }
            }

        return gebruiker
    }

    fun logUserIn(email: String, paswoord: String, fragment: Fragment) {
        if (!validateForm(email, paswoord, null, null, 2)) {
            return
        }
        auth.signInWithEmailAndPassword(email, paswoord)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    activity.updateUi(user, fragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this.activity.baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun logUserOut() {
        auth.signOut()
    }


    private fun validateForm(
        email: String,
        paswoord: String,
        confirmPaswoord: String?,
        naam: String?,
        case: Int
    ): Boolean {
        var valid = true
        val fieldEmail: TextView
        val fieldPassword: TextView
        var fieldConfirmPassword: TextView? = null
        var fieldNaam: TextView? = null


        if (case == 2) {
            fieldEmail = activity.findViewById<EditText>(R.id.login_email)!!
            fieldPassword = activity.findViewById<EditText>(R.id.login_paswoord)!!
            Log.d(TAG, "emailfield: $fieldEmail, paswfield:$fieldPassword")
        } else {
            fieldEmail = activity.findViewById<EditText>(R.id.register_email)!!
            fieldPassword = activity.findViewById<EditText>(R.id.register_password)
            fieldConfirmPassword = activity.findViewById<EditText>(R.id.register_password_confirm)
            fieldNaam = activity.findViewById<EditText>(R.id.register_naam)
            //Log.d(TAG, "emailfield: $fieldEmail, paswfield:$fieldPassword, confpasfield:$fieldConfirmPasword")

        }
        when {
            TextUtils.isEmpty(email) -> {
                fieldEmail.error = "Vereist."
                valid = false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                fieldEmail.error = "Geen geldig emailformaat."
                valid = false
            }

            else -> fieldEmail.error = null

        }

        if (TextUtils.isEmpty(paswoord)) {
            fieldPassword.error = "Vereist."
            valid = false
        } else {
            fieldPassword.error = null
        }

        if (TextUtils.isEmpty(naam)) {
            fieldNaam?.error = "Vereist."
            valid = false
        } else {
            fieldNaam?.error = null
        }

        if (confirmPaswoord != null) {
            when {
                TextUtils.isEmpty(confirmPaswoord) -> {
                    fieldConfirmPassword?.error = "Vereist."
                    valid = false
                }
                confirmPaswoord != paswoord -> {
                    fieldConfirmPassword?.error = "Beide wachtwoorden moeten dezelfde zijn."
                    valid = false
                }
                else -> fieldConfirmPassword?.error = null
            }
        }
        Log.d(TAG, "$valid")
        return valid
    }

    fun getAuth(): FirebaseAuth {
        return auth
    }

}