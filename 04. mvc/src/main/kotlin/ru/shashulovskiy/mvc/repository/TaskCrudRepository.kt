package ru.shashulovskiy.mvc.repository

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.shashulovskiy.mvc.model.Task

@Repository
interface TaskCrudRepository: CrudRepository<Task, Long> {
    @Query("UPDATE Task t SET t.completed = true where t.id = ?1")
    @Modifying
    @Transactional
    fun markAsCompleted(id: Long)
}