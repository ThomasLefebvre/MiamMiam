package fr.thomas.lefebvre.miammiam.ui.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.squareup.picasso.Picasso
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.model.RecipeModel
import fr.thomas.lefebvre.miammiam.service.RecipeHelper
import fr.thomas.lefebvre.miammiam.ui.adapter.IngredientAdapter
import kotlinx.android.synthetic.main.activity_recipe_details.*


class RecipeDetailsActivity : AppCompatActivity() {

    // recipe path
    var recipePath: String? = null
    // recipe helper firestore
    val recipeHelper = RecipeHelper()
    //admob interstitial
    lateinit var mInterstitialAd: InterstitialAd
    // tag debug
    val TAG = "DEBUG_RECIPES_DETAILS"
    // shared prefs
    val SHARED_PREF = "sharedPrefs"
    val COUNT_PUB = "countPub"
    var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipePath = intent.getStringExtra("recipePath")
        setContentView(R.layout.activity_recipe_details)
        getRecipe(recipePath!!)
        loadPub()
        loadCounter()


    }


    // --- RECYCLER VIEW INGREDIENT ---
    fun initRecyclerViewIngredients(listIngredient: List<String>) {
        recyclerViewIngredients.apply {
            layoutManager = LinearLayoutManager(this@RecipeDetailsActivity)
            adapter = IngredientAdapter(this@RecipeDetailsActivity, listIngredient)
        }
    }

    // --- RECYCLER VIEW STEPS RECIPE ---
    fun initRecyclerViewSteps(listSteps: List<String>) {
        recyclerViewSteps.apply {
            layoutManager = LinearLayoutManager(this@RecipeDetailsActivity)
            adapter = IngredientAdapter(this@RecipeDetailsActivity, listSteps)
        }
    }


    // --- LOAD RECIPE TO FIREBASE DATA ---
    fun getRecipe(uidRecipe: String) {
        recipeHelper.getRecipeById(uidRecipe).addOnSuccessListener { documentSnapshot ->
            val recipeFireStore = documentSnapshot.toObject(RecipeModel::class.java)
            Picasso.get().load(recipeFireStore?.photoUrl).into(imageViewRecipeDetails)
            textViewTitleRecipes.text = recipeFireStore?.name
            textViewCal.text = recipeFireStore?.cal
            textViewTime.text = recipeFireStore?.time
            textViewQuantity.text = recipeFireStore?.quantity


            initRecyclerViewIngredients(recipeFireStore!!.listIngredients!!)
            initRecyclerViewSteps(recipeFireStore.listStep!!)
        }
    }

    // --- COUNTER PUB ---

    private fun saveCounter() {
        val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (count < 2) {
            count = count + 1
        } else {
            count = 0
        }
        editor.putInt(COUNT_PUB, count)
        editor.apply()
    }

    private fun loadCounter() {
        val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        count = sharedPreferences.getInt(COUNT_PUB, 0)
    }

    // --- LOAD PUB ---

    private fun loadPub() {
        MobileAds.initialize(this) {} //TODO PUB ca-app-pub-5765642947536779/2555750071
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId =
            "ca-app-pub-3940256099942544/1033173712" //TODO PUB ca-app-pub-5765642947536779/3021179152       "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    // --- OVERRIDE METHOD ---
    override fun onBackPressed() {
        saveCounter()

        if (mInterstitialAd.isLoaded&&count==0) {
            mInterstitialAd.show()
        } else {
            Log.d(TAG, "The interstitial wasn't loaded yet.")
        }
        super.onBackPressed()
    }
}
