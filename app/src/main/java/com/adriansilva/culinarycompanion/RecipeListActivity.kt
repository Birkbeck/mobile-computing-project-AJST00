// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing Intent for switching between activities
import android.content.Intent
// Bundle passes data and restore state during lifecycle changes
import android.os.Bundle
// Importing Button widget for user interaction
import android.widget.Button
// ImageView to display image based buttons
import android.widget.ImageView
// Allows to display text in the UI
import android.widget.TextView
// Imports View class base for all UI components
import android.view.View
// base class that supports lifecycle components like ViewModel
import androidx.activity.ComponentActivity
// Extension to get ViewModel scoped to this activity
import androidx.activity.viewModels
// Layout manager that arranges items in a vertical scrollable list
import androidx.recyclerview.widget.LinearLayoutManager
// Used to build and manage a scrollable list of recipes efficiently
import androidx.recyclerview.widget.RecyclerView

// Activity that displays a list of recipes filtered by category
class RecipeListActivity : ComponentActivity() {

    // RecyclerView displays the list of recipes
    private lateinit var recyclerView: RecyclerView
    // Adapter populates the RecyclerView with recipe data
    private lateinit var adapter: RecipeAdapter

    // ViewModel that stores and manages recipe data
    private val recipeViewModel: RecipeViewModel by viewModels()

    // Called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout for the activity
        setContentView(R.layout.activity_recipe_list)

        // Retrieves the selected category passed from MainActivity
        val category = intent.getStringExtra("category") ?: "Recipes"
        // Sets the category title in the UI
        findViewById<TextView>(R.id.text_title).text = "$category Recipes"

        // Handles the back navigation button
        val backButton = findViewById<ImageView>(R.id.back_button)
        // Sets up the back button to return to the main screen
        backButton.setOnClickListener {
            // Navigates back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        recyclerView = findViewById(R.id.recipe_recycler_view)
        adapter = RecipeAdapter(emptyList()) { selectedRecipe ->
            val intent = Intent(this, ViewRecipeActivity::class.java).apply {
                putExtra("recipe_id", selectedRecipe.id)
            }
            startActivity(intent)
        }

        // Binding the adapter to the RecyclerView
        recyclerView.adapter = adapter
        // Sets a vertical LinearLayoutManager for scrolling
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Handle the "Add" button click
        val addButton: Button = findViewById(R.id.btn_add)
        addButton.setOnClickListener {
            // Opens EditRecipeActivity to add a new recipe
            val intent = Intent(this, EditRecipeActivity::class.java)
            startActivity(intent)
        }
        // Finds the "No recipes found" TextView
        val emptyView = findViewById<TextView>(R.id.empty_view)

        // Observes the list of all recipes from ViewModel
        recipeViewModel.allRecipes.observe(this) { recipes ->
            // Filters recipes by selected category
            val filteredRecipes = recipes.filter { it.category == category }
            // Updates the adapter with the filtered list
            adapter.updateData(filteredRecipes)

            // Shows or hides the No recipes found message based on the list
            emptyView.visibility = if (filteredRecipes.isEmpty()) View.VISIBLE else View.GONE
        }

    }
}
