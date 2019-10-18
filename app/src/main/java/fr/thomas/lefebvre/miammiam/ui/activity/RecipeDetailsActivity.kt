package fr.thomas.lefebvre.miammiam.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.ui.adapter.IngredientAdapter
import kotlinx.android.synthetic.main.activity_recipe_details.*




class RecipeDetailsActivity : AppCompatActivity() {


    val recipePath="Tiramisu"

    val listIngredient= arrayListOf<String>("100 gr mascarpone","3 oeufs","1 verre de café","1 boite de boudoirs","10kg d'imagination","1 pincée de folie")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)
        initRecyclerViewIngredients(listIngredient)

        MobileAds.initialize(this) {} //TODO PUB ca-app-pub-5765642947536779/2555750071
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)







    }


    // --- RECYCLER VIEW ---
    fun initRecyclerViewIngredients(listIngredient:List<String>){
        recyclerViewIngredients.apply {
            layoutManager=LinearLayoutManager(this@RecipeDetailsActivity)
            adapter=IngredientAdapter(this@RecipeDetailsActivity,listIngredient)
        }
    }









}
