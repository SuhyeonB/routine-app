package com.example.routine.domain.routine

import org.springframework.data.jpa.repository.JpaRepository

interface RoutineRepository : JpaRepository<Routine, Long>