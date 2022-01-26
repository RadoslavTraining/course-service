package com.monov.course.repository;

import com.monov.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,String> {

    Optional<Course> findById(String id);

    @Query(value = "SELECT student_id FROM course_student_ids where course_id = :courseid",
            nativeQuery = true)
    List<String> findStudentIdsByCourseId(@Param("courseid") String courseId);

    @Query(value = "SELECT * FROM COURSE as c JOIN course_student_ids as csids  ON c.id=csids.course_id where " +
            "csids.student_id=:studentid", nativeQuery = true)
    List<Course> findCoursesByStudentId(@Param("studentid") String studentid);

}
