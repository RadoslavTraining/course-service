package com.monov.course.controller;


import com.monov.course.data.CourseIds;
import com.monov.course.entity.Course;
import com.monov.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.monov.course.data.CourseSearchRequest;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
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

//    @GetMapping("/students/{courseId}")
//    public List<Long> findStudentIdsByCourseId(@PathVariable(name = "courseId") Long courseId) {
//        return courseService.findStudentIdsByCoursId(courseId);
//    }
//
//    @PostMapping("/search")
//    public List<Long> findCourseIdsByStudentId(@RequestBody CourseSearchRequest request) {
//        return courseService.searchCourse(request);
//    }

//// requirement 1
//    @GetMapping("/students/{studentId}")
//    public List<Course> findCoursesByStudentId(@PathVariable(name = "studentId") Long studentId) {
//        return courseService.findCoursesByStudentId(studentId);
//    }

    // requirement 1
    @PostMapping("/students")
    public List<Course> findCoursesByStudentId(@RequestBody CourseSearchRequest request) {
        return courseService.searchCourses(request);
    }

    //req 2
    @GetMapping("/students/{courseId}")
    public List<Long> findStudentIdsByCourseId(@PathVariable(name = "courseId") Long courseId) {
        return courseService.findStudentIdsByCoursId(courseId);
    }
}
