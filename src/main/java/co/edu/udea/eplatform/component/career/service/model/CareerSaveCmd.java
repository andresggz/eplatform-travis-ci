package co.edu.udea.eplatform.component.career.service.model;

import co.edu.udea.eplatform.component.career.model.Career;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareerSaveCmd {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 700)
    private String description;


    public static Career toModel(@NotNull CareerSaveCmd roadmapToCreateCmd){
        return Career.builder().name(roadmapToCreateCmd.getName())
                .description(roadmapToCreateCmd.getDescription())
                .build();
    }
}
