package paul.spring.mvc.practice.tasks;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class TaskAttributes {
    private final String details;
    private final TaskStatus taskStatus;

    public TaskAttributes(
            final String details,
            final TaskStatus taskStatus) {
        this.details = requireNonNull(details);
        this.taskStatus = requireNonNull(taskStatus);
    }

    public String getDetails() { return details; }
    public TaskStatus getTaskStatus() { return taskStatus; }
}
