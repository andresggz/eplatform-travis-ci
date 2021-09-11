package co.edu.udea.eplatform.component.roadmap.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CourseIdRoadmap {

    @Id
    private Long id;
}
