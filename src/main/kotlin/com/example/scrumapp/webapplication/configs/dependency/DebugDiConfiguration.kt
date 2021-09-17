package com.example.scrumapp.webapplication.configs.dependency

import com.example.scrumapp.scrum.domain.models.user.UserContext
import com.example.scrumapp.scrum.domain.models.user.UserId
import com.example.scrumapp.scrum.inmemoryinfrastructure.persistence.user.StubUser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("debug")
class DebugDiConfiguration {
    @Bean
    fun userContext(): UserContext {
        return StubUser(UserId("stub-user-id"))
    }
}