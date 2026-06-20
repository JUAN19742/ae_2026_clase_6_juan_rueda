package com.pucetec.students.services

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
import com.pucetec.students.entities.Subject
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.ProfessorNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.ProfessorRepository
import com.pucetec.students.repositories.SubjectRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val professorRepository: ProfessorRepository,
    private val subjectRepository: SubjectRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun createSubject(
        request: SubjectRequest
    ): SubjectResponse {

        logger.info("Creating subject ${request.name}")

        if (request.name.isBlank()) {
            throw BlankNameException("El nombre de la materia no puede estar vacío")
        }

        if (request.code.isBlank()) {
            throw BlankNameException("El código de la materia no puede estar vacío")
        }

        val professor = professorRepository.findById(request.professorId).orElseThrow {
            ProfessorNotFoundException("Profesor no encontrado: ${request.professorId}")
        }

        //validar profesor activo

        val subjectEntity = Subject(
            name = request.name,
            code = request.code,
            professor = professor
        )

        val savedSubject = subjectRepository.save(subjectEntity)

        return savedSubject.toResponse()
    }

    fun getAllSubjects(): List<SubjectResponse> {
        logger.info("Getting all subjects")
        return subjectRepository.findAll().map { it.toResponse() }
    }

    fun getSubjectById(id: Long): SubjectResponse {
        logger.info("Getting subject by id $id")

        val subject = subjectRepository.findById(id).orElseThrow {
            SubjectNotFoundException("Materia $id no encontrada")
        }

        return subject.toResponse()
    }

    fun updateSubject(id: Long, request: SubjectRequest): SubjectResponse {
        logger.info("Updating subject $id")

        if (request.name.isBlank()) {
            throw BlankNameException("El nombre de la materia no puede estar vacío")
        }

        if (request.code.isBlank()) {
            throw BlankNameException("El código de la materia no puede estar vacío")
        }

        val existingSubject = subjectRepository.findById(id).orElseThrow {
            SubjectNotFoundException("Materia $id no encontrada")
        }

        val professor = professorRepository.findById(request.professorId).orElseThrow {
            ProfessorNotFoundException("Profesor no encontrado: ${request.professorId}")
        }

        val updatedSubject = Subject(
            id = existingSubject.id,
            name = request.name,
            code = request.code,
            professor = professor,
            enrollments = existingSubject.enrollments
        )

        val savedSubject = subjectRepository.save(updatedSubject)
        logger.info("Updated subject with id ${savedSubject.id}")

        return savedSubject.toResponse()
    }

    fun deleteSubject(id: Long) {
        logger.info("Deleting subject $id")

        val existingSubject = subjectRepository.findById(id).orElseThrow {
            SubjectNotFoundException("Materia $id no encontrada")
        }

        subjectRepository.delete(existingSubject)
        logger.info("Deleted subject with id $id")
    }
}