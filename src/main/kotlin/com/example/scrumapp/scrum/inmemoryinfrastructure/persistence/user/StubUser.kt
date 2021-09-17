package com.example.scrumapp.scrum.inmemoryinfrastructure.persistence.user

import com.example.scrumapp.scrum.domain.models.user.UserContext
import com.example.scrumapp.scrum.domain.models.user.UserId

class StubUser(private val id: UserId) : UserContext {
    override fun getId(): UserId {
        return id
    }
}