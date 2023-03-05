package com.paul.kotlin.todos.tasks.springdata

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TaskSpringDataRepository: ReactiveCrudRepository<TaskEntity, Long>
