package paul.spring.mvc.practice.tasks;

public class TaskAttributesPatch {
    private final String details;
    private final TaskStatus taskStatus;

    public TaskAttributesPatch(
            final String details,
            final TaskStatus taskStatus) {
        this.details = details;
        this.taskStatus = taskStatus;
    }

    public String getDetails() { return details; }
    public TaskStatus getTaskStatus() { return taskStatus; }
}
