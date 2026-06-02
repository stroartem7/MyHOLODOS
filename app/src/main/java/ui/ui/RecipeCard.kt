package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RecipeCard(

    title: String,

    time: String,

    calories: String,

    ingredients: String,

    steps: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E293B),
                            Color(0xFF0F172A)
                        )
                    )
                )
                .padding(18.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {

                AssistChip(
                    onClick = {},
                    label = {
                        Text("⏱ $time")
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                AssistChip(
                    onClick = {},
                    label = {
                        Text("🔥 $calories")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "🧾 Ингредиенты",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = ingredients,
                color = Color(0xFFD1D5DB)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "👨‍🍳 Приготовление",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = steps,
                color = Color(0xFFD1D5DB)
            )
        }
    }
}