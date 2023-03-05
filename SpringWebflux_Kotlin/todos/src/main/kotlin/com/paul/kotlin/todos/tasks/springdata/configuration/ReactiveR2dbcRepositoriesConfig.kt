package com.paul.kotlin.todos.tasks.springdata.configuration

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator


@Configuration
@EnableR2dbcRepositories
internal class ReactiveR2dbcRepositoriesConfig: AbstractR2dbcConfiguration() {
    override fun connectionFactory(): ConnectionFactory =
            H2ConnectionFactory(H2ConnectionConfiguration.builder()
                    .inMemory("testdb")
                    .build())
}
