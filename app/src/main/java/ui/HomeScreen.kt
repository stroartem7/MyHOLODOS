package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myholodos.MainButton
import com.example.myholodos.BottomBar
import androidx.compose.material3.Scaffold


@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(

        bottomBar = {
            BottomBar(navController)
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("🍔 MyHOLODOS")

            Spacer(modifier = Modifier.height(24.dp))

            MainButton(
                text = "📋 Меню"
            ) {
                navController.navigate("menu")
            }

            Spacer(modifier = Modifier.height(16.dp))

            MainButton(
                text = "🛒 Корзина"
            ) {
                navController.navigate("cart")
            }

            Spacer(modifier = Modifier.height(16.dp))

            MainButton(
                text = "🤖 AI Чат"
            ) {
                navController.navigate("chat")
            }
        }
    }
}