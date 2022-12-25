package ru.shashulovskiy.mvc.model

import javax.persistence.*

@Entity
class TaskList(
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) val id: Long? = null,
    @Column(nullable=false) val name: String? = "",
    @OneToMany(mappedBy = "taskList") val tasks: Set<Task> = emptySet()
)