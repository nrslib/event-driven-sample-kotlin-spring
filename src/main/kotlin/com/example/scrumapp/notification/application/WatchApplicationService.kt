package com.example.scrumapp.notification.application

import com.example.scrumapp.notification.domain.models.task.TaskId
import com.example.scrumapp.notification.domain.models.task.TaskRepository
import com.example.scrumapp.notification.domain.models.watch.WatchRepository
import com.example.scrumapp.notification.domain.models.watcher.MailAddress
import com.example.scrumapp.notification.domain.models.watcher.Watcher
import com.example.scrumapp.notification.domain.models.watcher.WatcherId
import com.example.scrumapp.notification.domain.models.watcher.WatcherRepository
import javassist.NotFoundException
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.util.*

open class WatchApplicationService(
    private val watcherRepository: WatcherRepository,
    private val watchRepository: WatchRepository,
    private val taskRepository: TaskRepository
) {
    open fun getWatchers(): List<Watcher> {
        return watcherRepository.findAll()
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    open fun register(mailRaw: String) {
        val mail = MailAddress(mailRaw)
        if (watcherRepository.exists(mail)) {
            return
        }

        val watcher = Watcher(
            WatcherId(UUID.randomUUID().toString()),
            mail
        )
        watcherRepository.save(watcher)
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    open fun watch(taskId: String, watcherId: String) {
        val watcher = watcherRepository.find(WatcherId(watcherId))
            ?: throw NotFoundException("watcher not found. (id:$watcherId)")
        val task = taskRepository.find(TaskId(taskId)) ?: throw NotFoundException("task not found. (id:$taskId)")
        val watch = watcher.watch(task)
        if (watchRepository.exists(watch)) {
            return
        }

        watchRepository.save(watch)
    }
}