package fr.thomas.lefebvre.miammiam.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.service.UserHelper
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.dialog_delete_account.view.*
import kotlinx.android.synthetic.main.dialog_quitt.view.*

class AccountActivity : AppCompatActivity() {

    private val currentUser= FirebaseAuth.getInstance().currentUser

    private val userHelper=UserHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        getCurrentUser()

        clickDeleteButton()
    }



    /* ------------------------------
                                                UPDATE UI
                                                                                      ------------------------------------  */
    private fun getCurrentUser() {
        textView_Settings_Name.text = currentUser?.displayName//set the user name
        textView_Settings_Mail.text = currentUser?.email//set the user mail
        if (currentUser?.photoUrl != null) {
            Picasso.get().load(currentUser?.photoUrl).into(circleImageView_Setting)//set the user picture
        }

    }








    /* ------------------------------
                                            DELETE USER
                                                                                  ------------------------------------  */
    private fun deleteAccount() {
        if (currentUser != null) {
            userHelper.deleteUser(currentUser.uid)
                .addOnSuccessListener {

                    AuthUI.getInstance().delete(this)
                        .addOnCompleteListener {

                            val welcomeActivityIntent = Intent(this, LoginActivity::class.java)
                            startActivity(welcomeActivityIntent)
                            finish()

                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                            onBackPressed()

                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error delete account data base", Toast.LENGTH_LONG).show()
                }

        }
    }


    private fun alertDialogDeleteAccount() {

        val mDialog = LayoutInflater.from(this)
            .inflate(R.layout.dialog_delete_account, null)
        val mBuilder = android.app.AlertDialog.Builder(this)
            .setView(mDialog)
        val mAlertDialog = mBuilder.show()
        mDialog.buttonValidDelete.setOnClickListener {
            deleteAccount()
            mAlertDialog.dismiss()

        }
        mDialog.buttonCancelDelete.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }


    private fun clickDeleteButton() {
        imageButton_Delete_Account.setOnClickListener {
            alertDialogDeleteAccount()
        }
    }
}
