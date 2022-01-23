package paul.spring.mvc.practice.tasks.springdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import paul.spring.mvc.practice.tasks.TaskService;

@Configuration
public class TaskServiceConfig {
    @Bean
    TaskService taskService(final TaskSpringDataRepository taskSpringDataRepository) {
        return new TaskSpringDataService(taskSpringDataRepository);
    }
}
