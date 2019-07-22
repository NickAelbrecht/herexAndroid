package com.example.nick.herexamen.authentication

import android.util.Log
import android.widget.Toast
import com.example.nick.herexamen.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthenticationService {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var activity: MainActivity
    var TAG = "AuthenticationService"

    constructor(activity: MainActivity) {
        this.activity = activity
    }

    fun checkSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun createUser(email: String, paswoord: String): FirebaseUser? {
        var gebruiker: FirebaseUser? = null
        auth.createUserWithEmailAndPassword(email, paswoord)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    gebruiker = user
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this.activity.baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        return gebruiker
    }

    fun logUserIn(email: String, paswoord: String): FirebaseUser? {
        var gebruiker: FirebaseUser? = null
        auth.signInWithEmailAndPassword(email, paswoord)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    gebruiker = user
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this.activity.baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        return gebruiker
    }
}