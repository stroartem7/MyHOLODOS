package ui

import utils.parseRecipe
import ui.RecipeCard
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.lazy.LazyRow
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myholodos.BuildConfig
import kotlinx.coroutines.launch

import model.ChatMessage

import com.example.myholodos.api.RetrofitClient
import com.example.myholodos.api.ChatRequest
import com.example.myholodos.api.Message
import com.example.myholodos.api.CompletionOptions
import repository.RecipeRepository
import database.RecipeEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavHostController,
    repository: RecipeRepository
) {

    var userText by rememberSaveable {
        mutableStateOf("")
    }

    val messages = remember {
        mutableStateListOf<ChatMessage>()
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {

        messages.add(
            ChatMessage(
                text = """
👋 Привет!

Я AI-повар MyHOLODOS 🍳

Могу:
• подобрать блюдо
• придумать рецепт
• подсказать что приготовить
• помочь из продуктов в холодильнике 😄
""",
                isUser = false
            )
        )
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    fun sendMessage() {

        if (userText.isBlank()) return

        val text = userText

        messages.add(
            ChatMessage(
                text = text,
                isUser = true
            )
        )


        userText = ""

        keyboardController?.hide()

        scope.launch {

            isLoading = true

            try {

                val historyMessages = messages.map {

                    Message(
                        role =
                            if (it.isUser)
                                "user"
                            else
                                "assistant",

                        text = it.text
                    )
                }

                val response =
                    RetrofitClient.api.getRecommendation(
                        auth = "Api-Key ${BuildConfig.YANDEX_API_KEY}",
                        folderId = "b1g3ll84a1ad7pu19hit",
                        request = ChatRequest(
                            modelUri = "gpt://b1g3ll84a1ad7pu19hit/yandexgpt/latest",

                            completionOptions = CompletionOptions(
                                temperature = 0.7,
                                maxTokens = "2000"
                            ),

                            messages = listOf(

                                Message(
                                    role = "system",
                                    text = """
Ты AI-повар приложения MyHOLODOS.

Если пользователь просит рецепт,
всегда отвечай строго так:

РЕЦЕПТ:
Название блюда

ВРЕМЯ:
25 мин

КАЛОРИИ:
420 ккал

ИНГРЕДИЕНТЫ:
- ингредиент 1
- ингредиент 2
- ингредиент 3

ШАГИ:
1. шаг
2. шаг
3. шаг

Не используй другой формат.
"""
                                )

                            ) + historyMessages
                        )
                    )

                val aiText =
                    response.result
                        .alternatives
                        .first()
                        .message
                        .text

                messages.add(
                    ChatMessage(
                        text = "",
                        isUser = false
                    )
                )

                val aiIndex = messages.lastIndex

                for (char in aiText.toCharArray()) {

                    delay(8)

                    messages[aiIndex] =
                        ChatMessage(
                            text = messages[aiIndex].text + char,
                            isUser = false
                        )
                }

            } catch (e: Exception) {

                messages.add(
                    ChatMessage(
                        text = "Ошибка: ${e.message}",
                        isUser = false
                    )
                )

            } finally {

                isLoading = false
            }
        }
    }

    var animatedDots by remember {
        mutableStateOf(".")
    }

    LaunchedEffect(isLoading) {

        while (isLoading) {

            animatedDots =
                when (animatedDots) {
                    "." -> ".."
                    ".." -> "..."
                    else -> "."
                }

            delay(400)
        }
    }

    val suggestions = listOf(
        "🍳 Быстрый завтрак",
        "🥗 ПП рецепт",
        "🍝 Итальянская кухня",
        "🧊 Что приготовить из холодильника?"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF111827),
                        Color(0xFF1E293B)
                    )
                )
            )
            .imePadding()
            .padding(16.dp)
    ) {

        Text(
            text = "MyHOLODOS AI ✨",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(messages) { message ->

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { it / 2 }
                    )
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            if (message.isUser)
                                Arrangement.End
                            else
                                Arrangement.Start
                    ) {

                        if (!message.isUser) {

                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .shadow(
                                        elevation = 16.dp,
                                        shape = RoundedCornerShape(100.dp)
                                    )
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFF8B5CF6),
                                                Color(0xFF6366F1),
                                                Color(0xFF06B6D4)
                                            )
                                        ),
                                        shape = RoundedCornerShape(100.dp)
                                    ),

                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "✦",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))
                        }

                        Card(
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor =
                                    if (message.isUser)
                                        Color(0xFF7C3AED)
                                    else
                                        Color(0xFF1F2937)
                            ),
                            modifier = Modifier
                                .shadow(12.dp, RoundedCornerShape(24.dp))
                                .widthIn(max = 320.dp)
                        ) {

                            if (
                                !message.isUser &&
                                message.text.contains("РЕЦЕПТ:")
                            ) {

                                val recipe = parseRecipe(message.text)

                                RecipeCard(
                                    title = recipe.title,
                                    time = recipe.time,
                                    calories = recipe.calories,
                                    ingredients = recipe.ingredients,
                                    steps = recipe.steps,
                                    onSaveClick = {

                                        scope.launch {

                                            repository.saveRecipe(
                                                RecipeEntity(
                                                    title = recipe.title,
                                                    time = recipe.time,
                                                    calories = recipe.calories,
                                                    ingredients = recipe.ingredients,
                                                    steps = recipe.steps
                                                )
                                            )
                                        }
                                    }
                                )

                            } else {

                                Text(
                                    text = message.text.replace("\\n", "\n"),
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.White
                                )
                            }


                        } // Card

                        if (message.isUser) {
                            Text(
                                text = "🧑",
                                modifier = Modifier.padding(start = 6.dp),
                                color = Color.White
                            )
                        }

                    } // Row
                } // AnimatedVisibility
            } // items
        } // LazyColumn

        if (isLoading) {

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .widthIn(max = 320.dp),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1F2937)
                )
            ) {

                Text(
                    text = "🤖 AI печатает$animatedDots",
                    modifier = Modifier.padding(14.dp),
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(suggestions) { suggestion ->

                AssistChip(
                    onClick = {

                        userText = suggestion

                        sendMessage()
                    },

                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFF7C3AED),
                        labelColor = Color.White
                    ),

                    label = {
                        Text(
                            text = suggestion,
                            color = Color.White
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

                TextField(
                    value = userText,

                    onValueChange = {
                        userText = it
                    },

                    modifier = Modifier.weight(1f),

                    shape = RoundedCornerShape(24.dp),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF1F2937),
                        unfocusedContainerColor = Color(0xFF1F2937),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),

                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),

                    keyboardActions = KeyboardActions(
                        onSend = {
                            sendMessage()
                        }
                    ),

                    placeholder = {
                        Text("Напиши сообщение...")
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = {
                        sendMessage()
                    },
                    containerColor = Color(0xFF7C3AED)
                ) {
                    Text("➤")
                }
            }
        }
    }



