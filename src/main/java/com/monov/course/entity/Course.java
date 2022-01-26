package com.monov.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Validated
public class Course {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "com.monov.commons.utils.StringUUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String  id;
    @NotEmpty
    private String name;
    @ElementCollection
    @CollectionTable(name = "course_student_ids"
            ,joinColumns = @JoinColumn(name = "course_id",referencedColumnName = "id")
    )
    @Column(name = "student_id")
    private List<String> studentIds;

}
