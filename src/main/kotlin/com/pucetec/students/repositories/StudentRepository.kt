package com.pucetec.students.repositories

import com.pucetec.students.entities.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//Es el q interactua con la base de datos
@Repository
interface StudentRepository: JpaRepository<Student, Long>