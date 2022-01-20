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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    @ElementCollection
    @CollectionTable(name = "course_student_ids"
            ,joinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id")
    )
    @Column(name = "student_id")
    private List<Long> studentIds;

}
