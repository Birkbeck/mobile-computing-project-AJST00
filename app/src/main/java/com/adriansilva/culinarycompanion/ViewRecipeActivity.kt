// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Imports Bundle for passing and restoring data in activities
import android.os.Bundle
// ImageView class to access and handle image buttons
import android.widget.ImageView
// Importating TextView class to manipulate text based UI components
import android.widget.TextView
// Importing Toast class for short popup messages to user
import android.widget.Toast
// Importing ComponentActivity as base for this activity
import androidx.activity.ComponentActivity
// Provides a ViewModel scoped to this activity
import androidx.activity.viewModels
// AlertDialog to show confirmation prompt
import android.app.AlertDialog
// Importing Intent for switching between activities
import android.content.Intent

// activity for viewing the details of a single recipe
class ViewRecipeActivity : ComponentActivity() {

    // Initialises the ViewModel for recipe data operations
    private val recipeViewModel: RecipeViewModel by viewModels()
    // Stores the currently loaded recipe
    private var recipe: Recipe? = null

    // Calling when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sets the layout to activity_view_recipe.xml
        setContentView(R.layout.activity_view_recipe)

        // Binding UI buttons its respective views using IDs
        val backButton = findViewById<ImageView>(R.id.back_button)
        val editButton = findViewById<ImageView>(R.id.edit_button)
        val deleteButton = findViewById<ImageView>(R.id.delete_button)

        // back button to close the activity when clicked
        backButton.setOnClickListener {
            finish()
        }

        // Edit button to launch EditRecipeActivity passing the recipe ID
        editButton.setOnClickListener {
            recipe?.let {
                val intent = Intent(this, EditRecipeActivity::class.java).apply {
                    putExtra("recipe_id", it.id)
                }
                startActivity(intent)
            }
        }

        // Delete button shows a confirmation promt before deletion
        deleteButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to permanently delete this recipe?")
                .setPositiveButton("Delete") { _, _ ->
                    recipe?.let {
                        recipeViewModel.delete(it)
                        Toast.makeText(this, "Recipe deleted", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Retrieves recipe ID from the intent that started this activity
        val recipeId = intent.getIntExtra("recipe_id", -1)

        // Loads recipe if a valid ID is provided
        if (recipeId != -1) {
            recipeViewModel.getRecipeById(recipeId).observe(this) { loadedRecipe ->
                loadedRecipe?.let {

                    // Stores loaded recipe in a local variable
                    recipe = it

                    // Populate UI with the recipe's details
                    findViewById<TextView>(R.id.text_name).text = it.title
                    findViewById<TextView>(R.id.text_ingredients).text = it.ingredients
                    findViewById<TextView>(R.id.text_instructions).text = it.instructions

                    // Formats and display the category label
                    val categoryText = getString(R.string.category_label, it.category)
                    findViewById<TextView>(R.id.text_category).text = categoryText
                }
            }
        }
    }
}
