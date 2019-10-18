package fr.thomas.lefebvre.miammiam.model




data class RecipeModel (
    val uid:String,
    val category:String,
    val name:String,
    val listStep:ArrayList<String>,
    val photoUrl:String,
    val listIngredients:ArrayList<String>
)


