package com.paul.kotlin.todos.tasks

import org.springframework.lang.Nullable

data class TaskAttributesPatch (
    @Nullable val details: String,
    @Nullable val taskStatus: TaskStatus,
)