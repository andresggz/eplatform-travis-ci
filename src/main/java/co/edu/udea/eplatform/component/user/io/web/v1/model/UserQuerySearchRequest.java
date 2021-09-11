package co.edu.udea.eplatform.component.user.io.web.v1.model;

import co.edu.udea.eplatform.component.user.service.model.UserQuerySearchCmd;
import lombok.*;

@Data
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserQuerySearchRequest {

    private String names;

    private String lastNames;

    private String username;

    private String primaryEmailAddress;

    public static UserQuerySearchCmd toModel(UserQuerySearchRequest queryCriteria){
        return UserQuerySearchCmd.builder().names(queryCriteria.getNames())
                .lastNames(queryCriteria.getLastNames()).username(queryCriteria.getUsername())
                .primaryEmailAddress(queryCriteria.getPrimaryEmailAddress())
                .build();
    }
}
