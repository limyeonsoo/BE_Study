package com.paul.kotlin.todos.tasks.springdata

import com.paul.kotlin.todos.tasks.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

class TaskSpringDataService(private val taskSpringDataRepository: TaskSpringDataRepository): TaskService{
    override fun insert(taskAttributeInsert: TaskAttributeInsert): Mono<Task>{
        val taskEntity = TaskEntity(
            null,
            UUID.randomUUID(),
            taskAttributeInsert.details,
            TaskStatus.ACTIVE
        );
        val savedEntity = taskSpringDataRepository.save(taskEntity);
        return savedEntity.flatMap { Mono.just(Task(it.taskUUID, it.details, it.taskStatus)) };
    }

    override fun delete(taskId: UUID) {
        // TODO("Not yet implemented")
    }

    override fun update(taskId: UUID, taskAttributes: TaskAttributes) {
       //  TODO("Not yet implemented")
    }

    override fun patch(taskId: UUID, taskAttributesPatch: TaskAttributesPatch) {
        // TODO("Not yet implemented")
    }

    override fun select(taskId: UUID) {
        // TODO("Not yet implemented")
    }

    override fun selectAll(): Flux<Task> {
        return taskSpringDataRepository.findAll()
                .map { it: TaskEntity -> Task(
                        it.taskUUID,
                        it.details,
                        it.taskStatus
                )}
        // TODO("Not yet implemented")
    }


}
