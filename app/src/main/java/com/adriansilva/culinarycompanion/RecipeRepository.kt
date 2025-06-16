// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing LiveData for observing database changes
import androidx.lifecycle.LiveData

// Declaring repository class for handling database operations
class RecipeRepository(private val recipeDao: RecipeDao) {

    // Retrieve all recipes as LiveData so UI can observe for changes
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    // Inserting a new recipe into the database asynchronously
    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    // Update an existing recipe asynchronously
    suspend fun update(recipe: Recipe) {
        recipeDao.update(recipe)
    }

    // Deleting a recipe asynchronously
    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    // Getting specific recipe by ID as LiveData
    fun getRecipeById(id: Int): LiveData<Recipe?> {
        return recipeDao.getRecipeById(id)
    }
}
