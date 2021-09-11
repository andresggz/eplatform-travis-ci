package co.edu.udea.eplatform.component.course.service.model;

import co.edu.udea.eplatform.component.course.model.CourseLevel;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseQuerySearchCmd {

    private String name;

    private CourseLevel level;

    private Boolean active;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime releaseDateLessThan;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime releaseDateGreaterThan;
}
