package com.example.scrumapp.scrum.domain.models.task

import com.example.scrumapp.lib.domainsupport.aggregate.AggregateRoot
import com.example.scrumapp.scrum.domain.models.user.UserContext
import com.example.scrumapp.scrum.domain.models.user.UserId
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryId
import com.example.scrumapp.shared.scrum.models.task.ProgressStatus
import com.example.scrumapp.shared.scrum.models.task.events.TaskStatusChangedEvent


class Task(
    val id: TaskId,
    val storyId: UserStoryId,
    var description: Description,
    var status: ProgressStatus,
    val signupMembers: MutableList<UserId>
) : AggregateRoot<Task>() {
    fun changeDescription(description: Description) {
        this.description = description
    }

    fun changeStatus(status: ProgressStatus) {
        if (!isReady()) {
            throw IllegalChangeStatusException("ステータス変更の準備が整っていません")
        }
        val before = this.status
        this.status = status
        registerEvent(TaskStatusChangedEvent(id, before, status))
    }

    fun signup(user: UserContext) {
        val userId = user.getId()

        if (signupMembers.any { it.equals(userId) }) {
            return
        }

        signupMembers.add(userId)
    }

    fun isReady(): Boolean {
        return signupMembers.size > 0
    }
}