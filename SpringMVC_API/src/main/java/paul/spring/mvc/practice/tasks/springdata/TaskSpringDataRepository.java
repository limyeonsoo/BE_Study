package paul.spring.mvc.practice.tasks.springdata;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface TaskSpringDataRepository extends CrudRepository<TaskEntity, UUID> {
}
