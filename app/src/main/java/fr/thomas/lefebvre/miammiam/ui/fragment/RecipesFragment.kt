package fr.thomas.lefebvre.miammiam.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.model.RecipeModel
import fr.thomas.lefebvre.miammiam.ui.activity.RecipeDetailsActivity
import fr.thomas.lefebvre.miammiam.ui.adapter.RecipeAdapter
import kotlinx.android.synthetic.main.activity_recipe_details.*
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.android.synthetic.main.fragment_recipes.adView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RecipesFragment : Fragment() {

    lateinit var adapterClassique: RecipeAdapter
    lateinit var adapterFun: RecipeAdapter
    lateinit var adapterVoyage: RecipeAdapter


    // --- OVERRIDE METHODE ---
    override fun onStart() {
        super.onStart()
        adapterClassique.startListening()
        adapterFun.startListening()
        adapterVoyage.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterClassique.stopListening()
        adapterFun.stopListening()
        adapterVoyage.stopListening()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        adsMobs()

        setRecyclerViewClassique()
        setRecyclerViewFunny()
        setRecyclerViewVoyage()

        super.onViewCreated(view, savedInstanceState)
    }

    // --- SET ADS MOBS PUB ---

    private fun adsMobs() {
        MobileAds.initialize(
            requireContext()
        ) {} //TODO PUB ca-app-pub-5765642947536779/2555750071
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

    }

    // --- SET RECIPE OF DAYS ---
    private fun setRecipeOfDay() {
        imageButton_Recipe_Of_Day.setOnClickListener {
            startActivity(Intent(requireContext(), RecipeDetailsActivity::class.java))
        }
    }


    fun setOptionFirebase(path: String, valuePath: String): FirestoreRecyclerOptions<RecipeModel> {
        val query = FirebaseFirestore.getInstance()
            .collection("recipes")
            .whereEqualTo(path, valuePath)


        val options = FirestoreRecyclerOptions.Builder<RecipeModel>()
            .setQuery(query, RecipeModel::class.java)
            .build()

        return options
    }

    // --- SET ALL RECYCLER VIEW ---

    fun setRecyclerViewClassique() {
        adapterClassique = RecipeAdapter(setOptionFirebase("tag", "classique")) { photoClick: RecipeModel ->
            articleClick(photoClick)
        }
        recyclerViewClassique.setHasFixedSize(false)
        recyclerViewClassique.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewClassique.adapter = adapterClassique
    }

    fun setRecyclerViewFunny() {
        adapterFun = RecipeAdapter(setOptionFirebase("tag", "fun")) { photoClick: RecipeModel ->
            articleClick(photoClick)
        }
        recyclerViewFunny.setHasFixedSize(false)
        recyclerViewFunny.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFunny.adapter = adapterFun
    }

    fun setRecyclerViewVoyage() {
        adapterVoyage = RecipeAdapter(setOptionFirebase("tag", "voyage")) { photoClick: RecipeModel ->
            articleClick(photoClick)
        }
        recyclerViewVoyage.setHasFixedSize(false)
        recyclerViewVoyage.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewVoyage.adapter = adapterVoyage
    }

    // SET CLICK LISTENER OF RECYCLER VIEW ---

    private fun articleClick(itemClick: RecipeModel) {//start details activity if click on photo
        val intentDetailsRecipe = Intent(requireContext(), RecipeDetailsActivity::class.java)
        intentDetailsRecipe.putExtra("recipePath", itemClick.uid)
        startActivity(intentDetailsRecipe)

    }


}
