package com.example.scrumapp.webapplication.controllers

import com.example.scrumapp.notification.application.WatchApplicationService
import com.example.scrumapp.webapplication.models.watch.post.TaskWatchPostRequestModel
import com.example.scrumapp.webapplication.models.watch.watcher.getList.WatchGetWatcherListResponseModel
import com.example.scrumapp.webapplication.models.watch.watcher.getList.WatcherModel
import com.example.scrumapp.webapplication.models.watch.watcher.post.TaskWatchPostWatcherRequestModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/watch")
class WatchController(
    private val watchApplicationService: WatchApplicationService
) {
    @PostMapping("/{taskId}")
    fun watch(@PathVariable("taskId") taskId: String, @RequestBody request: TaskWatchPostRequestModel) {
        watchApplicationService.watch(taskId, request.watcherId)
    }

    @GetMapping("/watcher")
    fun getWatcherList(): WatchGetWatcherListResponseModel {
        val watchers = watchApplicationService.getWatchers()

        return WatchGetWatcherListResponseModel(watchers.map { WatcherModel(it.id.value, it.mailAddress.value) })
    }

    @PostMapping("/watcher")
    fun postWatcher(@RequestBody request: TaskWatchPostWatcherRequestModel) {
        watchApplicationService.register(request.mailAddress)
    }
}