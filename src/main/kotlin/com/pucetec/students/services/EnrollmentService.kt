package com.pucetec.students.services

import com.pucetec.students.dto.EnrollmentRequest
import com.pucetec.students.dto.EnrollmentResponse
import com.pucetec.students.dto.EnrollmentStatusRequest
import com.pucetec.students.entities.Enrollment
import com.pucetec.students.exceptions.EnrollmentNotFoundException
import com.pucetec.students.exceptions.StudentNotFoundException
import com.pucetec.students.exceptions.SubjectNotFoundException
import com.pucetec.students.mappers.toResponse
import com.pucetec.students.repositories.EnrollmentRepository
import com.pucetec.students.repositories.StudentRepository
import com.pucetec.students.repositories.SubjectRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EnrollmentService (
    private val studentRepository: StudentRepository,
    private val subjectRepository: SubjectRepository,
    private val enrollmentRepository: EnrollmentRepository
){
    private val logger = LoggerFactory.getLogger(javaClass)

    fun createEnrollment(enrollmentRequest: EnrollmentRequest): EnrollmentResponse {

        val studentEntity = studentRepository.findById(enrollmentRequest.studentId).orElseThrow {
            StudentNotFoundException("Estudiante no encontrado")
        }

        val subjectEntity = subjectRepository.findById(enrollmentRequest.subjectId).orElseThrow {
            SubjectNotFoundException("Materia no encontrada")
        }

        //validar si esa matricula ya existe


        val enrollment = Enrollment(
            student = studentEntity,
            subject = subjectEntity,
            status = "INSCRITO"
        )

        return enrollmentRepository.save(enrollment).toResponse()
    }

    fun getAllEnrollments(): List<EnrollmentResponse> {
        logger.info("Getting all enrollments")
        return enrollmentRepository.findAll().map { it.toResponse() }
    }

    fun getEnrollmentById(id: Long): EnrollmentResponse {
        logger.info("Getting enrollment by id $id")

        val enrollment = enrollmentRepository.findById(id).orElseThrow {
            EnrollmentNotFoundException("Inscripción $id no encontrada")
        }

        return enrollment.toResponse()
    }

    fun updateEnrollmentStatus(id: Long, request: EnrollmentStatusRequest): EnrollmentResponse {
        logger.info("Updating enrollment $id with status ${request.status}")

        val existingEnrollment = enrollmentRepository.findById(id).orElseThrow {
            EnrollmentNotFoundException("Inscripción $id no encontrada")
        }

        val updatedEnrollment = Enrollment(
            id = existingEnrollment.id,
            createdAt = existingEnrollment.createdAt,
            status = request.status,
            subject = existingEnrollment.subject,
            student = existingEnrollment.student
        )

        val savedEnrollment = enrollmentRepository.save(updatedEnrollment)
        logger.info("Updated enrollment with id ${savedEnrollment.id}")

        return savedEnrollment.toResponse()
    }

    fun deleteEnrollment(id: Long) {
        logger.info("Deleting enrollment $id")

        val existingEnrollment = enrollmentRepository.findById(id).orElseThrow {
            EnrollmentNotFoundException("Inscripción $id no encontrada")
        }

        enrollmentRepository.delete(existingEnrollment)
        logger.info("Deleted enrollment with id $id")
    }
}

