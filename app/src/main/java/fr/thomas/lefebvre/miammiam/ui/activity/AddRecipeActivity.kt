package fr.thomas.lefebvre.miammiam.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.model.RecipeModel
import fr.thomas.lefebvre.miammiam.service.RecipeHelper
import fr.thomas.lefebvre.miammiam.ui.adapter.AddIngredientOrStepAdapter
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.dialog_ingredient.view.*
import kotlinx.android.synthetic.main.dialog_step.view.*
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.Bitmap
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.io.ByteArrayOutputStream


class AddRecipeActivity : AppCompatActivity() {

    val listIngredient = ArrayList<String>()
    val listStep = ArrayList<String>()
    lateinit var ingredientAddAdapter: AddIngredientOrStepAdapter
    lateinit var stepAddAdapter: AddIngredientOrStepAdapter

    private val currentUser = FirebaseAuth.getInstance().currentUser

    companion object {
        const val CODE_CHOOSE_PHOTO = 100
    }

    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var downloadUri: Uri? = null

    private val recipeHelper = RecipeHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        setRecyclerViewIngredient()
        setRecyclerViewStep()

        onClickAddIngredient()
        onClickAddStep()
        onClickAddPhoto()
        onClickSaveRecipe()



        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
    }


    // -----                Recycler View            ---------------

    private fun setRecyclerViewIngredient() {
        val linearLayoutManager = LinearLayoutManager(this)
        ingredientAddAdapter = AddIngredientOrStepAdapter(this, listIngredient) { position: Int ->
            onClickSuppressIngredient(position)
        }
        recyclerViewAddIngredient.layoutManager = linearLayoutManager
        recyclerViewAddIngredient.adapter = ingredientAddAdapter
    }

    private fun onClickSuppressIngredient(position: Int) {
        listIngredient.removeAt(position)//remove item at list
        ingredientAddAdapter.notifyItemRemoved(position)//notify adapter of remove

    }


    private fun setRecyclerViewStep() {
        val linearLayoutManager = LinearLayoutManager(this)
        stepAddAdapter = AddIngredientOrStepAdapter(this, listStep) { position: Int ->
            onClickSuppressStep(position)
        }
        recyclerViewAddStep.layoutManager = linearLayoutManager
        recyclerViewAddStep.adapter = stepAddAdapter
    }

    private fun onClickSuppressStep(position: Int) {
        listStep.removeAt(position)//remove item at list
        stepAddAdapter.notifyItemRemoved(position)//notify adapter of remove

    }


    // -----                Alert dialog            ---------------

    private fun alertDialogIngredients() {
        val mDialog = LayoutInflater.from(this)
            .inflate(R.layout.dialog_ingredient, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialog)
        val mAlertDialog = mBuilder.show()
        mDialog.buttonValidIngredient.setOnClickListener {

            listIngredient.add(mDialog.text_input_dialog_ingredient.text.toString())
            ingredientAddAdapter.notifyDataSetChanged()

            mAlertDialog.dismiss()
        }
        mDialog.buttonCancelIngredient.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun alertDialogStep() {
        val mDialog = LayoutInflater.from(this)
            .inflate(R.layout.dialog_step, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialog)
        val mAlertDialog = mBuilder.show()
        mDialog.buttonValid.setOnClickListener {

            listStep.add(mDialog.text_input_dialog.text.toString())
            stepAddAdapter.notifyDataSetChanged()

            mAlertDialog.dismiss()
        }
        mDialog.buttonCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    //  ---------           On click                 --------------

    private fun onClickAddIngredient() {
        buttonAddIngredient.setOnClickListener {
            alertDialogIngredients()
        }
    }

    private fun onClickAddStep() {
        buttonAddStep.setOnClickListener {
            alertDialogStep()
        }
    }

    private fun onClickAddPhoto() {
        buttonAddPhoto.setOnClickListener {
            pickPhoto()
        }
    }

    private fun onClickSaveRecipe() {
        buttonSaveOnline.setOnClickListener {
            uploadPhoto()
        }
    }

    //  ---------           Activity result                --------------

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CODE_CHOOSE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    imageViewAddRecipe.setImageURI(data!!.data)









                    filePath = data.data
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //  ---------           Pick photo Intent                --------------

    private fun pickPhoto() {
        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(
            intentGallery,
            CODE_CHOOSE_PHOTO
        )
    }

    //  ---------           SAVE DATA FIRESTORE                --------------

    private fun uploadPhoto() {
        if (filePath != null) {

            val ref = storageReference!!.child("uploads/" + UUID.randomUUID().toString())

            // method for resize image before upload
            val bmp = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            val baos = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)

            val data = baos.toByteArray()
            val uploadTask = ref?.putBytes(data!!)


            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    downloadUri = task.result
                    Log.d("DEBUG", downloadUri.toString())
                    saveRecipeOnFireStore()
                } else {
                    // Handle failures
                    // ...
                }
            }
        } else {
            Toast.makeText(this, "Merci de choisir une photo", Toast.LENGTH_SHORT)
                .show()//TODO in string
        }
    }

    private fun saveRecipeOnFireStore() {
        recipeHelper.createRecipeOnFireStore(setRecipe())

    }

    // ------------------ SET RECIPE FOR SAVE -----------------

    private fun setRecipe(): RecipeModel {
        val recipe: RecipeModel = RecipeModel(
            nameAddRecipe.text.toString() + " - Chef ${currentUser!!.displayName}",
            "",
            nameAddRecipe.text.toString() + " - Chef ${currentUser!!.displayName}",
            listStep,
            downloadUri.toString(),
            listIngredient,
            quantity_add.text.toString(),
            "",
            "",
            time_add_recipe.text.toString()
        )
        return recipe
    }


}
