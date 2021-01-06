package com.maimoona.firebase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        btn_SignIn.setOnClickListener {
            var signInIntent=Intent(this,LoginActivity::class.java)
            startActivity(signInIntent)
        }

        btn_SignUp.setOnClickListener {
            var signUpIntent=Intent(this,RegisterActivity::class.java)
            startActivity(signUpIntent)
        }

        btn_SignWithPhone.setOnClickListener {
            var intent=Intent(this,PhoneNumberLoginActivity::class.java)
            startActivity(intent)
        }


        btn_SignWithGoogle.setOnClickListener {

        }
    }
}