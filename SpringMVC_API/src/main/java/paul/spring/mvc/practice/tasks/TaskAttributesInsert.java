package paul.spring.mvc.practice.tasks;

import static java.util.Objects.requireNonNull;

public class TaskAttributesInsert {
    private final String details;

    public TaskAttributesInsert(final String details) {
        this.details = requireNonNull(details);
    }

    public String getDetails() { return details; }
}
