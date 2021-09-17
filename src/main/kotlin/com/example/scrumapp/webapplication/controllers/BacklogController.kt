package com.example.scrumapp.webapplication.controllers

import com.example.scrumapp.scrum.application.services.backlog.BacklogAddUserStoryCommand
import com.example.scrumapp.scrum.application.services.backlog.BacklogApplicationService
import com.example.scrumapp.scrum.application.services.backlog.BacklogUpdateUserStoryCommand
import com.example.scrumapp.scrum.application.services.backlog.query.BacklogQueryService
import com.example.scrumapp.webapplication.models.backlog.estimate.BacklogEstimateRequestModel
import com.example.scrumapp.webapplication.models.backlog.get.BacklogGetResponseModel
import com.example.scrumapp.webapplication.models.backlog.get.UserStoryModel
import com.example.scrumapp.webapplication.models.backlog.index.BacklogIndexResponseModel
import com.example.scrumapp.webapplication.models.backlog.index.UserStorySummaryModel
import com.example.scrumapp.webapplication.models.backlog.post.BacklogPostRequestModel
import com.example.scrumapp.webapplication.models.backlog.put.BacklogPutRequestModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/backlog")
class BacklogController(
    private val backlogQueryService: BacklogQueryService,
    private val backlogApplicationService: BacklogApplicationService
) {
    @GetMapping
    fun index(): BacklogIndexResponseModel {
        val stories = backlogQueryService.getAllUserStories()
        val summaries = stories.map { UserStorySummaryModel(it.id.value) }.toList()

        return BacklogIndexResponseModel(summaries)
    }

    @GetMapping("/{id}")
    operator fun get(@PathVariable("id") id: String): BacklogGetResponseModel? {
        val testUser = backlogApplicationService.getUserStory(id)
        return testUser?.let {
            BacklogGetResponseModel(
                UserStoryModel(
                    it.id.value,
                    it.story
                )
            )
        }
    }

    @PostMapping
    fun post(@RequestBody request: BacklogPostRequestModel) {
        val command = BacklogAddUserStoryCommand(request.story)
        backlogApplicationService.addUserStory(command)
    }

    @PutMapping("/{id}")
    fun put(@PathVariable("id") id: String, @RequestBody request: BacklogPutRequestModel) {
        backlogApplicationService.updateUserStory(BacklogUpdateUserStoryCommand(id, request.story))
    }

    @PutMapping("/{id}/estimate")
    fun estimate(@PathVariable("id") id: String, @RequestBody request: BacklogEstimateRequestModel) {
        backlogApplicationService.estimateUserStory(id, request.estimation)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) {
        backlogApplicationService.deleteUserStory(id)
    }
}