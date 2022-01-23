package paul.spring.mvc.practice.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

class TaskCreateRequest {
    private final String details;

    @JsonCreator
    TaskCreateRequest(
            @JsonProperty(value = "details", required = true) final String details
    ) {
        this.details = requireNonNull(details);
    }

    public String getDetails() {
        return details;
    }
}
