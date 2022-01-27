package com.monov.course.controller;

import com.monov.commons.dto.CourseSearchRequestDTO;
import com.monov.course.CourseServiceApplication;
import com.monov.course.entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.monov.commons.utils.StringUtils.asJsonString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CourseServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.config.location = classpath:integration-test-application.yaml" })
@Transactional
class CourseControllerIntTest {
    private final String firstSampleDataCourseID = "sampleID1";
    private final String secondSampleDataCourseID = "sampleID2";
    private final String firstSampleDataStudentID = UUID.randomUUID().toString();
    private final String secondSampleDataStudentID = UUID.randomUUID().toString();

    @Autowired
    private MockMvc mvc;

    @Test
    void getAllCourses() throws Exception{
        mvc.perform(get("/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void saveCourse() throws Exception{
        Course course = new Course();
        final String courseName = "TESTLiterature";
        course.setName(courseName);

        mvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(course)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(courseName)));
    }

    @Test
    void findCourseById() throws Exception{
        mvc.perform(get(String.format("/courses/%s",firstSampleDataCourseID))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstSampleDataCourseID)));
    }

    @Test
    void addStudentToCourse() throws Exception{
        final String courseId = firstSampleDataCourseID;
        final String studentId = firstSampleDataStudentID;

        addStudentToCourse(courseId,studentId);
    }

    @Test
    void findCoursesByStudentId() throws Exception{
        addStudentToCourse(firstSampleDataCourseID, firstSampleDataStudentID);
        addStudentToCourse(secondSampleDataCourseID, firstSampleDataStudentID);
        CourseSearchRequestDTO searchRequestDTO = new CourseSearchRequestDTO(firstSampleDataStudentID);
        mvc.perform(post("/courses/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(searchRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findStudentIdsByCourseId() throws Exception{
        addStudentToCourse(firstSampleDataCourseID,firstSampleDataStudentID);
        addStudentToCourse(firstSampleDataCourseID,secondSampleDataStudentID);

        mvc.perform(get(String.format("/courses/students/%s", firstSampleDataCourseID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder(firstSampleDataStudentID,secondSampleDataStudentID)));

    }

    private void addStudentToCourse(String courseId, String studentId) throws Exception{
        mvc.perform(post(String.format("/courses/%s/%s", courseId, studentId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(courseId)));
    }
}