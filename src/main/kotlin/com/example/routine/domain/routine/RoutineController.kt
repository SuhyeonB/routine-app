package com.example.routine.domain.routine

import com.example.routine.domain.category.CategoryService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/routines")
class RoutineController (
    private val routineService: RoutineService,
    private val categoryService: CategoryService
) {
    @GetMapping
    fun list(
        model: Model,
        @RequestParam(required = false) categoryId: Long?
    ): String {
        val routines = if (categoryId != null)
            routineService.getAll().filter { it.category?.id == categoryId }
        else
            routineService.getAll()
        model.addAttribute("routines", routines)
        return "routine/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        model.addAttribute("categories", categoryService.getAll())
        return "routine/form"
    }

    @PostMapping
    fun create(@ModelAttribute request: RoutineRequest): String {
        routineService.create(request)
        return "redirect:/routines"
    }

    @GetMapping("/{id}/edit")
    fun editForm(@PathVariable id: Long, model: Model): String {
        model.addAttribute("routine", routineService.getById(id))
        model.addAttribute("categories", categoryService.getAll())
        return "routine/form"
    }

    @PostMapping("/{id}/edit")
    fun edit(@PathVariable id: Long, @ModelAttribute request: RoutineRequest): String {
        routineService.update(id, request)
        return "redirect:/routines"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        routineService.delete(id)
        return "redirect:/routines"
    }
}