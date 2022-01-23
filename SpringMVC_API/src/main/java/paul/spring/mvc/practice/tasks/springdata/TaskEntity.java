package paul.spring.mvc.practice.tasks.springdata;

import paul.spring.mvc.practice.tasks.TaskStatus;

import javax.persistence.*;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID taskId;
    private String details;
    private TaskStatus taskStatus;

    TaskEntity(){}

    TaskEntity(
            final String details,
            final TaskStatus taskStatus
    ) {
        this(null, details, taskStatus);
    }

    TaskEntity(
            final UUID taskId,
            final String details,
            final TaskStatus taskStatus
    ) {
        this.taskId = taskId;
        this.details = requireNonNull(details);
        this.taskStatus = requireNonNull(taskStatus);
    }

    public UUID getTaskId() {
        return taskId;
    }

    public String getDetails() {
        return details;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
}
