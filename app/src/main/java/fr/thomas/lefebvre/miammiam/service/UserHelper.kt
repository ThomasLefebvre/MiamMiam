package fr.thomas.lefebvre.miammiam.service


import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import fr.thomas.lefebvre.miammiam.model.UserModel


class UserHelper {

    private val COLLECTION_NAME="users"

    //--- COLLECTION REFERENCE ---

    private fun  getUsersCollection():CollectionReference{
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
    }

    //--- CREATE USER ---

     fun createUser(uid:String,name:String,email:String,photoUrl:String){
        val userToCreate=UserModel(uid,name,email,photoUrl)
        getUsersCollection().document(uid).set(userToCreate)
    }

    //--- GET USER ---

     fun getUserById(uid:String): Task<DocumentSnapshot> {
        return getUsersCollection().document(uid).get()
    }


    // --- UPDATE USER ---

    fun updateBookUser(uidRecipe:String,uid:String):Task<Void>{
        return getUsersCollection().document(uid).update("bookRecipes",FieldValue.arrayUnion(uidRecipe))
    }

    fun remooveBookUser(uidRecipe:String,uid:String):Task<Void>{
        return getUsersCollection().document(uid).update("bookRecipes",FieldValue.arrayRemove(uidRecipe))
    }

}