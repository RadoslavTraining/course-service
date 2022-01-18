package com.monov.course.repository;

import com.monov.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {

    Optional<Course> findById(Long id);

    @Query(value = "SELECT student_id FROM course_student_ids where course_id = :courseid",
            nativeQuery = true)
    List<Long> findStudentIdsByCourseId(@Param("courseid") Long courseId);

    @Query(value = "SELECT course_id FROM course_student_ids WHERE student_id = :studentid",
            nativeQuery = true)
    List<Long> findCourseIdsByStudentId(@Param("studentid") Long studentId);

    @Query(value = "SELECT * FROM COURSE as c JOIN course_student_ids as csids  ON c.id=csids.course_id where " +
            "csids.student_id=:studentid", nativeQuery = true)
    List<Course> findCoursesByStudentId(@Param("studentid") Long studentid);
}
