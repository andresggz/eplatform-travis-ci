package co.edu.udea.eplatform.component.career.io.web.v1.model;

import co.edu.udea.eplatform.component.career.model.Career;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerListResponse {

    private Long id;

    private String name;

    private String description;

    private String iconId;

    private Boolean active;

    private Integer totalRoadmaps;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static CareerListResponse fromModel(Career career){
        return CareerListResponse.builder().id(career.getId())
                .name(career.getName()).description(career.getDescription())
                .totalRoadmaps(career.getRoadmapIds().size())
                .createDate(career.getCreateDate())
                .updateDate(career.getUpdateDate())
                .build();
    }
}
