package com.example.myholodos

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import ui.HomeScreen
import ui.MenuScreen
import ui.ShoppingScreen
import ui.ChatScreen

@Composable
fun AppNavigation(navController: NavHostController) {

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
            ChatScreen(navController)
        }
    }
}