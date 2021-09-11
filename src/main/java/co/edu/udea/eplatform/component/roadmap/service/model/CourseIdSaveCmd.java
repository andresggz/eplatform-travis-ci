package co.edu.udea.eplatform.component.roadmap.service.model;

import co.edu.udea.eplatform.component.roadmap.model.CourseIdRoadmap;
import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseIdSaveCmd {

    private Long id;

    private String name;

    private String description;

    private String level;

    private String iconId;

    private Boolean active;

    public static CourseIdRoadmap toModel(CourseIdSaveCmd courseToRegister){
        return CourseIdRoadmap.builder().id(courseToRegister.getId())
                .build();
    }
}
