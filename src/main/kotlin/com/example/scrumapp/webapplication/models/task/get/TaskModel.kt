package com.example.scrumapp.webapplication.models.task.get

import com.example.scrumapp.shared.scrum.models.task.ProgressStatus

data class TaskModel(
    val id: String,
    val userStoryId: String,
    val description: String,
    val status: ProgressStatus,
    val signupMembers: List<String>
)