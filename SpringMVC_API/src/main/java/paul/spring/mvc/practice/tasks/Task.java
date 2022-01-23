package paul.spring.mvc.practice.tasks;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Task {
    private final UUID taskId;
    private final String details;
    private final TaskStatus taskStatus;

    public Task(
            final UUID taskId,
            final String details,
            final TaskStatus taskStatus) {
        this.taskId = requireNonNull(taskId);
        this.details = requireNonNull(details);
        this.taskStatus = requireNonNull(taskStatus);
    }

    public UUID getTaskId() { return taskId; }
    public String getDetails() { return details; }
    public TaskStatus getTaskStatus() { return taskStatus; }
}
