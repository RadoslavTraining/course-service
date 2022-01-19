package com.monov.course.service;

import com.monov.commons.dto.CourseDTO;
import com.monov.commons.dto.CourseSearchRequest;
import com.monov.course.entity.Course;
import com.monov.course.exception.CourseNotFoundException;
import com.monov.course.exception.StudentAlreadyEnrolledException;
import com.monov.course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO findById(Long id){
        log.info("Inside findById method in CourseService");
        Optional<Course> course = courseRepository.findById(id);
        if(course.isPresent()) {
            return convertToCourseDTO(courseRepository.findById(id).get());
        }
        throw new CourseNotFoundException(id);
    }

    public List<CourseDTO> findAllCourses(){
        log.info("findAllCourses saveCourse method in CourseService");
        return  convertToCourseDTOs(courseRepository.findAll());
    }

    public CourseDTO saveCourse(Course course) {
        log.info("Inside saveCourse method in CourseService");
        return convertToCourseDTO(courseRepository.save(course));
    }

    public CourseDTO addStudentToCourse(Long courseId, Long studentId) {
        log.info("Inside addStudentToCourse method in CourseService");

        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent()) {
            Course courseToUpdate = course.get();
            if(courseToUpdate.getStudentIds().contains(studentId)) {
                throw new StudentAlreadyEnrolledException(courseId,studentId);
            }
            courseToUpdate.getStudentIds().add(studentId);
            courseRepository.save(courseToUpdate);
            return convertToCourseDTO(courseToUpdate);
        }
        throw new CourseNotFoundException(courseId);

    }

    public List<Long> findStudentIdsByCourseId(Long courseId) {
        return courseRepository.findStudentIdsByCourseId(courseId);
    }

    public List<CourseDTO> searchCourses(CourseSearchRequest request) {
        if(request.getStudentId() != null) {
            return convertToCourseDTOs(courseRepository.findCoursesByStudentId(request.getStudentId()));
        }

        return new ArrayList<>();
    }

    private List<CourseDTO> convertToCourseDTOs(List<Course> courseEntities) {
        return courseEntities.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    private CourseDTO convertToCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());

        return courseDTO;
    }

}
