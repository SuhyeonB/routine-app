package com.example.routine.domain.routine

import com.example.routine.domain.category.Category
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate

@Entity
class Routine(
    val title: String,
    val memo: String? = null,

    @Enumerated(EnumType.STRING)
    val repeatType: RepeatType,

    val repeatDays: String? = null,
    val repeatInterval: Int? = null,
    val repeatEndDate: java.time.LocalDate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category? = null,

    @CreationTimestamp
    val createdAt: java.time.LocalDateTime = java.time.LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) {
    fun isScheduledOn(date: LocalDate): Boolean {
        if (repeatEndDate != null && date.isAfter(repeatEndDate)) return false

        return when (repeatType) {
            RepeatType.DAILY -> true
            RepeatType.WEEKLY -> {
                val dayOfWeek = date.dayOfWeek.name.take(3)
                repeatDays?.split(",")?.contains(dayOfWeek) ?: false
            }
            RepeatType.EVERY_N -> {
                val interval = repeatInterval ?: return false
                val daysBetween = java.time.temporal.ChronoUnit.DAYS.between(createdAt.toLocalDate(), date)
                daysBetween % interval == 0L
            }
        }
    }
}

enum class RepeatType {
    DAILY, WEEKLY, EVERY_N
}