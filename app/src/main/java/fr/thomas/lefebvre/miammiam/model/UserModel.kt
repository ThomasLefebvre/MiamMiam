package fr.thomas.lefebvre.miammiam.model

data class UserModel (
    val uid:String="",
    val name:String="",
    val email:String="",
    val photoUrl:String="",
    val bookRecipes:ArrayList<String> = arrayListOf(),
    val listPhotoUrl:ArrayList<String> = arrayListOf()
)