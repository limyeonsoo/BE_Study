package paul.spring.mvc.practice.controller;

import static java.util.Objects.requireNonNull;

class TaskIdResponse {
    private final String taskId;

    TaskIdResponse(final String taskId) {
        this.taskId = requireNonNull(taskId);
    }

    public String getTaskId() { return taskId; }
}
