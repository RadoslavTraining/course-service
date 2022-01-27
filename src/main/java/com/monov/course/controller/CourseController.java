package com.monov.course.controller;


import com.monov.commons.dto.CourseDTO;
import com.monov.commons.dto.CourseSearchRequestDTO;
import com.monov.course.entity.Course;
import com.monov.course.response.CourseResponseHandler;
import com.monov.course.response.StringResponseHandler;
import com.monov.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        log.info("Inside getAllCourses method in CourseController");
        return CourseResponseHandler.generateListSuccessResponse (HttpStatus.OK, courseService.findAllCourses());
    }

    @PostMapping
    public ResponseEntity<CourseDTO> saveCourse(@Valid @RequestBody Course course) {
        log.info("Inside saveCourse method in CourseController");
        return CourseResponseHandler.generateSuccessResponse(HttpStatus.OK, courseService.saveCourse(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> findCourseById(@PathVariable("id") String courseId) {
        log.info("Inside findCourseById method in CourseController");
        return CourseResponseHandler.generateSuccessResponse(HttpStatus.OK,
                courseService.findById(courseId));
    }

    @PostMapping("/{courseId}/{studentId}")
    public ResponseEntity<CourseDTO> addStudentToCourse(@PathVariable("courseId") String courseId,
                                     @PathVariable("studentId") String studentId) {
        log.info("Inside addStudentToCourse method in CourseController");
        return CourseResponseHandler.generateSuccessResponse(HttpStatus.OK,courseService.addStudentToCourse(courseId,
                studentId));
    }

    @PostMapping("/students")
    public ResponseEntity<List<CourseDTO>> findCoursesByStudentId(@RequestBody CourseSearchRequestDTO request) {
        return CourseResponseHandler.generateListSuccessResponse(HttpStatus.OK,courseService.searchCourses(request));
    }

    @GetMapping("/students/{courseId}")
    public ResponseEntity<List<String>> findStudentIdsByCourseId(@PathVariable(name = "courseId") String courseId) {
        return StringResponseHandler.generateListSuccessResponse(HttpStatus.OK,
                courseService.findStudentIdsByCourseId(courseId));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
