// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing Context used to provide application level context for database creation
import android.content.Context
// Imports the Room @Database annotation to define database configuration
import androidx.room.Database
// Importing anotation to mark the class as a Room database entity
import androidx.room.Room
// Importing RoomDatabase as the base class for the Room database
import androidx.room.RoomDatabase

// Annotates this class as a Room database specifying the entities and version
@Database(entities = [Recipe::class], version = 1, exportSchema = false)
// Declares the abstract RecipeDatabase class extending RoomDatabase
abstract class RecipeDatabase : RoomDatabase() {

    // Declaring abstract function to retrieve the DAO
    abstract fun recipeDao(): RecipeDao

    // Ensures a single shared instance of the database across the app
    companion object {

        // Marks INSTANCE as volatile to ensure visibility across threads
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        // Returns database instance
        fun getDatabase(context: Context): RecipeDatabase {

            // Checks whether the database instance already exists
            return INSTANCE ?: synchronized(this) {
                // Builds the database if it does not already exist
                val instance = Room.databaseBuilder(
                    // Uses application context to avoid leaking an Activity
                    context.applicationContext,
                    // Database class reference
                    RecipeDatabase::class.java,
                    // Sets the name of the database file
                    "recipe_database"
                ).build()
                // Assigns the built database to INSTANCE
                INSTANCE = instance
                // Returns the created database instance
                instance
            }
        }
    }
}
