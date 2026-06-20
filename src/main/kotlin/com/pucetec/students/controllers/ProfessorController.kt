package com.pucetec.students.controllers

import com.pucetec.students.dto.ProfessorRequest
import com.pucetec.students.dto.ProfessorResponse
import com.pucetec.students.services.ProfessorService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfessorController (
    private val professorService: ProfessorService
){

    private val logger = LoggerFactory.getLogger(ProfessorController::class.java)

    @PostMapping("/api/professors")
    fun createProfessor(
        @RequestBody
        request: ProfessorRequest
    ): ProfessorResponse {
        logger.info("Creating professor ${request.name}")
        return professorService.createProfessor(request)
    }

    @GetMapping("/api/professors")
    fun getAllProfessors(): List<ProfessorResponse> {
        logger.info("Getting all professors")
        return professorService.getAllProfessors()
    }

    @GetMapping("/api/professors/{id}")
    fun getProfessorById(
        @PathVariable id: Long
    ): ProfessorResponse {
        logger.info("Getting professor by id $id")
        return professorService.getProfessorById(id)
    }

    @PutMapping("/api/professors/{id}")
    fun updateProfessor(
        @PathVariable id: Long,
        @RequestBody request: ProfessorRequest
    ): ProfessorResponse {
        logger.info("Updating professor $id")
        return professorService.updateProfessor(id, request)
    }

    @DeleteMapping("/api/professors/{id}")
    fun deleteProfessor(
        @PathVariable id: Long
    ) {
        logger.info("Deleting professor $id")
        professorService.deleteProfessor(id)
    }
}
