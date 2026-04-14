package com.example.routine.domain.statistics

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

@Controller
@RequestMapping("/statistics")
class StatisticsController(
    private val statisticsService: StatisticsService
) {
    @GetMapping
    fun statistics(
        model: Model,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) weekStart: LocalDate?,
        @RequestParam(required = false) yearMonth: String?
    ): String {
        val thisWeekStart = weekStart ?: LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val thisYearMonth = if (yearMonth != null) YearMonth.parse(yearMonth) else YearMonth.now()

        model.addAttribute("weekStats", statisticsService.getWeeklyStats(thisWeekStart))
        model.addAttribute("monthStats", statisticsService.getMonthlyStats(thisYearMonth))
        model.addAttribute("weekStart", thisWeekStart)
        model.addAttribute("weekEnd", thisWeekStart.plusDays(6))
        model.addAttribute("yearMonth", thisYearMonth)

        return "statistics"
    }
}