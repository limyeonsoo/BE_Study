package com.paul.kotlin.todos.tasks.springdata

import com.paul.kotlin.todos.tasks.TaskStatus
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.util.*
import javax.annotation.processing.Generated
import javax.persistence.*

@Table("TASKS")
data class TaskEntity(
    @Id
    val id: Long?,
    val taskUUID: UUID,
    val details: String,
    val taskStatus: TaskStatus,
)
