package co.edu.udea.eplatform.component.career.io.web.v1.model;

import co.edu.udea.eplatform.component.career.service.model.CareerSaveCmd;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CareerSaveRequest {

    @NotNull(message = "name has to be present.")
    @NotBlank(message = "name can't be blank.")
    @Size(min = 3, max = 45, message = "Please enter at least {min} and at most {max} characters.")
    private String name;

    @NotNull(message = "description has to be present.")
    @NotBlank(message = "description can't be blank.")
    @Size(min = 3, max = 700, message = "Please enter at least {min} and at most {max} characters.")
    private String description;

    public static CareerSaveCmd toModel(@NotNull CareerSaveRequest roadmapToCreate){
        return CareerSaveCmd.builder().name(roadmapToCreate.getName())
                .description(roadmapToCreate.getDescription())
                .build();
    }
}
