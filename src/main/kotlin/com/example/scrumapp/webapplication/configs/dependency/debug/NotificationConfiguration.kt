package com.example.scrumapp.webapplication.configs.dependency.debug

import com.example.scrumapp.notification.application.NotificationApplicationService
import com.example.scrumapp.notification.application.WatchApplicationService
import com.example.scrumapp.notification.domain.models.task.TaskRepository
import com.example.scrumapp.notification.domain.models.watch.WatchRepository
import com.example.scrumapp.notification.domain.models.watcher.WatcherRepository
import com.example.scrumapp.notification.handler.NotificationEventHandler
import com.example.scrumapp.notification.infrastructure.service.MailService
import com.example.scrumapp.notification.inmemoryinfrastructure.persistence.task.InMemoryTaskRepository
import com.example.scrumapp.notification.inmemoryinfrastructure.persistence.watch.InMemoryWatchRepository
import com.example.scrumapp.notification.inmemoryinfrastructure.persistence.watcher.InMemoryWatcherRepository
import com.example.scrumapp.notification.inmemoryinfrastructure.service.NopMailService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.context.annotation.RequestScope

@Configuration
@Profile("debug")
class NotificationConfiguration(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Bean
    fun notificationTaskRepository(): TaskRepository {
        return InMemoryTaskRepository(applicationEventPublisher)
    }

    @Bean
    fun watcherRepository(): WatcherRepository {
        return InMemoryWatcherRepository(applicationEventPublisher)
    }

    @Bean
    fun watchRepository(): WatchRepository {
        return InMemoryWatchRepository(applicationEventPublisher)
    }

    @Bean
    fun notificationEventHandler(): NotificationEventHandler {
        return NotificationEventHandler(notificationApplicationService())
    }

    @Bean
    @RequestScope
    fun notificationApplicationService(): NotificationApplicationService {
        return NotificationApplicationService(
            watcherRepository(),
            notificationTaskRepository(),
            watchRepository(),
            mailService()
        )
    }

    @Bean
    @RequestScope
    fun watchApplicationService(): WatchApplicationService {
        return WatchApplicationService(watcherRepository(), watchRepository(), notificationTaskRepository())
    }

    @Bean
    fun mailService(): MailService {
        return NopMailService()
    }
}