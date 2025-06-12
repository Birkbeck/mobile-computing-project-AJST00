// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing Bundle class to pass data and save state information
import android.os.Bundle
// Importing Android UI widgets like EditText, Button and Spinner
import android.widget.*
// Defining new public class named EditRecipeActivity that inherits from AppCompatActivity
import androidx.activity.ComponentActivity
// Extension to get ViewModel scoped to this activity
import androidx.activity.viewModels

// EditRecipeActivity: Handles creating and editing a recipe
class EditRecipeActivity : ComponentActivity() {

    // Creating ViewModel instance for managing UI related data
    private val recipeViewModel: RecipeViewModel by viewModels()
    // Nullable variable to track whether editing an existing recipe
    private var recipeId: Int? = null // track if editing

    // Called when the activity is starting.
    // Initialize UI components, bind data, and set up event listeners
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting the screen layout for this activity
        setContentView(R.layout.activity_edit_recipe)

        // Linking UI elements to variables
        val nameInput = findViewById<EditText>(R.id.edit_name)
        val ingredientsInput = findViewById<EditText>(R.id.edit_ingredients)
        val instructionsInput = findViewById<EditText>(R.id.edit_instructions)
        val saveButton = findViewById<Button>(R.id.btn_save)
        val categorySpinner = findViewById<Spinner>(R.id.spinner_category)

        // X icon returns to previous screen without saving
        val cancelButton = findViewById<ImageView>(R.id.btn_cancel)
        cancelButton.setOnClickListener {
            finish()
        }

        // Categories shown in the dropdown
        val categories = listOf("Breakfast", "Brunch", "Lunch", "Dinner", "Desserts", "Other")


        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)

        // Setting up spinner with category options
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = spinnerAdapter

        // Checking if editing an existing recipe
        recipeId = intent.getIntExtra("recipe_id", -1)
        if (recipeId != -1) {
            recipeViewModel.getRecipeById(recipeId!!).observe(this) { recipe ->
                recipe?.let {
                    nameInput.setText(it.title)
                    ingredientsInput.setText(it.ingredients)
                    instructionsInput.setText(it.instructions)
                    val categoryIndex = categories.indexOf(it.category)
                    if (categoryIndex != -1) {
                        categorySpinner.setSelection(categoryIndex)
                    }
                }
            }
        } else {
            recipeId = null // No recipe ID passed hence creating a new one
        }

        // Save button behavior
        saveButton.setOnClickListener {
            // Retrieve and trim input values
            val name = nameInput.text.toString().trim()
            val ingredients = ingredientsInput.text.toString().trim()
            val instructions = instructionsInput.text.toString().trim()
            val category = categorySpinner.selectedItem.toString()

            // Validating inputs to make sure all fields are completed
            if (name.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty()) {
                val recipe = Recipe(
                    id = recipeId ?: 0, // ID needed for update
                    title = name,
                    ingredients = ingredients,
                    instructions = instructions,
                    category = category
                )

                // Insert or update based on whether we're creating or editing
                if (recipeId == null) {
                    recipeViewModel.insert(recipe)
                } else {
                    recipeViewModel.update(recipe)
                }
                // Close the activity after saving
                finish()
            } else {
                // Show a warning message if any field is empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
