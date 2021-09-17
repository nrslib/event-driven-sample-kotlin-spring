package com.example.scrumapp.notification.domain.models.task

interface TaskRepository {
    fun find(id: TaskId): Task?
    fun save(task: Task)
}