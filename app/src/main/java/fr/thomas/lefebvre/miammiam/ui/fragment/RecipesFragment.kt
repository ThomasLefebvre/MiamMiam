package fr.thomas.lefebvre.miammiam.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.ui.activity.RecipeDetailsActivity
import kotlinx.android.synthetic.main.fragment_recipes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RecipesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO DELETE AFTER TEST
        imageButton_Recipe_Of_Day.setOnClickListener {
            startActivity(Intent(requireContext(),RecipeDetailsActivity::class.java))
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
