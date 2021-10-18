package com.example.blooddonationapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blooddonationapp.R
import com.example.blooddonationapp.utils.FirebaseAuthSingleton
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    var new_user: TextView? = null
    var Done_login: Button? = null
    var db = FirebaseFirestore.getInstance()
    var email: EditText? = null
    var password: EditText? = null
    private val mAuth = FirebaseAuthSingleton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        new_user = findViewById(R.id.new_user)
        Done_login = findViewById(R.id.Done_login)
        email = findViewById(R.id.TextEmail)
        password = findViewById(R.id.set_password)
        Done_login?.setOnClickListener(View.OnClickListener { v ->
            if (email?.getText().toString().isEmpty() || password?.getText().toString().isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Please Enter Email And Password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                tryLogin(v)
            }
        })
        new_user?.setOnClickListener(View.OnClickListener {
            val `in` = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(`in`)
        })
    }

    private fun tryLogin(v: View) {
        mAuth.instance!!.signInWithEmailAndPassword(
            email!!.text.toString(),
            password!!.text.toString()
        )
            .addOnCompleteListener((v.context as Activity)) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    checkUserType()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        v.context, "Authentication failed." + task.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun checkUserType() {
        val docRef = db.collection("users").document(
            mAuth.instance!!.uid.toString()
        )
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    if (document["user_type"].toString() == "user") {
                        val `in` = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(`in`)
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "User type not supported",
                            Toast.LENGTH_SHORT
                        ).show()
                        mAuth.instance!!.signOut()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "User type not supported",
                        Toast.LENGTH_SHORT
                    ).show()
                    mAuth.instance!!.signOut()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please try Again" + task.exception,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        finish()
        System.runFinalizersOnExit(true)
    }
}