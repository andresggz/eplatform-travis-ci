package co.edu.udea.eplatform.component.roadmap.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "roadmaps")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @Lob
    @NotNull
    @NotBlank
    @Size(min = 3, max = 700)
    private String description;

    @Lob
    @NotNull
    @NotBlank
    @Size(min = 3)
    private String detail;

    private String iconId;

    private String bannerId;

    private Boolean active;

    private Boolean linkedToRoute;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @ManyToMany
    private Set<CourseIdRoadmap> coursesIds;

}
