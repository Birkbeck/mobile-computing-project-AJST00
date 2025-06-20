// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Imports Application for global app context
import android.app.Application
// Importing AndroidViewModel with application context
import androidx.lifecycle.AndroidViewModel
// Importing observable data holder for UI updates
import androidx.lifecycle.LiveData
// Importing coroutine scope tied to ViewModel
import androidx.lifecycle.viewModelScope
// Importing dispatcher for background tasks
import kotlinx.coroutines.Dispatchers
// Importing launch to start coroutines
import kotlinx.coroutines.launch

// managing recipe data within ViewModel
class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    // Repository handle data operations
    private val repository: RecipeRepository
    // Observable list of all recipes
    val allRecipes: LiveData<List<Recipe>>

    // Initialises the ViewModel and sets up repository access
    init {
        // Geting DAO from Room database
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        repository = RecipeRepository(recipeDao)
        // Getting all recipes from repository
        allRecipes = repository.allRecipes
    }

    // Inserting recipe in background
    fun insert(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(recipe)
    }

    // Updating recipe in background
    fun update(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(recipe)
    }

    // Deleting recipe in background
    fun delete(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(recipe)
    }

    // Getting recipe by ID
    fun getRecipeById(id: Int): LiveData<Recipe?> {
        return repository.getRecipeById(id)
    }
}
