package co.edu.udea.eplatform.component.user.io.web.v1.model;

import co.edu.udea.eplatform.component.user.service.model.UserSaveCmd;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveRequest {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String names;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String lastNames;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 35)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    @Email
    private String primaryEmailAddress;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 45)
    @ToString.Exclude
    private String password;

    @Size(min = 5, max = 25)
    private String primaryPhoneNumber;

    public static UserSaveCmd toModel(@NotNull UserSaveRequest userToCreate){
        return UserSaveCmd.builder().names(userToCreate.getNames()).lastNames(userToCreate.getLastNames())
                .username(userToCreate.getUsername()).primaryEmailAddress(userToCreate.getPrimaryEmailAddress())
                .password(userToCreate.getPassword()).primaryPhoneNumber(userToCreate.getPrimaryPhoneNumber())
                .build();
    }
}
