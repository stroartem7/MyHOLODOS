package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.remember
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.myholodos.R
import com.example.myholodos.ShoppingCart
import model.Dish
import model.DishItem
import com.example.myholodos.api.RetrofitClient
import com.example.myholodos.api.ChatRequest
import com.example.myholodos.api.Message
import com.example.myholodos.api.CompletionOptions
import com.example.myholodos.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavHostController) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val dishes = listOf(
        Dish("Паста Карбонара", "20 мин • 450 ккал", R.drawable.pasta),
        Dish("Бургер", "15 мин • 700 ккал", R.drawable.burger),
        Dish("Салат Цезарь", "10 мин • 300 ккал", R.drawable.salad),
        Dish("Суп Том Ям", "25 мин • 350 ккал", R.drawable.soup)
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // ✅ ВОТ ЗДЕСЬ КНОПКА
            Button(
                onClick = {

                    scope.launch {

                        try {

                            val response = RetrofitClient.api.getRecommendation(
                                auth = "Api-Key ${BuildConfig.YANDEX_API_KEY}",
                                folderId = "b1g3ll84a1ad7pu19hit",
                                request = ChatRequest(
                                    modelUri = "gpt://b1g3ll84a1ad7pu19hit/yandexgpt/latest",
                                    completionOptions = CompletionOptions(),
                                    messages = listOf(
                                        Message(
                                            role = "user",
                                            text = "Посоветуй одно блюдо из списка: ${
                                                dishes.joinToString { it.title }
                                            }"
                                        )
                                    )
                                )
                            )

                            val result =
                                response.result.alternatives.first().message.text

                            snackbarHostState.showSnackbar("🤖 $result")

                        } catch (e: Exception) {

                            snackbarHostState.showSnackbar(
                                e.toString() ?: "Неизвестная ошибка"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("🤖 Подобрать блюдо")
            }

            // ✅ А ВОТ СПИСОК
            LazyColumn {
                items(dishes) { dish ->

                    DishItem(
                        dish = dish,
                        onAddClick = {
                            ShoppingCart.items.add(dish)

                            scope.launch {
                                snackbarHostState.showSnackbar("${dish.title} добавлено 🛒")
                            }
                        }
                    )
                }
            }
        }
    }
}

