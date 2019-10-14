package fr.thomas.lefebvre.miammiam.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import fr.thomas.lefebvre.miammiam.R

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000
    lateinit var animationRotate: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(
            {

                startActivity(Intent(this, MainActivity::class.java))//start main activity
                finish()//finish splace screen
            }, SPLASH_TIME_OUT//time wait
        )
    }
}
//TODO ANIMATION