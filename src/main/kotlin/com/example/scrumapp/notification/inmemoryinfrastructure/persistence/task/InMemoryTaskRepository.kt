package com.example.scrumapp.notification.inmemoryinfrastructure.persistence.task

import com.example.scrumapp.lib.domainsupport.repository.InMemoryCrudRepository
import com.example.scrumapp.notification.domain.models.task.Task
import com.example.scrumapp.notification.domain.models.task.TaskId
import com.example.scrumapp.notification.domain.models.task.TaskRepository
import org.springframework.context.ApplicationEventPublisher

class InMemoryTaskRepository(
    applicationEventPublisher: ApplicationEventPublisher
) : InMemoryCrudRepository<TaskId, Task>(applicationEventPublisher), TaskRepository {
    override fun getKey(value: Task): TaskId {
        return value.id
    }

    override fun deepClone(value: Task): Task {
        return Task(value.id, value.progressStatus)
    }
}