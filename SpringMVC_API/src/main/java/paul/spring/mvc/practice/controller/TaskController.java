package paul.spring.mvc.practice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paul.spring.mvc.practice.tasks.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    TaskController(final TaskService taskService) { this.taskService = taskService; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskIdResponse create(@RequestBody final TaskCreateRequest taskCreateRequest) {
        final var taskAttributesInsert = toTaskAttributeInsert(taskCreateRequest);
        final var createdTaskId = taskService.insert(taskAttributesInsert);
        return toTaskIdResponse(createdTaskId);
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskAttributesResponse> retrieveById(
            @PathVariable("id") final String id
    ) {
       final var taskId = toTaskId(id);

       final var retrievedData = taskService.select(taskId);

       return ResponseEntity.of(retrievedData.map(TaskController::toTaskAttributesResponse));
    }

    @GetMapping
    List<TaskResponse> retrieveAll(){
        final var retrievedData = taskService.selectAll();
        return retrievedData.stream()
                .map(TaskController::toTaskResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    @PutMapping("/{id}")
    TaskAttributesResponse update(
            @PathVariable("id") final String id,
            @RequestBody final TaskUpdateRequest taskUpdateRequest
    ) {
        final var taskId = toTaskId(id);
        final var taskAttributes = toTaskAttributeUpdate(taskUpdateRequest);

        final var updateTask = taskService.update(taskId, taskAttributes);
        return toTaskAttributesResponse(updateTask);
    }

    @PatchMapping("/{id}")
    TaskAttributesResponse patch(
            @PathVariable("id") final String id,
            @RequestBody TaskPatchRequest taskPatchRequest
    ) {
        final var taskId = toTaskId(id);
        final var taskAttributesPatch = toTaskAttributePatch(taskPatchRequest);

        final var patchedTask = taskService.patch(taskId, taskAttributesPatch);
        return toTaskAttributesResponse(patchedTask);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") final String id) {
        final var taskId = toTaskId(id);

        taskService.delete(taskId);
    }

    static TaskAttributesPatch toTaskAttributePatch(TaskPatchRequest taskPatchRequest) {
        final TaskStatus status = TaskStatus.valueOf(taskPatchRequest.getStatus());
        return new TaskAttributesPatch(
                taskPatchRequest.getDetails(),
                status
        );
    }

    static TaskAttributesInsert toTaskAttributeInsert(final TaskCreateRequest taskCreateRequest) {
        return new TaskAttributesInsert(taskCreateRequest.getDetails());
    }

    static TaskIdResponse toTaskIdResponse(final UUID taskId) {
        return new TaskIdResponse(taskId.toString());
    }

    static TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getTaskId().toString(),
                task.getDetails(),
                task.getTaskStatus().name().toLowerCase(Locale.ENGLISH)
        );
    }

    static TaskAttributesResponse toTaskAttributesResponse(TaskAttributes taskAttribute) {
        return new TaskAttributesResponse(
                taskAttribute.getDetails(),
                taskAttribute.getTaskStatus().name().toLowerCase(Locale.ENGLISH)
        );
    }

    static TaskAttributes toTaskAttributeUpdate(TaskUpdateRequest taskUpdateRequest) {
        return new TaskAttributes(
                taskUpdateRequest.getDetails(),
                TaskStatus.valueOf(taskUpdateRequest.getStatus().toUpperCase(Locale.ENGLISH)));
    }

    static UUID toTaskId(String id) {
        return UUID.fromString(id);
    }
}
