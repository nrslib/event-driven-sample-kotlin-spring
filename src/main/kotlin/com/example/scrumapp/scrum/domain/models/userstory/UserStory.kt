package com.example.scrumapp.scrum.domain.models.userstory

import com.example.scrumapp.scrum.domain.models.task.Description
import com.example.scrumapp.scrum.domain.models.task.Task
import com.example.scrumapp.scrum.domain.models.task.TaskId
import com.example.scrumapp.shared.scrum.models.task.ProgressStatus
import java.util.*

class UserStory(
    val id: UserStoryId,
    var story: String,
    var estimation: Int? = null
) {
    fun estimate(estimation: Int) {
        require(estimation >= 0) { "estimation must be positive numbers." }
        this.estimation = estimation
    }

    fun modifyStory(story: String) {
        this.story = story
    }

    fun createTask(description: Description): Task {
        return Task(
            TaskId(UUID.randomUUID().toString()),
            id,
            description,
            ProgressStatus.CREATED,
            ArrayList()
        )
    }
}
