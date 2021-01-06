package com.maimoona.firebase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            var email = email_login.text.toString()
            var password = passWord_login.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun login(email: String, password: String) {
        mAuth = FirebaseAuth.getInstance()
        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) {

                if (it.isSuccessful) {
                    verifyEmailAddress()
                } else {
                    Toast.makeText(this, "Your login Failed ${it.exception.toString()}", Toast.LENGTH_LONG)
                        .show()
                }
            }

        txt_new_account.setOnClickListener {
            var intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        ly_phoneNumber.setOnClickListener{
            var intent=Intent(this,PhoneNumberLoginActivity::class.java)
            startActivity(intent)
        }
    }


    private fun verifyEmailAddress() {

        val user: FirebaseUser? = mAuth?.currentUser
        if(user!!.isEmailVerified){
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
            var intent = Intent(this, ShowDataActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }else{
            Toast.makeText(this, "Please verify your account", Toast.LENGTH_LONG).show()
        }
    }
}