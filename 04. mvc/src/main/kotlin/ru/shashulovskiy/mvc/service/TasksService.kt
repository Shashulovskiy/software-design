package ru.shashulovskiy.mvc.service

import org.springframework.stereotype.Service
import ru.shashulovskiy.mvc.model.Task
import ru.shashulovskiy.mvc.model.TaskList
import ru.shashulovskiy.mvc.repository.TaskCrudRepository
import ru.shashulovskiy.mvc.repository.TaskListCrudRepository

@Service
class TasksService(
    private val taskCrudRepository: TaskCrudRepository,
    private val taskListCrudRepository: TaskListCrudRepository
) {
    fun getTaskLists(): Iterable<TaskList> = taskListCrudRepository.findAll()
    fun getTaskList(id: Long): TaskList = taskListCrudRepository.findTaskListById(id)
    fun addTaskList(taskList: TaskList) = taskListCrudRepository.save(taskList)
    fun addTask(task: Task) = taskCrudRepository.save(task)
    fun markTaskAsCompleted(id: Long) = taskCrudRepository.markAsCo mpleted(id)
}