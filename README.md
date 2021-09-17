# Event Driven Sample (Kotlin, Spring boot)

## Architecture
Hexagonal  
or  
ADOP  
https://nrslib.com/adop/

## Contexts

- Scrum
- Notification
- Shared

## Execution
```curl --location --request PUT 'localhost:8080/api/task/test-task-id/' --header 'Content-Type: application/json' --data-raw '{"changeStatus" : "DOING"}'```

### Flow

1. TaskController::put
2. TaskApplicationService::changeStatus (begin transaction)
3. Task::registerEvent
4. ApplicationEventPublisher::publishEvent
5. finish transaction
6. NotificationEventHandler::onTaskStatusChanged
