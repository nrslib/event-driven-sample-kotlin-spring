package com.example.scrumapp.scrum.application.services.task;

import com.example.applicationsupportstack.applicationsupport.exceptions.NotFoundException;
import com.example.scrum.domain.models.task.*;
import com.example.scrum.domain.models.task.events.TaskCreatedEvent;
import com.example.scrum.domain.models.user.UserContext;
import com.example.scrum.domain.models.userstory.UserStoryId;
import com.example.scrum.domain.models.userstory.UserStoryRepository;
import com.example.scrumapp.shared.scrum.models.task.ProgressStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

public class TaskApplicationService {
    private final ApplicationEventPublisher publisher;
    private final UserContext userContext;
    private final TaskRepository taskRepository;
    private final UserStoryRepository userStoryRepository;

    public TaskApplicationService(ApplicationEventPublisher publisher, UserContext userContext, TaskRepository taskRepository, UserStoryRepository userStoryRepository) {
        this.publisher = publisher;
        this.userContext = userContext;
        this.taskRepository = taskRepository;
        this.userStoryRepository = userStoryRepository;
    }

    public Optional<Task> getTask(String aId) {
        Objects.requireNonNull(aId);

        var id = new TaskId(aId);

        return taskRepository.find(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(TaskAddCommand command) {
        Objects.requireNonNull(command);

        var storyId = new UserStoryId(command.getStoryId());
        var optStory = userStoryRepository.find(storyId);
        if (optStory.isEmpty()) {
            throw new NotFoundException("Story not found. id:" + storyId);
        }

        var story = optStory.get();
        var task = story.createTask(new Description(command.getDescription()));
        publisher.publishEvent(new TaskCreatedEvent(this));

        taskRepository.save(task);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void signup(String id) {
        Objects.requireNonNull(id);

        var task = getTaskOrThrowNotFound(id);
        task.signup(userContext);

        taskRepository.save(task);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void changeStatus(String id, ProgressStatus status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);

        var task = getTaskOrThrowNotFound(id);
        task.changeStatus(status);

        taskRepository.save(task);
    }

    private Task getTaskOrThrowNotFound(String id) {
        var taskId = new TaskId(id);
        var optTask = taskRepository.find(taskId);
        if (optTask.isEmpty()) {
            throw new NotFoundException("Task not found. id:" + taskId);
        }

        return optTask.get();
    }
}
