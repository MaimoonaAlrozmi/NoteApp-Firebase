package com.maimoona.firebase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        var mAuth: FirebaseAuth? = null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mAuth = FirebaseAuth.getInstance()


        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed(
            {
                if (mAuth?.currentUser != null) {
                    var intentToAuth = Intent(this, ShowDataActivity::class.java)
                    startActivity(intentToAuth)
                    finish()
                } else {
                    val i = Intent(this@SplashScreen, AuthenticationActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }, 3000
        ) // 3000 is the delayed time in milliseconds.
    }

    override fun onStart() {
        super.onStart()


    }
}