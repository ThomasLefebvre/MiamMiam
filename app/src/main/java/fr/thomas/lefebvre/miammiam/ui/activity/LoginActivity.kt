package fr.thomas.lefebvre.miammiam.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
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
                .setLogo(R.drawable.ic_tomato)
                .setAvailableProviders(providers)
//                .setIsSmartLockEnabled(false)//TODO DELETE FOR AUTOMATIC CHECK LOGIN
                .build(),
            RC_SIGN_IN)

    }

    // --- CREATE USER TO DATABASE IF NOT ALREADY EXIST ---
    private fun createUserToDataBaseIfNotExist(currentUser: FirebaseUser?){
        if (currentUser != null) {
            userHelper.getUserById(currentUser.uid).addOnSuccessListener { documentSnapshot ->
                if(!documentSnapshot.exists()){
                    userHelper.createUser(currentUser.uid,currentUser.displayName!!,currentUser.email!!,currentUser.photoUrl.toString())
                }
            }
        }
    }

    // --- ACTIVITY RESULT ---
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {//RESULT OF FIREBASE UI AUTH WITH GOOGLE OR FACEBOOK

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                createUserToDataBaseIfNotExist(FirebaseAuth.getInstance().currentUser)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                // ...
            } else {
                finish()
            }
        }
    }


}
