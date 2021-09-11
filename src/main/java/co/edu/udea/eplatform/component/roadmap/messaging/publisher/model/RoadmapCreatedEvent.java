package co.edu.udea.eplatform.component.roadmap.messaging.publisher.model;

import lombok.*;


@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RoadmapCreatedEvent {

    private Long id;

    private String name;

    private String description;
}
