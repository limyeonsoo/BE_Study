package paul.spring.mvc.practice.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TaskPatchRequest {
    private final String details;
    private final String status;

    @JsonCreator
    TaskPatchRequest(
            @JsonProperty(value = "details") final String details,
            @JsonProperty(value = "status") final String status
    ) {
        this.details = details;
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

}
