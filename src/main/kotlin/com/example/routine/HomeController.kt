package com.example.routine

import com.example.routine.domain.check.RoutineCheckService
import com.example.routine.domain.routine.RoutineService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDate

@Controller
class HomeController(
    private val routineService: RoutineService,
    private val routineCheckService: RoutineCheckService
) {
    @GetMapping("/")
    fun home(model: Model): String {
        val today = LocalDate.now()
        val routines = routineService.getTodayRoutines()
        val checkedIds = routines
            .filter { routineCheckService.isChecked(it.id, today) }
            .map { it.id }
            .toHashSet()

        model.addAttribute("routines", routines)
        model.addAttribute("checkedIds", checkedIds)
        model.addAttribute("today", today)
        return "index"
    }
}