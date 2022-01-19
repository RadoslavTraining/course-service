package com.monov.course.controller;


import com.monov.commons.dto.CourseDTO;
import com.monov.commons.dto.CourseSearchRequest;
import com.monov.course.entity.Course;
import com.monov.course.response.CourseResponseHandler;
import com.monov.course.response.LongResponseHandler;
import com.monov.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody Course course) {
        log.info("Inside saveCourse method in CourseController");
        return CourseResponseHandler.generateSuccessResponse(HttpStatus.OK, courseService.saveCourse(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> findCourseById(@PathVariable("id") Long courseId) {
        log.info("Inside findCourseById method in CourseController");
        return CourseResponseHandler.generateSuccessResponse(HttpStatus.OK,
                courseService.findById(courseId));
    }

    @PostMapping("/{courseId}/{studentId}")
    public ResponseEntity<CourseDTO> addStudentToCourse(@PathVariable("courseId") Long courseId,
                                     @PathVariable("studentId") Long studentId) {
        log.info("Inside addStudentToCourse method in CourseController");
        return CourseResponseHandler.generateSuccessResponse(HttpStatus.OK,courseService.addStudentToCourse(courseId,
                studentId));
    }

    @PostMapping("/students")
    public ResponseEntity<List<CourseDTO>> findCoursesByStudentId(@RequestBody CourseSearchRequest request) {
        return CourseResponseHandler.generateListSuccessResponse(HttpStatus.OK,courseService.searchCourses(request));
    }

    @GetMapping("/students/{courseId}")
    public ResponseEntity<List<Long>> findStudentIdsByCourseId(@PathVariable(name = "courseId") Long courseId) {
        return LongResponseHandler.generateListSuccessResponse(HttpStatus.OK,
                courseService.findStudentIdsByCourseId(courseId));
    }
}
