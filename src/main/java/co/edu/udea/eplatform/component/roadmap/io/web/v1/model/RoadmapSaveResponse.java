package co.edu.udea.eplatform.component.roadmap.io.web.v1.model;

import co.edu.udea.eplatform.component.roadmap.model.Roadmap;
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
public class RoadmapSaveResponse {

    private Long id;

    private String name;

    private String description;

    private String detail;

    private String iconId;

    private String bannerId;

    private Boolean active;

    private Integer totalCourses;

    private List<String> courses;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static RoadmapSaveResponse fromModel(Roadmap roadmap){
        return RoadmapSaveResponse.builder().id(roadmap.getId())
                .name(roadmap.getName()).description(roadmap.getDescription())
                .detail(roadmap.getDetail()).iconId(roadmap.getIconId())
                .active(roadmap.getActive()).detail(roadmap.getDetail())
                .createDate(roadmap.getCreateDate()).updateDate(roadmap.getUpdateDate())
                .totalCourses(roadmap.getCoursesIds().size())
                .build();
    }
}
