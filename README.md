# Spring Boot - Quartz Scheduler Demo

A demo spring boot application used to demonostrate the capabilities of 
Quartz scheduler.

The application is built using, mainly, the following libraries:

- Spring Boot Web: to create / retrieve / list / update / start and stop schedules dynamically.
- JPA: used to store custom schedules and quartz data.
- Flyway: to manage database migrations (avoid JPA automatic creation of schemmas)
- Quartz: The scheduler framework.

