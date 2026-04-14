package com.example.routine.domain.category

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CategoryService (
    private val categoryRepository: CategoryRepository
) {
    fun getAll(): List<Category> = categoryRepository.findAll()

    @Transactional
    fun create (name: String, color: String): Category {
        val category = Category(name = name, color = color)
        return categoryRepository.save(category)
    }

    @Transactional
    fun delete(id: Long) = categoryRepository.deleteById(id)
}