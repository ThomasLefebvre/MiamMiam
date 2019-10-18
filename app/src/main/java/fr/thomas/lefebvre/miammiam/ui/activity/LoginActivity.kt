package fr.thomas.lefebvre.miammiam.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.service.UserHelper

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 123
    lateinit var providers: List<AuthUI.IdpConfig>
    private val userHelper=UserHelper()


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

    private fun createUserToDataBaseIfNotExist(currentUser: FirebaseUser){
        userHelper.getUserById(currentUser.uid).addOnSuccessListener { documentSnapshot ->
            if(!documentSnapshot.exists()){
                userHelper.createUser(currentUser.uid,currentUser.displayName!!,currentUser.email!!,currentUser.photoUrl.toString())
            }
        }
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
