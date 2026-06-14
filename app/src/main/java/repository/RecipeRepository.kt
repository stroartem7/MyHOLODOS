package repository

import database.RecipeDao
import database.RecipeEntity

class RecipeRepository(
    private val dao: RecipeDao
) {

    suspend fun saveRecipe(
        recipe: RecipeEntity
    ) {
        dao.insert(recipe)
    }

    suspend fun getRecipes() =
        dao.getAllRecipes()

    suspend fun deleteRecipe(
        recipe: RecipeEntity
    ) {
        dao.delete(recipe)
    }
}