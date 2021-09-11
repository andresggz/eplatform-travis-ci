package co.edu.udea.eplatform.component.course;

import co.edu.udea.eplatform.component.course.io.web.v1.model.CourseSaveRequest;
import co.edu.udea.eplatform.component.course.model.CourseLevel;
import co.edu.udea.eplatform.component.course.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CourseControllerIT {

    @Autowired
    private MockMvc client;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateACourse_thenReturns201() throws Exception{

        CourseSaveRequest courseToCreate = CourseSaveRequest.builder()
                .name("Angular").description("This is a description about our Angular course")
                .level(CourseLevel.BEGINNER).releaseDate(LocalDateTime.now().plusDays(5))
                .build();

        var result = client.perform(post("/api/v1/courses")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(courseToCreate)));

        result.andExpect(status().isCreated());
    }

    @Test
    void whenCreateACourseLackingLevel_thenReturns400() throws Exception{
        CourseSaveRequest courseToCreate = CourseSaveRequest.builder()
                .name("Angular").description("This is a description about our Angular course")
                .build();

        var result = client.perform(post("/api/v1/courses")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(courseToCreate)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateACourseLackingCorrectReleaseDate_thenReturns400() throws Exception{
        CourseSaveRequest courseToCreate = CourseSaveRequest.builder()
                .name("Angular").description("This is a description about our Angular course")
                .releaseDate(LocalDateTime.now().minusDays(5))
                .build();

        var result = client.perform(post("/api/v1/courses")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(courseToCreate)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateACourseLackingNameSize_thenReturns400() throws Exception{
        CourseSaveRequest courseToCreate = CourseSaveRequest.builder()
                .name("A").description("This is a description about our Angular course")
                .build();

        var result = client.perform(post("/api/v1/courses")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(courseToCreate)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void whenFindACourseThatExists_thenReturns200() throws Exception{
        CourseSaveRequest courseToCreate = CourseSaveRequest.builder()
                .name("Angular").level(CourseLevel.INTERMEDIATE).description("Description about Angular course")
                .releaseDate(LocalDateTime.now().plusDays(5))
                .build();
        client.perform(post("/api/v1/courses")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(courseToCreate)));

        var result = client.perform(get("/api/v1/courses/{id}", 1)
                .contentType("application/json"));

        result.andExpect(status().isOk());
    }

    @Test
    void whenFindACourseThatNotExist_thenReturns404() throws Exception{
        var result = client.perform(get("/api/v1/courses/{id}", 1)
                .contentType("application/json"));

        result.andExpect(status().isNotFound());
    }
}
