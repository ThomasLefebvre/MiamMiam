package fr.thomas.lefebvre.miammiam.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.ui.activity.CategoryActivity
import kotlinx.android.synthetic.main.fragment_category.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonApero.setOnClickListener {
            launchCategoryAcitivty(getString(R.string.snacking))
        }
        buttonCake.setOnClickListener {
            launchCategoryAcitivty(getString(R.string.desser))
        }
        buttonEntre.setOnClickListener {
            launchCategoryAcitivty(getString(R.string.entre))
        }
        buttonFish.setOnClickListener {
            launchCategoryAcitivty(getString(R.string.fish))
        }
        buttonMeat.setOnClickListener {
            launchCategoryAcitivty(getString(R.string.viande))
        }
        buttonPlat.setOnClickListener {
            launchCategoryAcitivty(getString(R.string.plat))
        }
        buttonSmoothie.setOnClickListener {
            launchCategoryAcitivty(getString(R.string.liquide))
        }
        buttonVege.setOnClickListener {
            launchCategoryAcitivty(getString(R.string.vegetarien))
        }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun launchCategoryAcitivty(category: String) {
        val intentCategory = Intent(requireContext(), CategoryActivity::class.java)
        intentCategory.putExtra("category", category)
        startActivity(intentCategory)
    }


}
