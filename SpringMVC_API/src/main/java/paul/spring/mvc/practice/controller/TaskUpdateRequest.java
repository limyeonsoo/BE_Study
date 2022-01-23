package paul.spring.mvc.practice.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

public class TaskUpdateRequest {
    private final String details;
    private final String status;

    @JsonCreator
    TaskUpdateRequest(
            @JsonProperty(value = "details", required = true) final String details,
            @JsonProperty(value = "status", required = true) final String status
    ){
        this.details = requireNonNull(details);
        this.status = requireNonNull(status);
    }

    public String getDetails() { return details; }

    public String getStatus() { return status; }
}
