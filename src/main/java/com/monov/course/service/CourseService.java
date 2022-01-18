package com.monov.course.service;

import com.monov.course.data.CourseSearchRequest;
import com.monov.course.entity.Course;
import com.monov.course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course findById(Long id){
        log.info("Inside findById method in CourseService");
        return courseRepository.findByCourseId(id);
    }

    public List<Course> findAllCourses(){
        log.info("findAllCourses saveCourse method in CourseService");
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course) {
        log.info("Inside saveCourse method in CourseService");
        return courseRepository.save(course);
    }

    public List<Course> findByIds(List<Long> idList) {
        log.info("Inside findByIds method in CourseService");
        return courseRepository.findAllById(idList);
    }

    public Course addStudentToCourse(Long courseId, Long studentId) {
        log.info("Inside addStudentToCourse method in CourseService");
        Course courseToUpdate = courseRepository.getById(courseId);
        courseToUpdate.getStudentIds().add(studentId);
        courseRepository.save(courseToUpdate);
        return courseToUpdate;
    }

    public List<Long> findStudentIdsByCoursId(Long courseId) {
        return courseRepository.findStudentIdsByCourseId(courseId);
    }

    public List<Long> findCourseIdsByStudentId(Long studentId) {
        return courseRepository.findCourseIdsByStudentId(studentId);
    }
    //reeq1
    public List<Course> searchCourses(CourseSearchRequest request) {
        if(request.getStudentId() != null) {
            return courseRepository.findCoursesByStudentId(request.getStudentId());
        }

        return new ArrayList<>();
    }
//reeq1
//    public List<Course> findCoursesByStudentId(Long studentId) {
//        return courseRepository.findCoursesByStudentId(studentId);
//    }
    public List<Course> findCoursesByStudentId(CourseSearchRequest request) {
        return courseRepository.findCoursesByStudentId(request.getStudentId());
    }


}
