package com.pucetec.students.services

import com.pucetec.students.dto.StudentRequest
import com.pucetec.students.dto.StudentResponse
import com.pucetec.students.entities.Student
import com.pucetec.students.exceptions.EmailAlreadyExistsException
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

        logger.info("Validating request ${request.name}... checking email")
        val emailExists = repository.existsByEmail(request.email)

        if (emailExists){
            //lanzar excepcion no controlada
            throw EmailAlreadyExistsException(request.email)
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
        val students: List<Student> = repository.findAll()

        // convertir al response adecuado
        return students.map { miEstudiante: Student ->
            miEstudiante.toResponse() }

    }

}