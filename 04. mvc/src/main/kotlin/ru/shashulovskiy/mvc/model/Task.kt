package ru.shashulovskiy.mvc.model

import javax.persistence.*

@Entity
data class Task(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val id: Long? = null,
    @Column(nullable=false) val name: String? = null,
    @Column(nullable=false) val completed: Boolean = false,
    @ManyToOne @JoinColumn(name = "task_list_id") val taskList: TaskList? = null
)