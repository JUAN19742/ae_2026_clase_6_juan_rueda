package com.pucetec.students.controllers

import com.pucetec.students.dto.SubjectRequest
import com.pucetec.students.dto.SubjectResponse
import com.pucetec.students.services.SubjectService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SubjectController(
    private val subjectService: SubjectService,

) {
    private val logger = LoggerFactory.getLogger(SubjectController::class.java)

    @PostMapping("/api/subjects")
    fun createSubject(
        @RequestBody
        request: SubjectRequest
    ): SubjectResponse {
        return subjectService.createSubject(request)
    }

    @GetMapping("/api/subjects")
    fun getAllSubjects(): List<SubjectResponse> {
        logger.info("Getting all subjects")
        return subjectService.getAllSubjects()
    }

    @GetMapping("/api/subjects/{id}")
    fun getSubjectById(
        @PathVariable id: Long
    ): SubjectResponse {
        logger.info("Getting subject by id $id")
        return subjectService.getSubjectById(id)
    }

    @PutMapping("/api/subjects/{id}")
    fun updateSubject(
        @PathVariable id: Long,
        @RequestBody request: SubjectRequest
    ): SubjectResponse {
        logger.info("Updating subject $id")
        return subjectService.updateSubject(id, request)
    }

    @DeleteMapping("/api/subjects/{id}")
    fun deleteSubject(
        @PathVariable id: Long
    ) {
        logger.info("Deleting subject $id")
        subjectService.deleteSubject(id)
    }
}