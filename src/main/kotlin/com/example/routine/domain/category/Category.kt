package com.example.routine.domain.category

import jakarta.persistence.*;

@Entity
class Category (
    val name: String,
    val color: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
)