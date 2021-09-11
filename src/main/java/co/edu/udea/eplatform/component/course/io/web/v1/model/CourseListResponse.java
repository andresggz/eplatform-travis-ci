package co.edu.udea.eplatform.component.course.io.web.v1.model;

import co.edu.udea.eplatform.component.course.model.Course;
import co.edu.udea.eplatform.component.course.model.CourseLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseListResponse {

    private Long id;

    private String name;

    private String description;

    private CourseLevel level;

    private String iconId;

    private Boolean active;

    private LocalDateTime releaseDate;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static CourseListResponse fromModel(Course course){
        return CourseListResponse.builder().id(course.getId())
                .name(course.getName()).description(course.getDescription())
                .level(course.getLevel()).iconId(course.getIconId())
                .releaseDate(course.getReleaseDate())
                .createDate(course.getCreateDate())
                .updateDate(course.getUpdateDate())
                .build();
    }
}
