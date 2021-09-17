package com.example.scrumapp.notification.inmemoryinfrastructure.persistence.watch

import com.example.scrumapp.lib.domainsupport.repository.InMemoryCrudRepository
import com.example.scrumapp.notification.domain.models.task.TaskId
import com.example.scrumapp.notification.domain.models.watch.Watch
import com.example.scrumapp.notification.domain.models.watch.WatchRepository
import org.springframework.context.ApplicationEventPublisher

class InMemoryWatchRepository(
    applicationEventPublisher: ApplicationEventPublisher
) :
    InMemoryCrudRepository<String, Watch>(applicationEventPublisher), WatchRepository {
    override fun getKey(value: Watch): String {
        return "${value.watcherId}${value.taskId}"
    }

    override fun find(taskId: TaskId): List<Watch> {
        return keyToValue.values.filter { x -> x.taskId == taskId }.toList()
    }

    override fun deepClone(value: Watch): Watch {
        return Watch(value.taskId, value.watcherId, value.type)
    }

    override fun exists(value: Watch): Boolean {
        return keyToValue.any { it.value.taskId == value.taskId && it.value.watcherId == value.watcherId }
    }
}