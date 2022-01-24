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

import static com.monov.commons.utils.StringUtils.asJsonString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
                .andExpect(jsonPath("$.name", is(courseName)))
                .andExpect(jsonPath("$.id", is(3)));
    }

    @Test
    void findCourseById() throws Exception{
        final Integer id = 1;
        mvc.perform(get(String.format("/courses/%d",id))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)));
    }

    @Test
    void addStudentToCourse() throws Exception{
        final Integer courseId = 1;
        final Integer studentId = 1;

        addStudentToCourse(courseId,studentId);
    }

    @Test
    void findCoursesByStudentId() throws Exception{
        final Integer firstCourseId=1;
        final Integer secondCourseId=2;
        final Integer studentId=1;
        addStudentToCourse(firstCourseId,studentId);
        addStudentToCourse(secondCourseId,studentId);
        CourseSearchRequestDTO searchRequestDTO = new CourseSearchRequestDTO(Long.valueOf(studentId));
        mvc.perform(post("/courses/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(searchRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findStudentIdsByCourseId() throws Exception{
        final Integer courseId=1;
        final Integer firstStudentId=1;
        final Integer secondStudentId=2;
        addStudentToCourse(courseId,firstStudentId);
        addStudentToCourse(courseId,secondStudentId);

        mvc.perform(get(String.format("/courses/students/%d", courseId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder(firstStudentId,secondStudentId)));

    }

    private void addStudentToCourse(Integer courseId, Integer studentId) throws Exception{
        mvc.perform(post(String.format("/courses/%d/%d", courseId, studentId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(courseId)));
    }
}