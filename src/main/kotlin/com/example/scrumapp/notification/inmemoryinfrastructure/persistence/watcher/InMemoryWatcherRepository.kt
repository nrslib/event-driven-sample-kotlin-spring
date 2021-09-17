package com.example.scrumapp.notification.inmemoryinfrastructure.persistence.watcher

import com.example.scrumapp.lib.domainsupport.repository.InMemoryCrudRepository
import com.example.scrumapp.notification.domain.models.watcher.MailAddress
import com.example.scrumapp.notification.domain.models.watcher.Watcher
import com.example.scrumapp.notification.domain.models.watcher.WatcherId
import com.example.scrumapp.notification.domain.models.watcher.WatcherRepository
import org.springframework.context.ApplicationEventPublisher

class InMemoryWatcherRepository(
    applicationEventPublisher: ApplicationEventPublisher
) :
    InMemoryCrudRepository<WatcherId, Watcher>(applicationEventPublisher), WatcherRepository {
    override fun getKey(value: Watcher): WatcherId {
        return value.id
    }

    override fun deepClone(value: Watcher): Watcher {
        return Watcher(value.id, value.mailAddress)
    }

    override fun find(watcher: List<WatcherId>): List<Watcher> {
        return watcher.map { keyToValue[it]!! }
    }

    override fun findAll(): List<Watcher> {
        return keyToValue.values.toList()
    }

    override fun exists(mail: MailAddress): Boolean {
        return keyToValue.any { it.value.mailAddress == mail }
    }
}