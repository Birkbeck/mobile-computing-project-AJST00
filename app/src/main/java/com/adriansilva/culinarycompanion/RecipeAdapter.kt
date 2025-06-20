// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// LayoutInflater used to convert XML layout files into View objects
import android.view.LayoutInflater
// View is base class for all UI components
import android.view.View
// onCreateViewHolder provides the parent ViewGroup context for inflating the item layout
import android.view.ViewGroup
// Displays recipe title inside each list item connected via findViewById in the ViewHolder
import android.widget.TextView
// Used to build and manage a scrollable list of recipes efficiently
import androidx.recyclerview.widget.RecyclerView

// Declaring RecipeAdapter class and extends RecyclerView.Adapter
class RecipeAdapter(
    private var recipes: List<Recipe>, // List of recipes to display
    private val onItemClick: (Recipe) -> Unit // Click listener lambda
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() { // Specify the ViewHolder type

    // nested ViewHolder class holds the views for each recipe item
    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Finds the TextView in item_recipe.xml that displays the recipe title
        val titleText: TextView = itemView.findViewById(R.id.text_title)
    }

    // Called when a new ViewHolder is needed to display a list item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        // Inflates layout for a single recipe item from XML
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        // Returning new instance of RecipeViewHolder with inflated view
        return RecipeViewHolder(view)
    }

    // Binding data to existing ViewHolder at given position
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        // Geting recipe at the current position
        val recipe = recipes[position]
        // Setting text of the title TextView to recipe's title
        holder.titleText.text = recipe.title

        // Setting click listener on the entire item view to handle taps
        holder.itemView.setOnClickListener {
            onItemClick(recipe)
        }
    }

    // Returns the total number of items in the RecyclerView
    override fun getItemCount(): Int = recipes.size

    // Replacing current recipe list with a new one and refreshes RecyclerView
    fun updateData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}
