package co.edu.udea.eplatform.component.roadmap.io.web.v1.model;

import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapQuerySearchCmd;
import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoadmapQuerySearchRequest {

    private String name;

    private Boolean active;

    private Boolean isLinkedToRoute;

    public static RoadmapQuerySearchCmd toModel(RoadmapQuerySearchRequest queryCriteria){
        return RoadmapQuerySearchCmd.builder().name(queryCriteria.getName())
                    .active(queryCriteria.getActive())
                    .isLinkedToRoute(queryCriteria.getIsLinkedToRoute())
                    .build();
    }
}
