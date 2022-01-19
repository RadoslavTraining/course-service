package com.monov.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Validated
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @NotEmpty
    @NotNull
    private String name;
    @ElementCollection
    @CollectionTable(name = "course_student_ids"
            ,joinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id")
    )
    @Column(name = "student_id")
    private List<Long> studentIds;

}
