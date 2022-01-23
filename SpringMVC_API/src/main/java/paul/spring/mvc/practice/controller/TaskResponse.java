package paul.spring.mvc.practice.controller;

import static java.util.Objects.requireNonNull;

class TaskResponse extends TaskIdResponse{
    private final String details;
    private final String status;

    TaskResponse(
            final String id,
            final String details,
            final String status
    ) {
        super(id);
        this.details = requireNonNull(details);
        this.status = requireNonNull(status);
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }
}
