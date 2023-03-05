package com.paul.kotlin.todos.tasks.springdata

import com.paul.kotlin.todos.tasks.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TaskServiceConfig {

    @Bean
    @ConditionalOnMissingBean
    fun taskService(taskSpringDataRepository: TaskSpringDataRepository): TaskService {
        return TaskSpringDataService(taskSpringDataRepository);
    }
}
