package com.example.scrumapp.scrum.application.services.backlog.query

import com.example.scrumapp.scrum.domain.models.userstory.UserStory

interface BacklogQueryService {
    fun getAllUserStories(): List<UserStory>
}