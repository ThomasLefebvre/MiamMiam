package fr.thomas.lefebvre.miammiam.service


import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class RecipeHelper {

    private val COLLECTION_NAME="recipes"

    //--- COLLECTION REFERENCE ---

    private fun  getRecipesCollection():CollectionReference{
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
    }

    //--- GET RECIPE ---

    private fun getAllRecipes():Task<QuerySnapshot>{
        return getRecipesCollection().get()
    }

    private fun getRecipeById(uid:String): Task<DocumentSnapshot> {
        return getRecipesCollection().document(uid).get()
    }
}