package com.example.routine.domain.check

import com.example.routine.domain.routine.Routine
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface RoutineCheckRepository : JpaRepository<RoutineCheck, Long> {
    fun findByRoutineAndCheckedDate (routine: Routine, checkedDate: LocalDate): RoutineCheck?
    fun findByCheckedDate(chekedDate: LocalDate): List<RoutineCheck>
}