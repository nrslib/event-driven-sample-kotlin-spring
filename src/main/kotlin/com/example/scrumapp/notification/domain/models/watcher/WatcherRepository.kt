package com.example.scrumapp.notification.domain.models.watcher

interface WatcherRepository {
    fun find(watcher: WatcherId): Watcher?
    fun find(watcher: List<WatcherId>): List<Watcher>
    fun findAll(): List<Watcher>
    fun save(watcher: Watcher)
    fun exists(mailAddress: MailAddress): Boolean
}