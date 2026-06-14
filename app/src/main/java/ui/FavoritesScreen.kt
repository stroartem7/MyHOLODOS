package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.RecipeEntity
import kotlinx.coroutines.launch
import repository.RecipeRepository
import androidx.navigation.NavHostController
import com.example.myholodos.SelectedRecipe
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    repository: RecipeRepository,
    navController: NavHostController
) {

    var recipes by remember {
        mutableStateOf<List<RecipeEntity>>(emptyList())
    }

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        recipes = repository.getRecipes()
    }

    Scaffold(

        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "🔖 Избранные рецепты",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {

                items(recipes) { recipe ->

                    Card(
                        onClick = {

                            SelectedRecipe.recipe = recipe

                            navController.navigate("recipe_details")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {

                                TextButton(
                                    onClick = {

                                        scope.launch {

                                            repository.deleteRecipe(recipe)

                                            recipes = repository.getRecipes()

                                            snackbarHostState.showSnackbar(
                                                message = "Рецепт удалён ✅"
                                            )
                                        }
                                    }
                                ) {
                                    Text("🗑 Удалить")
                                }
                            }

                            Text(
                                text = recipe.title,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("⏱ ${recipe.time}")
                            Text("🔥 ${recipe.calories}")
                        }
                    }
                }
            }
        }
    }
}