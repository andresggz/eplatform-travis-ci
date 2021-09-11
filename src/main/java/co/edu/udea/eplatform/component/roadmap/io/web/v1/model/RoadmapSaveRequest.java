package co.edu.udea.eplatform.component.roadmap.io.web.v1.model;

import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapSaveCmd;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoadmapSaveRequest {

    @NotNull(message = "name has to be present.")
    @NotBlank(message = "name can't be blank.")
    @Size(min = 3, max = 45, message = "Please enter at least {min} and at most {max} characters.")
    private String name;

    @NotNull(message = "description has to be present.")
    @NotBlank(message = "description can't be blank.")
    @Size(min = 3, max = 700, message = "Please enter at least {min} and at most {max} characters.")
    private String description;

    @NotNull(message = "detail has to be present.")
    @NotBlank(message = "detail can't be blank.")
    @Size(min = 3, message = "Please enter at least {min} characters.")
    private String detail;

    public static RoadmapSaveCmd toModel(@NotNull RoadmapSaveRequest roadmapToCreate){
        return RoadmapSaveCmd.builder().name(roadmapToCreate.getName())
                .description(roadmapToCreate.getDescription())
                .detail(roadmapToCreate.getDetail())
                .build();
    }
}
