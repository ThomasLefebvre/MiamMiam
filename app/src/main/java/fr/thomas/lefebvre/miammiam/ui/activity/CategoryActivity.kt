package fr.thomas.lefebvre.miammiam.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.model.RecipeModel
import fr.thomas.lefebvre.miammiam.service.RecipeHelper
import fr.thomas.lefebvre.miammiam.ui.adapter.BooksAdapter
import fr.thomas.lefebvre.miammiam.ui.adapter.RecipeAdapter
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_book.*
import kotlinx.android.synthetic.main.fragment_recipes.*

class CategoryActivity : AppCompatActivity() {

    lateinit var mAdapter: RecipeAdapter// adapter recycler view

    private val recipeHelper = RecipeHelper()

    var listRecipe: ArrayList<String> = arrayListOf()
    var listPhotoUrl: ArrayList<String> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val category: String? = intent.getStringExtra("category")
        Log.d("DEBUG", category!!)

        getRecipes(category)
        loadPub()
        onClickAddRecipe()


    }


    // --------- GET  RECIPES FROM FIREBASE IN TERMS OF CATEGORY -----

    private fun getRecipes(category: String) {
        recipeHelper.getRecipeByCategory(category).addOnSuccessListener { documents ->
            if (documents.documents.isEmpty()) {
                Log.d("DEBUG", "No results")
            } else {
                for (document in documents) {

                    Log.d("DEBUG", "${document.id} => ${document.data}")
                    val recipe = document.toObject(RecipeModel::class.java)
                    listRecipe.add(recipe.uid)
                    listPhotoUrl.add(recipe.photoUrl)

                }
                setRecyclerView()
            }


        }
            .addOnFailureListener {
                Log.d("DEBUG", "Error get recipes")
            }
    }


    // --- SET RECYCLER VIEW ---

    private fun setRecyclerView() {


           val layoutManager = GridLayoutManager(this, 2)
           val adapter = BooksAdapter(this, listRecipe, listPhotoUrl) { recipe: String ->
                recipeClick(recipe)
            }
        recyclerViewCategory.adapter=adapter
        recyclerViewCategory.layoutManager=layoutManager

    }

    // SET CLICK LISTENER OF RECYCLER VIEW ---

    private fun recipeClick(recipe: String) {
        val intentRecipeDetails = Intent(this, RecipeDetailsActivity::class.java)
        intentRecipeDetails.putExtra("recipePath", recipe)
        startActivity(intentRecipeDetails)
    }

    // --- LOAD PUB ---

    private fun loadPub() {
        MobileAds.initialize(
            this
        ) {} //TODO PUB ca-app-pub-5765642947536779/2555750071
        val adRequest = AdRequest.Builder().build()
        adViewCategory.loadAd(adRequest)
    }

    // --- ON CLICK ---
    private fun onClickAddRecipe(){
        buttonAddRecipeCategory.setOnClickListener {
            val intentAddRecipeActivity=Intent(this,AddRecipeActivity::class.java)
            startActivity(intentAddRecipeActivity)
        }
    }
}
