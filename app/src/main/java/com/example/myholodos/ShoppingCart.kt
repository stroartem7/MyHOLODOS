package com.example.myholodos

import androidx.compose.runtime.mutableStateListOf
import model.Dish

object ShoppingCart {
    val items = mutableStateListOf<Dish>()
}
