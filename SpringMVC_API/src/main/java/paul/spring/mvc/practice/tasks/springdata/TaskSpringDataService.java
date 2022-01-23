package paul.spring.mvc.practice.tasks.springdata;

import org.springframework.dao.EmptyResultDataAccessException;
import paul.spring.mvc.practice.tasks.*;
import java.util.*;

class TaskSpringDataService implements TaskService {
    private final TaskSpringDataRepository taskSpringDataRepository;

    TaskSpringDataService(final TaskSpringDataRepository taskSpringDataRepository) {
        this.taskSpringDataRepository = taskSpringDataRepository;
    }

    @Override
    public UUID insert(TaskAttributesInsert taskAttributesInsert) {
        final var taskEntity = new TaskEntity(
                taskAttributesInsert.getDetails(),
                TaskStatus.ACTIVE
        );

        final var savedEntity = taskSpringDataRepository.save(taskEntity);

        return savedEntity.getTaskId();
    }

    @Override
    public Optional<TaskAttributes> select(UUID taskId) {

        final var retrievedEntity = taskSpringDataRepository.findById(taskId);

        return retrievedEntity.map(TaskSpringDataService::toTaskAttributes);
    }

    @Override
    public List<Task> selectAll() {
        final var retrievedEntities = taskSpringDataRepository.findAll();

        final var tasks = new ArrayList<Task>();
        for(var retrievedEntity : retrievedEntities) {
            tasks.add(toTask(retrievedEntity));
        }

        return Collections.unmodifiableList(tasks);
    }

    private Task toTask(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getTaskId(),
                taskEntity.getDetails(),
                taskEntity.getTaskStatus()
        );
    }

    @Override
    public TaskAttributes update(UUID taskId, TaskAttributes taskAttributes) {
        final var existingData = taskSpringDataRepository.findById(taskId);
        if(existingData.isEmpty()) {
            throw new NoEntityException();
        }

        final var newEntity = new TaskEntity(
                taskId,
                taskAttributes.getDetails(),
                taskAttributes.getTaskStatus()
        );

        final var updatedEntity = taskSpringDataRepository.save(newEntity);
        return new TaskAttributes(
                updatedEntity.getDetails(),
                updatedEntity.getTaskStatus()
        );
    }


    @Override
    public void delete(UUID taskId) {
        try {
            taskSpringDataRepository.deleteById(taskId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityException(e);
        }
    }


    @Override
    public TaskAttributes patch(UUID taskId, TaskAttributesPatch taskAttributesPatch) {
        final var existingData = taskSpringDataRepository.findById(taskId);

        final var newEntity = existingData.map(it -> new TaskEntity(
                it.getTaskId(),
                // Objects.requireNonNull(taskAttributesPatch.getDetails()),
                // Objects.requireNonNull(taskAttributesPatch.getTaskStatus())
                taskAttributesPatch.getDetails() == null ? existingData.get().getDetails() : taskAttributesPatch.getDetails(),
                taskAttributesPatch.getTaskStatus() == null ? existingData.get().getTaskStatus() : taskAttributesPatch.getTaskStatus()
            ))
            .orElseThrow(NoEntityException::new);

        final var patchedEntity = taskSpringDataRepository.save(newEntity);

        return toTaskAttributes(patchedEntity);
    }

    static TaskAttributes toTaskAttributes(final TaskEntity taskEntity) {
        return new TaskAttributes(
                taskEntity.getDetails(),
                taskEntity.getTaskStatus()
        );
    }
}
