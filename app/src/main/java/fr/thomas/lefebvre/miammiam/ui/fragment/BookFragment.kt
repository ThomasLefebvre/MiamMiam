package fr.thomas.lefebvre.miammiam.ui.fragment


import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth

import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.model.RecipeModel
import fr.thomas.lefebvre.miammiam.model.UserModel
import fr.thomas.lefebvre.miammiam.service.RecipeHelper
import fr.thomas.lefebvre.miammiam.service.UserHelper
import fr.thomas.lefebvre.miammiam.ui.activity.RecipeDetailsActivity
import fr.thomas.lefebvre.miammiam.ui.adapter.BooksAdapter
import fr.thomas.lefebvre.miammiam.ui.adapter.IngredientAdapter
import kotlinx.android.synthetic.main.activity_recipe_details.*
import kotlinx.android.synthetic.main.fragment_book.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BookFragment : Fragment() {
    val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    val userHelper = UserHelper()
    val recipeHelper = RecipeHelper()
    var listRecipe: ArrayList<String> = arrayListOf<String>()
    var listPhotoUrl: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getUserRecipeList(currentUser)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        getUserRecipeList(currentUser)
        super.onResume()
    }

    // --- GET RECIPE OF BOOKS ---

    private fun getUserRecipeList(uidUser: String) {
        userHelper.getUserById(uidUser).addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(UserModel::class.java)
            if (user?.bookRecipes != null) {
                listRecipe = user.bookRecipes
                listPhotoUrl = user.listPhotoUrl

            }

            setRecyclerViewBook()

        }
    }


    private fun setRecyclerViewBook() {

        recyclerView_Books.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = BooksAdapter(requireContext(), listRecipe, listPhotoUrl) { recipe: String ->
                recipeClick(recipe)

            }

        }
    }

    private fun recipeClick(recipe: String) {
        val intentRecipeDetails = Intent(requireContext(), RecipeDetailsActivity::class.java)
        intentRecipeDetails.putExtra("recipePath", recipe)
        startActivity(intentRecipeDetails)
    }


}
