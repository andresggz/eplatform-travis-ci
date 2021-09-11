package co.edu.udea.eplatform.component.career.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "careers")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Career {

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

    private String iconId;

    private Boolean active;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RoadmapIdCareer> roadmapIds;

}
