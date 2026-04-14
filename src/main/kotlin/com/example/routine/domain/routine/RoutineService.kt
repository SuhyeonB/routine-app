package com.example.routine.domain.routine

import com.example.routine.domain.category.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class RoutineService (
    private val routineRepository: RoutineRepository,
    private val categoryRepository: CategoryRepository
) {
    fun getAll(): List<Routine> = routineRepository.findAll()

    fun getById(id: Long): Routine = routineRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Routine not found: $id") }

    @Transactional
    fun create(request: RoutineRequest): Routine {
        val category = request.categoryId?.let {
            categoryRepository.findById(it).orElseThrow { IllegalArgumentException("Category not found: $it") }
        }
        val routine = Routine(
            title = request.title,
            memo = request.memo,
            repeatType = request.repeatType,
            repeatDays = request.repeatDays,
            repeatInterval = request.repeatInterval,
            repeatEndDate = request.repeatEndDate,
            category = category
        )
        return routineRepository.save(routine)
    }

    @Transactional
    fun update(id: Long, request: RoutineRequest): Routine {
        val routine = getById(id)
        val category = request.categoryId?.let {
            categoryRepository.findById(it).orElseThrow { IllegalArgumentException("Category not found: $it") }
        }
        return routineRepository.save(
            Routine(
                title = request.title,
                memo = request.memo,
                repeatType = request.repeatType,
                repeatDays = request.repeatDays,
                repeatInterval = request.repeatInterval,
                repeatEndDate = request.repeatEndDate,
                category = category,
                id = routine.id
            )
        )
    }

    @Transactional
    fun delete(id: Long) = routineRepository.deleteById(id)

    fun getTodayRoutines (): List<Routine> {
        val today = LocalDate.now()
        return routineRepository.findAll().filter { it.isScheduledOn(today) }
    }
}

data class RoutineRequest(
    val title: String,
    val memo: String? = null,
    val repeatType: RepeatType,
    val repeatDays: String? = null,
    val repeatInterval: Int? = null,
    val repeatEndDate: LocalDate? = null,
    val categoryId: Long? = null
)