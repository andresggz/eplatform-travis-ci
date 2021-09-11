package co.edu.udea.eplatform.component.course.service.model;

import co.edu.udea.eplatform.component.course.model.Course;
import co.edu.udea.eplatform.component.course.model.CourseLevel;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSaveCmd {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 700)
    private String description;

    @NotNull
    private CourseLevel level;

    @Future
    private LocalDateTime releaseDate;

    public static Course toModel(@NotNull CourseSaveCmd courseToCreateCmd){
        return Course.builder().name(courseToCreateCmd.getName())
                .description(courseToCreateCmd.getDescription())
                .level(courseToCreateCmd.getLevel())
                .releaseDate(courseToCreateCmd.getReleaseDate())
                .build();
    }
}
