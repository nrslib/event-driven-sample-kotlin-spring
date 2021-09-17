package com.example.scrumapp.shared.scrum.models.task.events

import com.example.scrumapp.scrum.domain.models.task.TaskId
import com.example.scrumapp.shared.scrum.models.task.ProgressStatus

class TaskStatusChangedEvent(val taskId: TaskId, val from: ProgressStatus, val to: ProgressStatus)