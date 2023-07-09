package com.kanini.studentmanagement.controller;

import com.kanini.studentmanagement.dto.request.StudentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class StudentManagementController {


    @PostMapping("/student/register")
    @ResponseStatus(HttpStatus.OK)
    public void registerStudent(@RequestBody StudentRequest studentRequest){

    }

}
