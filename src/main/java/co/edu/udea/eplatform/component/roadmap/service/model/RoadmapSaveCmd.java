package co.edu.udea.eplatform.component.roadmap.service.model;

import co.edu.udea.eplatform.component.roadmap.model.Roadmap;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapSaveCmd {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 700)
    private String description;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String detail;

    public static Roadmap toModel(@NotNull RoadmapSaveCmd roadmapToCreateCmd){
        return Roadmap.builder().name(roadmapToCreateCmd.getName())
                .description(roadmapToCreateCmd.getDescription())
                .detail(roadmapToCreateCmd.getDetail())
                .build();
    }
}
