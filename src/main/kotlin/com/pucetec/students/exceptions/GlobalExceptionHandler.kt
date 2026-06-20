package com.pucetec.students.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BlankNameException::class)
    fun handleBlankNameException(
        e: BlankNameException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = e.message ?: "Nombre en blanco - ERROR",
            source = "StudentService"
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    @ExceptionHandler(StudentNotFoundException::class)
    fun handleStudentNotFoundException(
        e: StudentNotFoundException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = e.message ?: "Estudiante no encontrado - ERROR",
            source = "StudentService"
        )

        //retrona nuestro error response con un codigo de error http 400 (bad request
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse)
    }


    @ExceptionHandler(SubjectNotFoundException::class)
    fun handleSubjectNotFoundException(
        e: SubjectNotFoundException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = e.message ?: "Materia no encontrada - ERROR",
            source = "SubjectService"
        )

        //retrona nuestro error response con un codigo de error http 400 (bad request
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse)
    }

    @ExceptionHandler(ProfessorNotFoundException::class)
    fun handleProfessorNotFoundException(
        e: ProfessorNotFoundException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = e.message ?: "Profesor no encontrado - ERROR",
            source = "ProfessorService"
        )

        //retrona nuestro error response con un codigo de error http 400 (bad request
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse)
    }

    @ExceptionHandler(EnrollmentNotFoundException::class)
    fun handleEnrollmentNotFoundException(
        e: EnrollmentNotFoundException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = e.message ?: "Inscripción no encontrada - ERROR",
            source = "EnrollmentService"
        )
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse)
    }

}

data class ErrorResponse (
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val source: String,
)