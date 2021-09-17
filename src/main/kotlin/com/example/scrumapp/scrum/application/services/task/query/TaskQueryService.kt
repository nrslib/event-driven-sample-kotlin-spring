package com.example.scrumapp.scrum.application.services.task.query

import com.example.scrumapp.scrum.domain.models.task.Task

interface TaskQueryService {
    fun getAllTasks(): List<Task>
}