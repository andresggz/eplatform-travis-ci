package co.edu.udea.eplatform.component.career.service.model;

import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoadmapIdSaveCmd {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private String description;

    public static RoadmapIdCareer toModel(RoadmapIdSaveCmd roadmapIdToRegister){
        return RoadmapIdCareer.builder().id(roadmapIdToRegister.getId())
                .build();
    }
}
