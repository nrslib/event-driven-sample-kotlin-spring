package com.example.scrumapp.notification.domain.models.watch

import com.example.scrumapp.notification.domain.models.task.TaskId

interface WatchRepository {
    fun save(watch: Watch)
    fun find(taskId: TaskId): List<Watch>
    fun exists(watch: Watch): Boolean
}