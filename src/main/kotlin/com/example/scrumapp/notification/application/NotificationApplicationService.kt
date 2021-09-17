package com.example.scrumapp.notification.application

import com.example.scrumapp.lib.applicationsupport.exceptions.NotFoundException
import com.example.scrumapp.notification.domain.models.task.TaskId
import com.example.scrumapp.notification.domain.models.task.TaskRepository
import com.example.scrumapp.notification.domain.models.watch.WatchRepository
import com.example.scrumapp.notification.domain.models.watcher.WatcherRepository
import com.example.scrumapp.notification.infrastructure.service.MailService
import com.example.scrumapp.shared.scrum.models.task.ProgressStatus

open class NotificationApplicationService(
    private val watcherRepository: WatcherRepository,
    private val taskRepository: TaskRepository,
    private val watchRepository: WatchRepository,
    private val mailService: MailService
) {
    open fun notifyChangeStatus(taskId: String, from: ProgressStatus, to: ProgressStatus) {
        val id = TaskId(taskId)
        val task = taskRepository.find(id) ?: throw NotFoundException("task not found. id:$id")
        val title = "Task${task.id} status changed."
        val content = "Task status change from $from to $to"
        sendToWatcher(task.id, title, content)
    }

    private fun sendToWatcher(taskId: TaskId, title: String, content: String) {
        val watches = watchRepository.find(taskId)
        val watchers = watcherRepository.find(watches.map { it.watcherId })
        watchers.forEach {
            mailService.send(it.mailAddress.toString(), title, content)
        }
    }
}