package com.example.scrumapp.notification.domain.models.task

import com.example.scrumapp.lib.domainsupport.aggregate.AggregateRoot
import com.example.scrumapp.shared.scrum.models.task.ProgressStatus

class Task(val id: TaskId, val progressStatus: ProgressStatus) : AggregateRoot<Task>()