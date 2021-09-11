package co.edu.udea.eplatform.component.course.io.web.v1.model;

import co.edu.udea.eplatform.component.course.model.CourseLevel;
import co.edu.udea.eplatform.component.course.service.model.CourseSaveCmd;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseSaveRequest {

    @NotNull(message = "name has to be present.")
    @NotBlank(message = "name can't be blank.")
    @Size(min = 3, max = 45, message = "Please enter at least {min} and at most {max} characters.")
    private String name;

    @NotNull(message = "description has to be present.")
    @NotBlank(message = "description can't be blank.")
    @Size(min = 3, max = 700, message = "Please enter at least {min} and at most {max} characters.")
    private String description;

    @NotNull(message = "level has to be present.")
    private CourseLevel level;

    @Future(message = "releaseDate has to be in the future.")
    private LocalDateTime releaseDate;

    public static CourseSaveCmd toModel(CourseSaveRequest courseToCreate) {
        return CourseSaveCmd.builder().name(courseToCreate.getName())
                .description(courseToCreate.getDescription())
                .level(courseToCreate.getLevel())
                .releaseDate(courseToCreate.getReleaseDate())
                .build();
    }
}
