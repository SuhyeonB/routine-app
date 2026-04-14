package com.example.routine.domain.category

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/categories")
class CategoryController (
    private val categoryService: CategoryService
) {
    @GetMapping
    fun list(model: Model): String {
        model.addAttribute("categories", categoryService.getAll())
        return "category/list"
    }

    @PostMapping
    fun create(
        @RequestParam name: String,
        @RequestParam color: String
    ): String {
        categoryService.create(name, color)
        return "redirect:/categories"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        categoryService.delete(id)
        return "redirect:/categories"
    }
}