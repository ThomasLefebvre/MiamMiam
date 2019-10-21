package fr.thomas.lefebvre.miammiam.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.squareup.picasso.Picasso
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.model.RecipeModel
import fr.thomas.lefebvre.miammiam.service.RecipeHelper
import fr.thomas.lefebvre.miammiam.ui.adapter.IngredientAdapter
import kotlinx.android.synthetic.main.activity_recipe_details.*




class RecipeDetailsActivity : AppCompatActivity() {


    var recipePath: String? = null
    val recipeHelper=RecipeHelper()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipePath=intent.getStringExtra("recipePath")
        setContentView(R.layout.activity_recipe_details)
       getRecipe(recipePath!!)
        MobileAds.initialize(this) {} //TODO PUB ca-app-pub-5765642947536779/2555750071
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)







    }


    // --- RECYCLER VIEW INGREDIENT ---
    fun initRecyclerViewIngredients(listIngredient:List<String>){
        recyclerViewIngredients.apply {
            layoutManager=LinearLayoutManager(this@RecipeDetailsActivity)
            adapter=IngredientAdapter(this@RecipeDetailsActivity,listIngredient)
        }
    }

    // --- RECYCLER VIEW STEPS RECIPE ---
    fun initRecyclerViewSteps(listSteps:List<String>){
        recyclerViewSteps.apply {
            layoutManager=LinearLayoutManager(this@RecipeDetailsActivity)
            adapter=IngredientAdapter(this@RecipeDetailsActivity,listSteps)
        }
    }



    // --- LOAD RECIPE TO FIREBASE DATA ---
    fun getRecipe(uidRecipe:String){
        recipeHelper.getRecipeById(uidRecipe).addOnSuccessListener { documentSnapshot ->
            val recipeFireStore= documentSnapshot.toObject(RecipeModel::class.java)
            Picasso.get().load(recipeFireStore?.photoUrl).into(imageViewRecipeDetails)
            textViewTitleRecipes.text=recipeFireStore?.name
            textViewCal.text=recipeFireStore?.cal
            textViewTime.text=recipeFireStore?.time
            textViewQuantity.text=recipeFireStore?.quantity


            initRecyclerViewIngredients(recipeFireStore!!.listIngredients!!)
            initRecyclerViewSteps(recipeFireStore.listStep!!)
        }
    }









}
