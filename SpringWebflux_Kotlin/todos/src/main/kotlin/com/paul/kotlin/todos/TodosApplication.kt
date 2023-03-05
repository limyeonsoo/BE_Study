package com.paul.kotlin.todos

import io.r2dbc.spi.ConnectionFactories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@ComponentScan("com.paul")
@EnableR2dbcRepositories
class TodosApplication

fun main(args: Array<String>) {
    runApplication<TodosApplication>(*args)

    val connectionFactory = ConnectionFactories.get("r2dbc:h2:mem:///testdb");
    connectionFactory.create();

}
