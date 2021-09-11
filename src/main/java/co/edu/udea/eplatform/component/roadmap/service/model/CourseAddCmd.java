package co.edu.udea.eplatform.component.roadmap.service.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseAddCmd {

    @NotNull
    private Long courseId;

}
