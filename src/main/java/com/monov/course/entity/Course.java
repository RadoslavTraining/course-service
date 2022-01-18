package com.monov.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "courseid")
    private Long courseId;
    private String name;
    @ElementCollection
    @CollectionTable(name = "course_student_ids"
            ,joinColumns = @JoinColumn(name = "course_id",referencedColumnName = "courseid")
    )
    @Column(name = "student_id")
    private List<Long> studentIds;

}
