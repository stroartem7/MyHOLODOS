package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.RecipeEntity
import com.example.myholodos.SelectedRecipe
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RecipeDetailsScreen() {

    val recipe = SelectedRecipe.recipe ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF334155)
                )
            ) {
                Text(
                    text = "⏱ ${recipe.time}",
                    modifier = Modifier.padding(12.dp),
                    color = Color.White
                )
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF334155)
                )
            ) {
                Text(
                    text = "🔥 ${recipe.calories}",
                    modifier = Modifier.padding(12.dp),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "🧾 Ингредиенты",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = recipe.ingredients,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "👨‍🍳 Приготовление",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = recipe.steps,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
