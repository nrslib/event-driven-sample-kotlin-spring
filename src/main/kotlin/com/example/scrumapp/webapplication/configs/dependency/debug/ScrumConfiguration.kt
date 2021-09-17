package com.example.scrumapp.webapplication.configs.dependency.debug

import com.example.scrumapp.scrum.application.services.backlog.BacklogApplicationService
import com.example.scrumapp.scrum.application.services.backlog.query.BacklogQueryService
import com.example.scrumapp.scrum.application.services.task.TaskApplicationService
import com.example.scrumapp.scrum.application.services.task.query.TaskQueryService
import com.example.scrumapp.scrum.domain.models.task.TaskRepository
import com.example.scrumapp.scrum.domain.models.user.UserContext
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryRepository
import com.example.scrumapp.scrum.inmemoryinfrastructure.persistence.task.InMemoryTaskRepository
import com.example.scrumapp.scrum.inmemoryinfrastructure.persistence.userstory.InMemoryUserStoryRepository
import com.example.scrumapp.scrum.inmemoryinfrastructure.queryservices.InMemoryBacklogQueryService
import com.example.scrumapp.scrum.inmemoryinfrastructure.queryservices.InMemoryTaskQueryService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.context.annotation.RequestScope
import com.example.scrumapp.notification.domain.models.task.TaskRepository as NotificationTaskRepository

@Configuration
@Profile("debug")
class ScrumConfiguration(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val userContext: UserContext,
    private val notificationTaskRepository: NotificationTaskRepository
) {
    @Bean
    fun userStoryRepository(): UserStoryRepository {
        return InMemoryUserStoryRepository(applicationEventPublisher)
    }

    @Bean
    fun scrumTaskRepository(): TaskRepository {
        return InMemoryTaskRepository(applicationEventPublisher, notificationTaskRepository)
    }

    @Bean
    @RequestScope
    fun backlogQueryService(): BacklogQueryService {
        return InMemoryBacklogQueryService(userStoryRepository() as InMemoryUserStoryRepository)
    }

    @Bean
    @RequestScope
    fun backlogApplicationService(): BacklogApplicationService {
        return BacklogApplicationService(userStoryRepository())
    }

    @Bean
    @RequestScope
    fun taskQueryService(): TaskQueryService {
        return InMemoryTaskQueryService(scrumTaskRepository() as InMemoryTaskRepository)
    }

    @Bean
    @RequestScope
    fun taskApplicationService(): TaskApplicationService {
        return TaskApplicationService(
            applicationEventPublisher,
            userContext,
            scrumTaskRepository(),
            userStoryRepository()
        )
    }
}