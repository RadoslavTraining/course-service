package com.monov.course.controller;


import com.monov.course.data.CourseIds;
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

    @GetMapping("/")
    public List<Course> getAllCourses() {
        log.info("Inside getAllCourses method in CourseController");
        return  courseService.findAllCourses();
    }

    @PostMapping
    public Course saveCourse(@RequestBody Course course) {
        log.info("Inside saveCourse method in CourseController");
        return courseService.saveCourse(course);
    }

    @GetMapping("/{id}")
    public Course findCourseById(@PathVariable("id") Long courseId) {
        log.info("Inside findCourseById method in CourseController");
        return courseService.findById(courseId);
    }

    @PostMapping("/ids")
    public List<Course> findCoursesByTheirIds(@RequestBody CourseIds courseIds) {
        log.info("Inside findCoursesByIds method in CourseController");
        return courseService.findByIds(courseIds.getIds());
    }

    @PostMapping("/{courseId}/{studentId}")
    public Course addStudentToCourse(@PathVariable("courseId") Long courseId,
                                     @PathVariable("studentId") Long studentId) {
        log.info("Inside addStudentToCourse method in CourseController");
        return courseService.addStudentToCourse(courseId,studentId);
    }

}
