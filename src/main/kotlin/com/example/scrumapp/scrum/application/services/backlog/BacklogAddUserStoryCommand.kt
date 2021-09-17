package com.example.scrumapp.scrum.application.services.backlog

data class BacklogAddUserStoryCommand(
    val story: String,
    val demo: String? = null
)