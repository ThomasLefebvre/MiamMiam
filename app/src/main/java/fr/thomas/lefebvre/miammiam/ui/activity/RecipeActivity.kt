package fr.thomas.lefebvre.miammiam.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.model.RecipeModel

import kotlinx.android.synthetic.main.activity_recipe.*


class RecipeActivity : AppCompatActivity() {


    val listIngredients= arrayListOf("250 gr mascarpone","1 boite de boudoirs","1 verre de café", "2 cl d'Amareto")
    val tiramisu=RecipeModel("Tiramisu","Dessert","Tiramisu",arrayListOf("Battre les jaunes d'oeufs","Mettre la mascarpone dans le mélange"),"https://firebasestorage.googleapis.com/v0/b/miammiam-6ff33.appspot.com/o/Recette%2FDessert%2Ftiramisu.jpg?alt=media&token=845e769e-d6c8-4ec3-bacd-2443417359c1",listIngredients)
    val COLLECTION_NAME="recipes"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        onButtonCreateClick()

    }

    private fun onButtonCreateClick(){
        button2.setOnClickListener {
            createRecipeOnDataBase()
        }
    }




    private fun createRecipeOnDataBase(){

        FirebaseFirestore.getInstance().collection(COLLECTION_NAME).document().set(tiramisu)
    }
}
