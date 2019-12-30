package fr.thomas.lefebvre.miammiam.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.model.RecipeModel

class RecipeAdapter (
    options: FirestoreRecyclerOptions<RecipeModel>,
    private val listener:(RecipeModel) -> Unit

): FirestoreRecyclerAdapter<RecipeModel,RecipeAdapter.RecipeHolder>(options){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.RecipeHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_recipes_item,parent,false)
        return RecipeHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeAdapter.RecipeHolder, position: Int, recipe: RecipeModel) {
       holder.bind(recipe,listener)
    }

    class RecipeHolder(view: View):RecyclerView.ViewHolder(view) {
    val photoRecipe:ImageView= itemView.findViewById(R.id.imageViewRecipeRecyclerView) as ImageView

        fun bind(recipe:RecipeModel,listener: (RecipeModel) -> Unit)= with(itemView){
            val photoUrl=recipe.photoUrl
            Picasso.get().load(photoUrl)
                .resize(300,300)
                .centerCrop()
                .into(photoRecipe)

            itemView.setOnClickListener {
                listener(recipe)
            }
        }
    }

}