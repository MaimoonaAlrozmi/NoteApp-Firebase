package com.maimoona.firebase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            var userName = register_user_name.text.toString()
            var email = register_email.text.toString()
            var password = register_password.text.toString()
            var confirmPassword = register_confirm_password.text.toString()

            if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password.length > 6 && password.length < 14) {
                    if (password == confirmPassword) {
                        register(userName, email, password)
                    } else {
                        Toast.makeText(
                            this,
                            "password not equal confirmPassword",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Password length must be between 6 and 14 ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this, "some fields empty", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun register(userName: String, email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {

                if (it.isSuccessful) {
                    sendEmailVerification()
                } else {
                    Toast.makeText(this, " You registered Failed ${it.exception.toString()}", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {

            if (it.isSuccessful) {
                Toast.makeText(this, "You registered successful", Toast.LENGTH_LONG).show()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}