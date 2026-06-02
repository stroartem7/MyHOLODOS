package ui


import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.myholodos.ShoppingCart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen() {

    val items = ShoppingCart.items

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Список покупок 🛒") })
        }
    ) { padding ->

        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Пока ничего нет 😢")
            }
        } else {
            LazyColumn(
                contentPadding = padding,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                items(items) { dish ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column {
                                Text(dish.title, fontWeight = FontWeight.Bold)
                                Text(dish.info, color = Color.Gray)
                            }

                            Button(onClick = {
                                ShoppingCart.items.remove(dish)
                            }) {
                                Text("Удалить")
                            }
                        }
                    }
                }
            }
        }
    }
}