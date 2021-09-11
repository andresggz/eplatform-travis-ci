package co.edu.udea.eplatform.component.career.io.web.v1.model;

import co.edu.udea.eplatform.component.career.model.Career;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerSaveResponse {

    private Long id;

    private String name;

    private String description;

    private String iconId;

    private Boolean active;

    private Integer totalRoadmaps;

    private List<String> roadmaps;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static CareerSaveResponse fromModel(Career career){
        return CareerSaveResponse.builder().id(career.getId())
                .name(career.getName()).description(career.getDescription())
                .totalRoadmaps(career.getRoadmapIds().size())
                .createDate(career.getCreateDate()).updateDate(career.getUpdateDate())
                .build();
    }
}
