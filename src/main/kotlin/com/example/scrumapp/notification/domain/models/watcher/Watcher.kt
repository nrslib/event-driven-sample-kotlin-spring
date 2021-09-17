package com.example.scrumapp.notification.domain.models.watcher

import com.example.scrumapp.notification.domain.models.task.Task
import com.example.scrumapp.notification.domain.models.watch.Type
import com.example.scrumapp.notification.domain.models.watch.Watch

class Watcher(val id: WatcherId, val mailAddress: MailAddress) {
    fun watch(task: Task): Watch {
        return Watch(task.id, id, Type.TASK)
    }
}