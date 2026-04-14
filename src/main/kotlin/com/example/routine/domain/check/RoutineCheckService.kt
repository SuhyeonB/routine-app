package com.example.routine.domain.check

import com.example.routine.domain.routine.RoutineService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class RoutineCheckService(
    private val routineCheckRepository: RoutineCheckRepository,
    private val routineService: RoutineService
) {
    fun isChecked(routineId: Long, date: LocalDate): Boolean {
        val routine = routineService.getById(routineId)
        return routineCheckRepository.findByRoutineAndCheckedDate(routine, date) != null
    }

    @Transactional
    fun toggle(routineId: Long, date: LocalDate) {
        val routine = routineService.getById(routineId)
        val existing = routineCheckRepository.findByRoutineAndCheckedDate(routine, date)

        if (existing != null) {
            routineCheckRepository.delete(existing)
        } else {
            routineCheckRepository.save(RoutineCheck(routine = routine, checkedDate = date))
        }
    }
}