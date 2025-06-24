// Defining package that class belongs to
package com.adriansilva.culinarycompanion

// Importing coroutine tools for launching and blocking execution
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Importing JUnit test annotations and assertions
import org.junit.Test
import org.junit.Assert.assertTrue

// Fake repository tracks whether insert() was called
class FakeRecipeRepository : RecipeRepositoryTestContract {
    var insertCalled = false

    override suspend fun insert(recipe: Recipe) {
        insertCalled = true
    }

    override suspend fun update(recipe: Recipe) {}
    override suspend fun delete(recipe: Recipe) {}
}

// Contract used by the testable ViewModel
interface RecipeRepositoryTestContract {
    suspend fun insert(recipe: Recipe)
    suspend fun update(recipe: Recipe)
    suspend fun delete(recipe: Recipe)
}

// Testable ViewModel with injectable repository
open class RecipeViewModelTestable(private val repo: RecipeRepositoryTestContract) {
    open fun insert(recipe: Recipe) = GlobalScope.launch {
        repo.insert(recipe)
    }
}

// Unit test class to verify ViewModel behaviour
class RecipeViewModelTest {

    // Testing that the ViewModel correctly delegates insert to the repository
    @Test
    fun testInsertCallsRepository() = runBlocking {
        val fakeRepo = FakeRecipeRepository()
        val viewModel = RecipeViewModelTestable(fakeRepo)
        val recipe = Recipe(0, "Salad", "Lettuce", "Mix", "Lunch")

        // Calling insert and wait for it to finish
        viewModel.insert(recipe).join()

        // Assert that insert was called
        assertTrue(fakeRepo.insertCalled)
    }
}


