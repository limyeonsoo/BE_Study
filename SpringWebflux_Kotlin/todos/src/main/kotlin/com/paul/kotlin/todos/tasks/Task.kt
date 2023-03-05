package com.paul.kotlin.todos.tasks;

import java.util.*

data class Task(
    val taskId: UUID,
    val details: String,
    val taskStatus: TaskStatus,
)
