package com.example.scrumapp.webapplication.controllers

import com.example.scrumapp.scrum.application.services.task.TaskAddCommand
import com.example.scrumapp.scrum.application.services.task.TaskApplicationService
import com.example.scrumapp.scrum.application.services.task.query.TaskQueryService
import com.example.scrumapp.webapplication.models.task.get.TaskGetResponseModel
import com.example.scrumapp.webapplication.models.task.get.TaskModel
import com.example.scrumapp.webapplication.models.task.index.TaskIndexResponseModel
import com.example.scrumapp.webapplication.models.task.index.TaskSummaryModel
import com.example.scrumapp.webapplication.models.task.patch.TaskPutRequestModel
import com.example.scrumapp.webapplication.models.task.post.TaskPostRequestModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/task")
class TaskController(
    private val taskQueryService: TaskQueryService,
    private val taskApplicationService: TaskApplicationService
) {
    @GetMapping
    fun index(): TaskIndexResponseModel {
        val tasks = taskQueryService.getAllTasks()
        val summaries = tasks.map { TaskSummaryModel(it.id.value) }
        return TaskIndexResponseModel(summaries)
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable("id") id: String): TaskGetResponseModel? {
        val task = taskApplicationService.getTask(id)
        return task?.let {
            TaskGetResponseModel(
                TaskModel(
                    it.id.value,
                    it.storyId.value,
                    it.description.value,
                    it.status,
                    it.signupMembers.map { x -> x.value })
            )
        }
    }

    @PostMapping
    fun post(@RequestBody request: TaskPostRequestModel) {
        val command = TaskAddCommand(request.storyId, request.description)
        taskApplicationService.create(command)
    }

    @PutMapping("/{id}/signup")
    fun signup(@PathVariable("id") id: String) {
        taskApplicationService.signup(id)
    }

    @PutMapping("/{id}")
    fun put(@PathVariable("id") id: String, @RequestBody request: TaskPutRequestModel) {
        val changeStatus = request.changeStatus
        if (changeStatus != null) {
            taskApplicationService.changeStatus(id, changeStatus)
        }
    }
}