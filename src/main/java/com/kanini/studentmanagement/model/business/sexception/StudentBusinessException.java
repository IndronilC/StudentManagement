package com.kanini.studentmanagement.model.business.sexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class StudentBusinessException extends RuntimeException {
    private String errorMessage;

    public StudentBusinessException(String errorMessage, Throwable errorCause){
        super(errorMessage, errorCause);
    }


}
