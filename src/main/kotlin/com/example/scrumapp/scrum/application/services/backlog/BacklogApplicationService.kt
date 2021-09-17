package com.example.scrumapp.scrum.application.services.backlog

import com.example.scrumapp.lib.applicationsupport.exceptions.NotFoundException
import com.example.scrumapp.scrum.domain.models.userstory.UserStory
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryId
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryRepository
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.util.*


open class BacklogApplicationService(
    private val userStoryRepository: UserStoryRepository
) {
    open fun getUserStory(aId: String): UserStory? {
        val id = UserStoryId(aId)
        return userStoryRepository.find(id)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    open fun addUserStory(command: BacklogAddUserStoryCommand) {
        val id = UserStoryId(UUID.randomUUID().toString())
        val story = UserStory(id, command.story)
        userStoryRepository.save(story)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    open fun updateUserStory(command: BacklogUpdateUserStoryCommand) {
        val id = UserStoryId(command.id)
        val story = userStoryRepository.find(id) ?: throw NotFoundException("Unknown story of id: $id")
        story.modifyStory(command.story)
    }

    @Transactional
    open fun estimateUserStory(aId: String, estimation: Int) {
        val id = UserStoryId(aId)
        val story = userStoryRepository.find(id) ?: throw NotFoundException("Unknown story of id: $id")
        story.estimate(estimation)
        userStoryRepository.save(story)
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    open fun deleteUserStory(aId: String) {
        val id = UserStoryId(aId)
        val story = userStoryRepository.find(id)
        story?.let(userStoryRepository::delete)
    }
}