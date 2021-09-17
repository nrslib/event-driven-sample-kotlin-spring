package com.example.scrumapp.notification.handler

import com.example.scrumapp.notification.application.NotificationApplicationService
import com.example.scrumapp.shared.scrum.models.task.events.TaskCreatedEvent
import com.example.scrumapp.shared.scrum.models.task.events.TaskStatusChangedEvent
import org.springframework.transaction.event.TransactionalEventListener

class NotificationEventHandler(private val notificationApplicationService: NotificationApplicationService) {
    @TransactionalEventListener
    fun onTaskCreated(event: TaskCreatedEvent) {
        // nop
    }

    @TransactionalEventListener
    fun onTaskStatusChanged(event: TaskStatusChangedEvent) {
        notificationApplicationService.notifyChangeStatus(event.taskId.value, event.from, event.to)
    }
}