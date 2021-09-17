package com.example.scrumapp.scrum.inmemoryinfrastructure.queryservices

import com.example.scrumapp.scrum.application.services.backlog.query.BacklogQueryService
import com.example.scrumapp.scrum.domain.models.userstory.UserStory
import com.example.scrumapp.scrum.inmemoryinfrastructure.persistence.userstory.InMemoryUserStoryRepository

class InMemoryBacklogQueryService(private val userStoryRepository: InMemoryUserStoryRepository) : BacklogQueryService {
    override fun getAllUserStories(): List<UserStory> {
        return userStoryRepository.keyToValue.values.toList()
    }
}