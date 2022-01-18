package com.monov.course.dto;

import com.monov.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private Long id;
    private String name;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }

}
