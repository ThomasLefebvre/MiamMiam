package fr.thomas.lefebvre.miammiam.model




data class RecipeModel (
    val uid:String="",
    val category:String="",
    val name:String="",
    val listStep:ArrayList<String>? =null,
    val photoUrl:String="",
    val listIngredients:ArrayList<String>?=null,
    val quantity:String="",
    val cal:String="",
    val tag:String="",
    val time:String=""
)



