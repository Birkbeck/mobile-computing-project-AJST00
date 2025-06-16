// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing anotation to mark the class as a Room database entity
import androidx.room.Entity
// Importing anotation to define the primary key field for room entity
import androidx.room.PrimaryKey

// Marking this data class as a room entity and specifies table name
@Entity(tableName = "recipes")
// Declares a data class for storing information about a recipe
data class Recipe(
    // Declaring id as primary key with auto generation
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // Property for storing the recipe title
    val title: String,
    // Declaring property for storing ingredients as single string
    val ingredients: String,
    // Declaring property for holding cooking instructions
    val instructions: String,
    // Declaring property to classify recipe
    val category: String
)
