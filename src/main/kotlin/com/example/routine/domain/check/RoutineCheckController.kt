package com.example.routine.domain.check

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Controller
@RequestMapping("/checks")
class RoutineCheckController(
    private val routineCheckService: RoutineCheckService
) {
    @PostMapping("/{routineId}/toggle")
    fun toggle(
        @PathVariable routineId: Long,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
    ): String {
        routineCheckService.toggle(routineId, date)
        return "redirect:/"
    }
}