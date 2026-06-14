package com.example.myholodos

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@Composable
fun BottomBar(
    navController: NavHostController
) {

    NavigationBar {

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("home")
            },
            icon = {
                Text("🏠")
            },
            label = {
                Text("Главная")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("menu")
            },
            icon = {
                Text("📋")
            },
            label = {
                Text("Меню")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("cart")
            },
            icon = {
                Text("🛒")
            },
            label = {
                Text("Корзина")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("chat")
            },
            icon = {
                Text("🤖")
            },
            label = {
                Text("AI")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("favorites")
            },
            icon = {
                Text("🔖")
            },
            label = {
                Text("Избранное")
            }
        )
    }
}