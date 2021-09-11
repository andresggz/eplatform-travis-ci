package co.edu.udea.eplatform.component.roadmap.io.web.v1.model;

import co.edu.udea.eplatform.component.roadmap.service.model.CourseAddCmd;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseAddRequest {

    @NotNull
    private Long courseId;

    public static CourseAddCmd toModel(CourseAddRequest courseToAdd){
        return CourseAddCmd.builder().courseId(courseToAdd.courseId).build();
    }
}
