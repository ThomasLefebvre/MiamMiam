package fr.thomas.lefebvre.miammiam.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.thomas.lefebvre.miammiam.R
import java.lang.StringBuilder

class AddIngredientOrStepAdapter(

    val context: Context,
    private val listIngredientsOrStep: List<String>,
    private val listener:(Int)->Unit

) : RecyclerView.Adapter<AddIngredientOrStepAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(p0.context).inflate(R.layout.recycler_view_add_recipe, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listIngredientsOrStep.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, listIngredientsOrStep[position],listener)
    }

    class ViewHolder(elementList: View) : RecyclerView.ViewHolder(elementList) {
        val nameIngredientOrStep: TextView = itemView.findViewById(R.id.textViewAddIngredientOrStep)
        val suppressIcon:ImageButton=itemView.findViewById(R.id.imageButtonSuppress)


        fun bind(context: Context, ingredient: String,listener: (Int) -> Unit) {
            val string = StringBuilder("- $ingredient")
            nameIngredientOrStep.text = string

            suppressIcon.setOnClickListener {
                listener(adapterPosition)
            }
        }

    }
}