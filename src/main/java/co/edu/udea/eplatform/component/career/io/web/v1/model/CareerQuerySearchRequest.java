package co.edu.udea.eplatform.component.career.io.web.v1.model;

import co.edu.udea.eplatform.component.career.service.model.CareerQuerySearchCmd;
import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CareerQuerySearchRequest {

    private String name;

    private Boolean active;

    public static CareerQuerySearchCmd toModel(CareerQuerySearchRequest queryCriteria){
        return CareerQuerySearchCmd.builder().name(queryCriteria.getName())
                    .active(queryCriteria.getActive())
                    .build();
    }
}
