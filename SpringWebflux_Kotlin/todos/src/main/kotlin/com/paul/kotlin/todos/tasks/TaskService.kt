package com.paul.kotlin.todos.tasks;

import com.paul.kotlin.todos.tasks.springdata.TaskEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID;

interface TaskService {
    fun insert(taskAttributeInsert: TaskAttributeInsert): Mono<Task>;
    // UUID insert(TaskAttributesInsert taskAttributesInsert);

    fun delete(taskId: UUID);
    // void delete(UUID taskId);

    fun update(taskId: UUID, taskAttributes: TaskAttributes);
    // TaskAttributes update(UUID taskID, TaskAttributes taskAttributes);

    fun patch(taskId: UUID, taskAttributesPatch: TaskAttributesPatch);
    // TaskAttributes patch(UUID taskID, TaskAttributesPatch taskAttributesPatch);

    fun select(taskId: UUID);
    // Optional<TaskAttributes> select(UUID taskId);

    fun selectAll(): Flux<Task>;
    // List<Task> selectAll();

    class NoEntityException: RuntimeException {
        constructor(): super() {}

        constructor(e: Exception): super(e) {}

        constructor(message: String): super(message) {}
    }
}
