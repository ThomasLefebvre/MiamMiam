package fr.thomas.lefebvre.miammiam.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.thomas.lefebvre.miammiam.R

class IngredientAdapter(

    val context: Context,
    private val listIngredients: List<String>

) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.recycler_view_ingredients_item, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listIngredients.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, listIngredients[position])
    }

    class ViewHolder(elementList: View) : RecyclerView.ViewHolder(elementList) {
        val nameIngredient:TextView= itemView.findViewById(R.id.textViewIngredients)





        fun bind(context: Context, ingredient: String){
            nameIngredient.text=ingredient
        }

    }
}