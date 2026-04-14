package com.example.routine.domain.check

import com.example.routine.domain.routine.Routine
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class RoutineCheck(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    val routine: Routine,

    val checkedDate: LocalDate,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
)