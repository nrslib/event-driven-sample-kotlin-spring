package com.example.scrumapp.scrum.inmemoryinfrastructure.queryservices

import com.example.scrumapp.scrum.application.services.task.query.TaskQueryService
import com.example.scrumapp.scrum.domain.models.task.Task
import com.example.scrumapp.scrum.inmemoryinfrastructure.persistence.task.InMemoryTaskRepository

class InMemoryTaskQueryService(private val taskRepository: InMemoryTaskRepository) : TaskQueryService {
    override fun getAllTasks(): List<Task> {
        return taskRepository.keyToValue.values.toList()
    }
}