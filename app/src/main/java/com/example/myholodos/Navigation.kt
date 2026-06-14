package com.example.myholodos

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import ui.HomeScreen
import ui.MenuScreen
import ui.ShoppingScreen
import ui.ChatScreen
import repository.RecipeRepository
import ui.RecipeDetailsScreen
import ui.FavoritesScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    repository: RecipeRepository
) {

    var selectedRecipe: database.RecipeEntity? = null

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController)
        }

        composable("menu") {
            MenuScreen(navController)
        }

        composable("cart") {
            ShoppingScreen()
        }

        composable("chat") {
            ChatScreen(
                navController = navController,
                repository = repository
            )
        }

        composable("favorites") {
            FavoritesScreen(
                repository = repository,
                navController = navController
            )
        }

        composable("recipe_details") {
            RecipeDetailsScreen()
        }
    }
}