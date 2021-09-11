package co.edu.udea.eplatform.component.course;

import co.edu.udea.eplatform.component.course.model.CourseLevel;
import co.edu.udea.eplatform.component.course.service.CourseGateway;
import co.edu.udea.eplatform.component.course.service.CourseService;
import co.edu.udea.eplatform.component.course.service.model.CourseSaveCmd;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseServiceIT {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseGateway courseGateway;

    @Test
    void whenCreateACourseLackingReleaseDate_thenActiveIsFalse() throws Exception{
        CourseSaveCmd courseToCreate = CourseSaveCmd.builder()
                .name("Angular").description("Description about Angular course")
                .level(CourseLevel.ADVANCED).build();

        var courseCreated = courseService.create(courseToCreate);

        assertThat(courseCreated.getActive())
                .isFalse();
    }

    @Test
    void whenCreateACourseWithReleaseDate_thenActiveIsTrue() throws Exception{
        CourseSaveCmd courseToCreate = CourseSaveCmd.builder()
                .name("Angular").description("Description about Angular course")
                .releaseDate(LocalDateTime.now().plusDays(5))
                .level(CourseLevel.ADVANCED).build();

        var courseCreated = courseService.create(courseToCreate);

        assertThat(courseCreated.getActive())
                .isTrue();
    }
}
