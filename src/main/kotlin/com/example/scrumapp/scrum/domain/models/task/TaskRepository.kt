package com.example.scrumapp.scrum.domain.models.task

interface TaskRepository {
    fun find(id: TaskId): Task?
    fun save(story: Task)
    fun delete(story: Task)
}