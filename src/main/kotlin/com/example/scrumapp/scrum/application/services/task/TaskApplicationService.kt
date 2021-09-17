package com.example.scrumapp.scrum.application.services.task

import com.example.scrumapp.lib.applicationsupport.exceptions.NotFoundException
import com.example.scrumapp.scrum.domain.models.task.Description
import com.example.scrumapp.scrum.domain.models.task.Task
import com.example.scrumapp.scrum.domain.models.task.TaskId
import com.example.scrumapp.scrum.domain.models.task.TaskRepository
import com.example.scrumapp.scrum.domain.models.user.UserContext
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryId
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryRepository
import com.example.scrumapp.shared.scrum.models.task.ProgressStatus
import com.example.scrumapp.shared.scrum.models.task.events.TaskCreatedEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

open class TaskApplicationService(
    private val publisher: ApplicationEventPublisher,
    private val userContext: UserContext,
    private val taskRepository: TaskRepository,
    private val userStoryRepository: UserStoryRepository
) {
    open fun getTask(aId: String): Task? {
        val id = TaskId(aId)
        return taskRepository.find(id)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    open fun create(command: TaskAddCommand) {
        val storyId = UserStoryId(command.storyId)
        val story = userStoryRepository.find(storyId) ?: throw NotFoundException("Story not found. id:$storyId")
        val task = story.createTask(Description(command.description))
        publisher.publishEvent(TaskCreatedEvent(this))
        taskRepository.save(task)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    open fun signup(id: String) {
        val task = getTaskOrThrowNotFound(id)
        task.signup(userContext)
        taskRepository.save(task)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    open fun changeStatus(id: String, status: ProgressStatus) {
        val task = getTaskOrThrowNotFound(id)
        task.changeStatus(status)
        taskRepository.save(task)
    }

    private fun getTaskOrThrowNotFound(id: String): Task {
        val taskId = TaskId(id)
        return taskRepository.find(taskId) ?: throw NotFoundException("Task not found. id:$taskId")
    }
}