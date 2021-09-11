package co.edu.udea.eplatform.component.user.io.web.v1.model;

import co.edu.udea.eplatform.component.user.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListResponse {

    private Long id;

    private String names;

    private String lastNames;

    private String username;

    private String primaryEmailAddress;

    private LocalDateTime updateDate;

    public static UserListResponse fromModel(User user){
        return UserListResponse.builder().id(user.getId())
                .names(user.getNames()).lastNames(user.getLastNames())
                .username(user.getUsername()).primaryEmailAddress(user.getPrimaryEmailAddress())
                .updateDate(user.getUpdateDate()).build();
    }
}
