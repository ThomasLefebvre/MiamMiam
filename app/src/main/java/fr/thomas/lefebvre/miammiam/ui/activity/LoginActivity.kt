package fr.thomas.lefebvre.miammiam.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import fr.thomas.lefebvre.miammiam.R

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 123
    lateinit var providers: List<AuthUI.IdpConfig>
    private val DEBUG_TAG="DEBUG_LOGIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        showSignInOptions()

    }


    private fun showSignInOptions(){

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.AppSign)
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)//TODO DELETE FOR AUTOMATIC CHECK LOGIN
                .build(),
            RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                startActivity(Intent(this, SplashScreenActivity::class.java))
                finish()
                // ...
            } else {
                finish()
            }
        }
    }


}
