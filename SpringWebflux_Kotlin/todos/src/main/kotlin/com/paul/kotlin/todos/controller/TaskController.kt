package com.paul.kotlin.todos.controller;

import com.paul.kotlin.todos.tasks.Task
import com.paul.kotlin.todos.tasks.TaskAttributeInsert
import com.paul.kotlin.todos.tasks.TaskService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody taskCreateRequest: TaskCreateRequest): Mono<TaskIdResponse> {
        val taskAttributeInsert = toTaskAttributeInsert(taskCreateRequest);
        return taskService.insert(taskAttributeInsert)
                .flatMap { Mono.just(toTaskIdResponse(it)) };
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAll(): Flux<TaskResponse> {
        val task = taskService.selectAll();
        val res = task.map { toTaskResponse(it) };
        return res;
    }

    private fun toTaskResponse(task: Task): TaskResponse {
        return TaskResponse(
                task.taskId.toString(),
                task.details,
                task.taskStatus.name
        )
    }


    private fun toTaskAttributeInsert(taskCreateRequest: TaskCreateRequest): TaskAttributeInsert {
        return TaskAttributeInsert(taskCreateRequest.details);
    }
    private fun toTaskIdResponse(task: Task): TaskIdResponse {
        return TaskIdResponse(task.taskId.toString());
    }


}
