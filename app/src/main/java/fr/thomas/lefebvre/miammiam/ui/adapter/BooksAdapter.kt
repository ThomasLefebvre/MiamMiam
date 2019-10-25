package fr.thomas.lefebvre.miammiam.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.thomas.lefebvre.miammiam.R



class BooksAdapter(

    val context: Context,
    private val listRecipesBook: List<String>,
    private val listPhotoUrl: List<String>,
    private val listenerRecipe:(String)-> Unit

) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.recycler_view_book_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listRecipesBook.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, listRecipesBook[position],listPhotoUrl[position],listenerRecipe)
    }

    class ViewHolder(elementList: View) : RecyclerView.ViewHolder(elementList) {
        val nameRecipe: TextView = itemView.findViewById(R.id.textViewBook)
        val photoRecipe:ImageView= itemView.findViewById(R.id.imageViewBook)





        fun bind(context: Context, recipe: String,photoUrl:String,listenerRecipe: (String) -> Unit){
            nameRecipe.text=recipe
            Picasso.get().load(photoUrl).into(photoRecipe)

            itemView.setOnClickListener {
                listenerRecipe(recipe)
            }

        }

    }
}