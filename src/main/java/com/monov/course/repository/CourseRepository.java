package com.monov.course.repository;

import com.monov.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {

    Course findByCourseId(Long id);

}
