package com.example.scrumapp.webapplication.models.task.patch

import com.example.scrumapp.shared.scrum.models.task.ProgressStatus

data class TaskPutRequestModel(val changeStatus: ProgressStatus?)