package co.edu.udea.eplatform.component.user.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    @Column(unique = true)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    @Email
    @Column(unique = true)
    private String primaryEmailAddress;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 45)
    private String password;

    @NotNull
    private Boolean active;

    @Size(min = 5, max = 25)
    private String primaryPhoneNumber;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;


}
