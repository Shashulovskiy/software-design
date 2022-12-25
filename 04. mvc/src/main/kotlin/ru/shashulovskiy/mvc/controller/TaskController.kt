package ru.shashulovskiy.mvc.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import ru.shashulovskiy.mvc.model.Task
import ru.shashulovskiy.mvc.model.TaskList
import ru.shashulovskiy.mvc.service.TasksService

@RestController
@RequestMapping("/v1/taskList")
class TaskController(private val tasksService: TasksService) {
    @GetMapping("")
    fun handleGetTaskLists(): ModelAndView = ModelAndView("taskLists")
        .addObject("taskLists", tasksService.getTaskLists())
        .addObject("taskList", TaskList())

    @GetMapping("/{id}")
    fun handleGetTaskList(@PathVariable id: Long): ModelAndView = ModelAndView("taskList")
        .addObject("taskList", tasksService.getTaskList(id))
        .addObject("task", Task())

    @PostMapping("/{id}")
    fun handleAddTask(@ModelAttribute task: Task, @PathVariable id: Long): ModelAndView {
        tasksService.addTask(task.copy(taskList = tasksService.getTaskList(id), id = null))

        return ModelAndView("taskList")
            .addObject("taskList", tasksService.getTaskList(id))
            .addObject("task", Task())
    }

    @PostMapping("/{id}/complete")
    fun handleTaskComplete(@PathVariable id: Long): ModelAndView {
        tasksService.markTaskAsCompleted(id)

        return ModelAndView("taskList")
            .addObject("taskList", tasksService.getTaskList(id))
            .addObject("task", Task())
    }


    @PostMapping("")
    fun handleAddTaskList(@ModelAttribute taskList: TaskList): ModelAndView {
        tasksService.addTaskList(taskList)

        return ModelAndView("taskLists")
            .addObject("taskLists", tasksService.getTaskLists())
            .addObject("taskList", TaskList())
    }
}