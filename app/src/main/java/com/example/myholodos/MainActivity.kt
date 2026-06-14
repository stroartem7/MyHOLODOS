package com.example.myholodos

import ui.HomeScreen
import ui.MenuScreen
import ui.ShoppingScreen
import ui.ChatScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import database.AppDatabase
import repository.RecipeRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "myholodos_db"
        ).build()

        val repository = RecipeRepository(
            db.recipeDao()
        )

        setContent {
            val navController = rememberNavController()

            AppNavigation(
                navController = navController,
                repository = repository
            )
        }
    }
}