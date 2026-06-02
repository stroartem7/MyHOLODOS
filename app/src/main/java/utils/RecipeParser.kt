package utils

import model.RecipeData

fun parseRecipe(text: String): RecipeData {

    val title =
        text.substringAfter("РЕЦЕПТ:")
            .substringBefore("ВРЕМЯ:")
            .trim()

    val time =
        text.substringAfter("ВРЕМЯ:")
            .substringBefore("КАЛОРИИ:")
            .trim()

    val calories =
        text.substringAfter("КАЛОРИИ:")
            .substringBefore("ИНГРЕДИЕНТЫ:")
            .trim()

    val ingredients =
        text.substringAfter("ИНГРЕДИЕНТЫ:")
            .substringBefore("ШАГИ:")
            .trim()

    val steps =
        text.substringAfter("ШАГИ:")
            .trim()

    return RecipeData(
        title = title,
        time = time,
        calories = calories,
        ingredients = ingredients,
        steps = steps
    )
}