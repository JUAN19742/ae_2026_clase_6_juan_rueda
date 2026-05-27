package com.pucetec.students.exceptions

class EmailAlreadyExistsException (
    override val message: String,
) : RuntimeException()