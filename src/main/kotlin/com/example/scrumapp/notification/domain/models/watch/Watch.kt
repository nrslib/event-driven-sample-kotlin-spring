package com.example.scrumapp.notification.domain.models.watch

import com.example.scrumapp.lib.domainsupport.aggregate.AggregateRoot
import com.example.scrumapp.notification.domain.models.task.TaskId
import com.example.scrumapp.notification.domain.models.watcher.WatcherId

class Watch(
    val taskId: TaskId,
    val watcherId: WatcherId,
    val type: Type
) : AggregateRoot<Watch>()