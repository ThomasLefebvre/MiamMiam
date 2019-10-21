package fr.thomas.lefebvre.miammiam.service


import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

class RecipeHelper {

    private val COLLECTION_NAME="recipes"

    //--- COLLECTION REFERENCE ---

    private fun  getRecipesCollection():CollectionReference{
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
    }

    //--- GET RECIPE ---

     fun getAllRecipes():Task<QuerySnapshot>{
        return getRecipesCollection().get()
    }

     fun getRecipeById(uid:String): Task<DocumentSnapshot> {
        return getRecipesCollection().document(uid).get()
    }

    fun getRecipeByUid(tag:String):Task<DocumentSnapshot>{
        return getRecipesCollection().document("tag").get()
    }
}