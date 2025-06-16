// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing LiveData for observing database changes
import androidx.lifecycle.LiveData
// Importing Room annotations for defining DAO behavior
import androidx.room.*

// Declaring DAO interface for database operations
@Dao
interface RecipeDao {
    // Defining database operations
    @Insert
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    // Query to retrieve all recipes from the database ordered by ID
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    // retrieve a specific recipe by its ID
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Int): LiveData<Recipe?>

}
