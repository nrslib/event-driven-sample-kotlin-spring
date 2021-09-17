package com.example.scrumapp.scrum.inmemoryinfrastructure.persistence.userstory

import com.example.scrumapp.lib.domainsupport.repository.InMemoryCrudRepository
import com.example.scrumapp.scrum.domain.models.userstory.UserStory
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryId
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryRepository
import org.springframework.context.ApplicationEventPublisher

class InMemoryUserStoryRepository(applicationEventPublisher: ApplicationEventPublisher) :
    InMemoryCrudRepository<UserStoryId, UserStory>(applicationEventPublisher), UserStoryRepository {
    override fun getKey(value: UserStory): UserStoryId {
        return value.id
    }

    override fun deepClone(value: UserStory): UserStory {
        return UserStory(
            value.id,
            value.story,
            value.estimation
        )
    }
}