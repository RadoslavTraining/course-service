package com.monov.course.controller;


import com.monov.course.dto.CourseDTO;
import com.monov.course.dto.CourseSearchRequest;
import com.monov.course.entity.Course;
import com.monov.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        log.info("Inside getAllCourses method in CourseController");
        return  courseService.findAllCourses();
    }

    @PostMapping
    public CourseDTO saveCourse(@RequestBody Course course) {
        log.info("Inside saveCourse method in CourseController");
        return courseService.saveCourse(course);
    }

    @GetMapping("/{id}")
    public CourseDTO findCourseById(@PathVariable("id") Long courseId) {
        log.info("Inside findCourseById method in CourseController");
        return courseService.findById(courseId);
    }

    @PostMapping("/{courseId}/{studentId}")
    public CourseDTO addStudentToCourse(@PathVariable("courseId") Long courseId,
                                     @PathVariable("studentId") Long studentId) {
        log.info("Inside addStudentToCourse method in CourseController");
        return courseService.addStudentToCourse(courseId,studentId);
    }

    @PostMapping("/students")
    public List<CourseDTO> findCoursesByStudentId(@RequestBody CourseSearchRequest request) {
        return courseService.searchCourses(request);
    }

    @GetMapping("/students/{courseId}")
    public List<Long> findStudentIdsByCourseId(@PathVariable(name = "courseId") Long courseId) {
        return courseService.findStudentIdsByCourseId(courseId);
    }
}
