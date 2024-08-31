package com.example.task.presentation.main

import com.example.task.domain.entities.Item
import com.example.task.domain.entities.ServerData

data class MainScreenState(
    val initialData: ServerData = ServerData(),
    val currentItem: Item = Item(),
    val id: Int = 0,
    val size: Int = 0
)
