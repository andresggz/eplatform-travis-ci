package co.edu.udea.eplatform.component.roadmap.io.web.v1.model;

import co.edu.udea.eplatform.component.roadmap.model.Roadmap;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapListResponse {

    private Long id;

    private String name;

    private String description;

    private String detail;

    private String iconId;

    private String bannerId;

    private Boolean active;

    private Integer totalCourses;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static RoadmapListResponse fromModel(Roadmap roadmap){
        return RoadmapListResponse.builder().id(roadmap.getId())
                .name(roadmap.getName()).description(roadmap.getDescription())
                .detail(roadmap.getDetail()).iconId(roadmap.getIconId())
                .bannerId(roadmap.getBannerId())
                .createDate(roadmap.getCreateDate())
                .updateDate(roadmap.getUpdateDate())
                .build();
    }
}
