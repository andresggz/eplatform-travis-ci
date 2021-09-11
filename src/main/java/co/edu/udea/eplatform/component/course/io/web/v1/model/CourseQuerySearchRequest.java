package co.edu.udea.eplatform.component.course.io.web.v1.model;

import co.edu.udea.eplatform.component.course.model.CourseLevel;
import co.edu.udea.eplatform.component.course.service.model.CourseQuerySearchCmd;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseQuerySearchRequest {

    private String name;

    private CourseLevel level;

    private Boolean active;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime releaseDateLessThan;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime releaseDateGreaterThan;

    public static CourseQuerySearchCmd toModel(CourseQuerySearchRequest queryCriteria){
        return CourseQuerySearchCmd.builder().name(queryCriteria.getName())
                    .level(queryCriteria.getLevel()).active(queryCriteria.getActive())
                    .releaseDateLessThan(queryCriteria.getReleaseDateLessThan())
                    .releaseDateGreaterThan(queryCriteria.getReleaseDateGreaterThan())
                    .build();
    }
}
