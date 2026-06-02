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
}