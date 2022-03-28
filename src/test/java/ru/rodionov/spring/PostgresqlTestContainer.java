package ru.rodionov.spring;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class PostgresqlTestContainer {

    public static final String DOCKER_IMAGE = "postgres:alpine";
    public static final Integer POSTGRESQL_PORT = 5432;
    public static final String DB_NAME = "postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "mysecretpassword";

    public static final PostgreSQLContainer<?> postgresqlContainer;

    static {
        postgresqlContainer = new PostgreSQLContainer()
                .withDatabaseName(DB_NAME)
                .withUsername(USER)
                .withPassword(PASSWORD);
        postgresqlContainer.start();
    }
    public static PostgreSQLContainer defaultPostgresql() {
        return postgresqlContainer;
    }

    public static void applyForLiquibase(ConfigurableApplicationContext configurableApplicationContext,
                                         String currentSchema) {
        apply(configurableApplicationContext, currentSchema);
        TestPropertyValues.of(
                "spring.liquibase.url=" + defaultPostgresql().getJdbcUrl(),
                "spring.liquibase.user=" + defaultPostgresql().getUsername(),
                "spring.liquibase.password=" + defaultPostgresql().getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
    public static void apply(ConfigurableApplicationContext configurableApplicationContext,
                             String currentSchema) {
        final String baseUrl = defaultPostgresql().getJdbcUrl();
        final String delimiter = baseUrl.contains("?") ? "&" : "?";

        TestPropertyValues.of(
                "spring.datasource.url=" + baseUrl + delimiter + "currentSchema=" + currentSchema,
                "spring.datasource.username=" + defaultPostgresql().getUsername(),
                "spring.datasource.password=" + defaultPostgresql().getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}


