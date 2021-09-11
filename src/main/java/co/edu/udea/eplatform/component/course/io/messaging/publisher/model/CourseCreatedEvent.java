package co.edu.udea.eplatform.component.course.io.messaging.publisher.model;

import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseCreatedEvent {

    private Long id;

    private String name;

    private String description;

    private String level;

    private String iconId;

    private Boolean active;
}
