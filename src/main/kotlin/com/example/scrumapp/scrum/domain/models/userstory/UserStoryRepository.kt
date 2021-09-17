package com.example.scrumapp.scrum.domain.models.userstory

interface UserStoryRepository {
    fun find(id: UserStoryId): UserStory?
    fun save(story: UserStory)
    fun delete(story: UserStory)
}