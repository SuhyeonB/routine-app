package com.example.routine.domain.statistics

import com.example.routine.domain.check.RoutineCheckRepository
import com.example.routine.domain.routine.Routine
import com.example.routine.domain.routine.RoutineRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class StatisticsService(
    private val routineRepository: RoutineRepository,
    private val routineCheckRepository: RoutineCheckRepository
) {
    // 주간 통계: 루틴별 이번 주 월~일 달성 여부
    fun getWeeklyStats(weekStart: LocalDate): List<RoutineWeekStat> {
        val weekDays = (0..6).map { weekStart.plusDays(it.toLong()) }
        val routines = routineRepository.findAll()

        return routines.map { routine ->
            val checkedDays = weekDays.map { date ->
                val checked = routineCheckRepository
                    .findByRoutineAndCheckedDate(routine, date) != null
                DayStat(date = date, checked = checked, scheduled = routine.isScheduledOn(date))
            }
            RoutineWeekStat(routine = routine, days = checkedDays)
        }
    }

    // 월간 통계: 루틴별 해당 월 달성 현황
    fun getMonthlyStats(yearMonth: YearMonth): List<RoutineMonthStat> {
        val routines = routineRepository.findAll()

        return routines.map { routine ->
            val days = (1..yearMonth.lengthOfMonth()).map { day ->
                val date = yearMonth.atDay(day)
                val checked = routineCheckRepository
                    .findByRoutineAndCheckedDate(routine, date) != null
                DayStat(date = date, checked = checked, scheduled = routine.isScheduledOn(date))
            }
            val scheduledCount = days.count { it.scheduled }
            val checkedCount = days.count { it.checked }
            val rate = if (scheduledCount > 0) (checkedCount * 100 / scheduledCount) else 0

            RoutineMonthStat(
                routine = routine,
                days = days,
                scheduledCount = scheduledCount,
                checkedCount = checkedCount,
                achievementRate = rate
            )
        }
    }
}

data class DayStat(
    val date: LocalDate,
    val checked: Boolean,
    val scheduled: Boolean
)

data class RoutineWeekStat(
    val routine: Routine,
    val days: List<DayStat>
)

data class RoutineMonthStat(
    val routine: Routine,
    val days: List<DayStat>,
    val scheduledCount: Int,
    val checkedCount: Int,
    val achievementRate: Int
)