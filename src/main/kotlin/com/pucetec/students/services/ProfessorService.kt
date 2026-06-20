package com.pucetec.students.services

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.dto.ProfessorResponse
import com.pucetec.students.entities.Professor
import com.pucetec.students.exceptions.BlankNameException
import com.pucetec.students.exceptions.ProfessorNotFoundException
import com.pucetec.students.mappers.toEntity
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.ProfessorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class ProfessorService(
    private val repository: ProfessorRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun createProfessor(request: ProfessorRequest): ProfessorResponse {
        logger.info("Creating professor ${request.name}")

        if (request.name.isBlank()) {
            throw BlankNameException("El nombre del profesor no puede estar vacío")
        }
        val professorToSave = request.toEntity()

        val savedProfessor = repository.save(professorToSave)
        logger.info("Saved professor with id ${savedProfessor.id}")

        return savedProfessor.toResponse()
    }

    fun getAllProfessors(): List<ProfessorResponse> {
        logger.info("Getting all professors")

        val professors: List<Professor> = repository.findAll()

        return professors.map { professor: Professor ->
            professor.toResponse()
        }
    }

    fun getProfessorById(id: Long): ProfessorResponse {
        logger.info("Getting professor by id $id")

        val professor = repository.findById(id).orElseThrow {
            ProfessorNotFoundException("Profesor $id no encontrado")
        }

        return professor.toResponse()
    }

    fun updateProfessor(id: Long, request: ProfessorRequest): ProfessorResponse {
        logger.info("Updating professor $id")

        if (request.name.isBlank()) {
            throw BlankNameException("El nombre del profesor no puede estar vacío")
        }

        val existingProfessor = repository.findById(id).orElseThrow {
            ProfessorNotFoundException("Profesor $id no encontrado")
        }

        val updatedProfessor = Professor(
            id = existingProfessor.id,
            name = request.name,
            email = request.email,
            subjects = existingProfessor.subjects
        )

        val savedProfessor = repository.save(updatedProfessor)
        logger.info("Updated professor with id ${savedProfessor.id}")

        return savedProfessor.toResponse()
    }

    fun deleteProfessor(id: Long) {
        logger.info("Deleting professor $id")

        val existingProfessor = repository.findById(id).orElseThrow {
            ProfessorNotFoundException("Profesor $id no encontrado")
        }

        repository.delete(existingProfessor)
        logger.info("Deleted professor with id $id")
    }

}
