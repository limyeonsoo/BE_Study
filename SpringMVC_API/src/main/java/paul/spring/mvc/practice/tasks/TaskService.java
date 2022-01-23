package paul.spring.mvc.practice.tasks;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    UUID insert(TaskAttributesInsert taskAttributesInsert);

    void delete (UUID taskId);

    TaskAttributes update(UUID taskId, TaskAttributes taskAttributes);

    TaskAttributes patch(UUID taskID, TaskAttributesPatch taskAttributesPatch);

    Optional<TaskAttributes> select(UUID taskId);

    List<Task> selectAll();

    class NoEntityException extends RuntimeException {

        public NoEntityException() {
            super();
        }

        public NoEntityException(final Throwable e) {
            super(e);
        }

        public NoEntityException(final String message) {
            super(message);
        }
    }
}
