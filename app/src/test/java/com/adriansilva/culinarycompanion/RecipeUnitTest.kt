// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing assertion methods
import org.junit.Assert.*
// Importing Test annotation to mark test functions
import org.junit.Test

// Declaring class containing unit tests related to Recipe
class RecipeUnitTest {

    // Marking function as a unit test
    @Test
    // Defining test function to verify Recipe object creation
    fun testCreateRecipeObject() {
        // Creating new Recipe object with set values
        val recipe = Recipe(
            id = 1,
            title = "Pancakes",
            ingredients = "Flour, Eggs, Milk",
            instructions = "Mix ingredients and cook.",
            category = "Breakfast"
        )

        // Verifing that all properties of the recipe match the expected values
        assertEquals(1, recipe.id)
        assertEquals("Pancakes", recipe.title)
        assertEquals("Flour, Eggs, Milk", recipe.ingredients)
        assertEquals("Mix ingredients and cook.", recipe.instructions)
        assertEquals("Breakfast", recipe.category)
    }
}
