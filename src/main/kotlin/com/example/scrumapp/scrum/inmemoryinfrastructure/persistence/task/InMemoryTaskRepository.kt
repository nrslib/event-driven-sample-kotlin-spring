package com.example.scrumapp.scrum.inmemoryinfrastructure.persistence.task

import com.example.scrumapp.lib.domainsupport.repository.InMemoryCrudRepository
import com.example.scrumapp.notification.inmemoryinfrastructure.persistence.task.InMemoryTaskRepository
import com.example.scrumapp.scrum.domain.models.task.Task
import com.example.scrumapp.scrum.domain.models.task.TaskId
import com.example.scrumapp.scrum.domain.models.task.TaskRepository
import org.springframework.context.ApplicationEventPublisher
import java.util.*
import com.example.scrumapp.notification.domain.models.task.Task as NotificationTask
import com.example.scrumapp.notification.domain.models.task.TaskId as NotificationTaskId
import com.example.scrumapp.notification.domain.models.task.TaskRepository as NotificationTaskRepository

class InMemoryTaskRepository(
    applicationEventPublisher: ApplicationEventPublisher,
    private val notificationTaskRepository: NotificationTaskRepository
) : InMemoryCrudRepository<TaskId, Task>(applicationEventPublisher), TaskRepository {
    override fun getKey(value: Task): TaskId {
        return value.id
    }

    override fun deepClone(value: Task): Task {
        return Task(
            value.id,
            value.storyId,
            value.description,
            value.status,
            ArrayList(value.signupMembers)
        )
    }

    override fun onSaved(value: Task) {
        if (notificationTaskRepository is InMemoryTaskRepository) {
            val task = NotificationTask(
                NotificationTaskId(value.id.value),
                value.status
            )
            notificationTaskRepository.keyToValue[task.id] = task
        }
    }
}