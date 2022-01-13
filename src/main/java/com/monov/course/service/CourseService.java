package com.monov.course.service;

import com.monov.course.data.Student;
import com.monov.course.entity.Course;
import com.monov.course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.agreement.srp.SRP6Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class CourseService {

    private static final String API_GATEWAY = "API-GATEWAY";
    private static final String STUDENTS_ENDPOINT = "students";

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RestTemplate restTemplate;

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
        Student student = addCourseIdToStudent(courseId,studentId);
        return courseToUpdate;
    }

    public Student addCourseIdToStudent(Long courseId, Long studentId) {
        return restTemplate.postForObject(String.format("http://%s/%s/%d/%d",API_GATEWAY,STUDENTS_ENDPOINT,studentId,courseId),
                null,
                Student.class);
    }

}
