package ru.shashulovskiy.mvc.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.shashulovskiy.mvc.model.Task
import ru.shashulovskiy.mvc.model.TaskList

@Repository
interface TaskListCrudRepository: CrudRepository<TaskList, Long> {
    fun findTaskListById(taskListId: Long): TaskList
}