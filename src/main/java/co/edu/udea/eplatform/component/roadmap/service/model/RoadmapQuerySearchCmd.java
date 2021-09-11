package co.edu.udea.eplatform.component.roadmap.service.model;

import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoadmapQuerySearchCmd {

    private String name;

    private Boolean active;

    private Boolean isLinkedToRoute;
}
