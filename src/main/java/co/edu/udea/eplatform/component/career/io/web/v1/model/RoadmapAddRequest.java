package co.edu.udea.eplatform.component.career.io.web.v1.model;

import co.edu.udea.eplatform.component.career.service.model.RoadmapAddCmd;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RoadmapAddRequest {

    @NotNull
    private Long roadmapId;

    public static RoadmapAddCmd toModel(RoadmapAddRequest roadmapToAdd){
        return RoadmapAddCmd.builder()
                .roadmapId(roadmapToAdd.getRoadmapId())
                .build();
    }
}
