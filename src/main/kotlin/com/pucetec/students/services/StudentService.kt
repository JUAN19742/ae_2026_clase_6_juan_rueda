package com.pucetec.students.services

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.entities.Student
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.StudentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

// Es el que alamacena la logica del negocio
@Service
class StudentService (
    private val repository: StudentRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass )

    fun createStudent(request: StudentRequest): StudentResponse{
        logger.info("Creating student ${request.name}")
        //Verificar si el nombre esta vacio
        if (request.name.isBlank()) {
            throw BlankNameException("El nombre del estudiante no puede estar vacío")
        }

       // validar

        val studentToSave = request.toEntity()

        //crear entidad


        // guardar entidad
        val savedStudent = repository.save(studentToSave)
        logger.info("Saved student with id ${savedStudent.name}")

        return savedStudent.toResponse()


        // retornar response

    }

    fun getAllStudents(): List<StudentResponse>{
        logger.info("Getting all students")

        // consultar todos los estudiantes
        val savedStudents = repository.findAll()

        // convertir al response adecuado
        return savedStudents.map {
            it.toResponse()
        }
    }

    fun getStudentById(id: Long): StudentResponse{
        logger.info("Getting student by id $id")
        val student = repository.findById(id).orElseThrow {
            StudentNotFoundException("Estudiante $id no encontrado")
        }
        return student.toResponse()
    }

    fun updateStudent(id: Long, request: StudentRequest): StudentResponse{
        logger.info("Updating student $id")

        if (request.name.isBlank()) {
            throw BlankNameException("El nombre del estudiante no puede estar vacío")
        }

        val existingStudent = repository.findById(id).orElseThrow {
            StudentNotFoundException("Estudiante $id no encontrado")
        }

        val updatedStudent = Student(
            id = existingStudent.id,
            name = request.name,
            email = request.email,
        )

        val savedStudent = repository.save(updatedStudent)
        logger.info("Updated student with id ${savedStudent.id}")

        return savedStudent.toResponse()
    }

    fun deleteStudent(id: Long) {
        logger.info("Deleting student $id")

        val existingStudent = repository.findById(id).orElseThrow {
            StudentNotFoundException("Estudiante $id no encontrado")
        }

        repository.delete(existingStudent)
        logger.info("Deleted student with id $id")
    }

}