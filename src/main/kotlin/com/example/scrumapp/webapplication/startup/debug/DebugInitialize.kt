package com.example.scrumapp.webapplication.startup.debug

import com.example.scrumapp.notification.domain.models.watch.Type
import com.example.scrumapp.notification.domain.models.watch.Watch
import com.example.scrumapp.notification.domain.models.watch.WatchRepository
import com.example.scrumapp.notification.domain.models.watcher.MailAddress
import com.example.scrumapp.notification.domain.models.watcher.Watcher
import com.example.scrumapp.notification.domain.models.watcher.WatcherId
import com.example.scrumapp.notification.domain.models.watcher.WatcherRepository
import com.example.scrumapp.scrum.domain.models.task.Description
import com.example.scrumapp.scrum.domain.models.task.Task
import com.example.scrumapp.scrum.domain.models.task.TaskId
import com.example.scrumapp.scrum.domain.models.task.TaskRepository
import com.example.scrumapp.scrum.domain.models.user.UserId
import com.example.scrumapp.scrum.domain.models.userstory.UserStory
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryId
import com.example.scrumapp.scrum.domain.models.userstory.UserStoryRepository
import com.example.scrumapp.shared.scrum.models.task.ProgressStatus
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.*
import com.example.scrumapp.notification.domain.models.task.Task as NotificationTask
import com.example.scrumapp.notification.domain.models.task.TaskId as NotificationTaskId

@Component
@Profile("debug")
class DebugInitialize(
    taskRepository: TaskRepository,
    userStoryRepository: UserStoryRepository,
    notificationTaskRepository: com.example.scrumapp.notification.domain.models.task.TaskRepository,
    watcherRepository: WatcherRepository,
    watchRepository: WatchRepository
) {
    init {
        userStoryRepository.save(
            UserStory(
                UserStoryId("test-user-story-id"),
                "test-story"
            )
        )

        val testTaskIdRaw = "test-task-id"
        taskRepository.save(
            Task(
                TaskId(testTaskIdRaw),
                UserStoryId("test-user-story-id"),
                Description("test-description"),
                ProgressStatus.CREATED,
                object : ArrayList<UserId>() {
                    init {
                        add(UserId("stub-user-id"))
                    }
                }
            )
        )

        notificationTaskRepository.save(
            NotificationTask(
                NotificationTaskId(testTaskIdRaw),
                ProgressStatus.CREATED
            )
        )

        val testWatcherIdRaw = "test-watcher-id"
        watcherRepository.save(
            Watcher(
                WatcherId(testWatcherIdRaw),
                MailAddress("test-watcher@example.com")
            )
        )

        watchRepository.save(
            Watch(
                NotificationTaskId(testTaskIdRaw),
                WatcherId(testWatcherIdRaw),
                Type.TASK
            )
        )
    }
}