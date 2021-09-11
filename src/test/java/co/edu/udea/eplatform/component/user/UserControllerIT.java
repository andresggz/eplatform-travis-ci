package co.edu.udea.eplatform.component.user;

import co.edu.udea.eplatform.component.user.io.web.v1.model.UserSaveRequest;
import co.edu.udea.eplatform.component.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc client;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void whenCreateAnUser_thenReturns201() throws Exception{

        UserSaveRequest userToCreate = UserSaveRequest.builder()
                .names("Andres").lastNames("Grisales").username("andres.grisalesg")
                .primaryEmailAddress("andres.grisales@eplatform.com")
                .password("123456abc")
                .primaryPhoneNumber("+573043567820").build();

        var result = client.perform(post("/api/v1/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userToCreate)));

        result.andExpect(status().isCreated());
    }

    @Test
    void whenCreateAnUserLackingPrimaryPhoneNumber_thenReturns201() throws Exception{
        UserSaveRequest userToCreate = UserSaveRequest.builder()
                .names("Andres").lastNames("Grisales").username("andres.grisalesg")
                .primaryEmailAddress("andres.grisalesg@eplatform.com")
                .password("123456abc").build();

        var result = client.perform(post("/api/v1/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userToCreate)));

        result.andExpect(status().isCreated());
    }

    @Test
    void whenCreateAnUserLackingNamesSize_thenReturn400() throws Exception{
        UserSaveRequest userToCreate = UserSaveRequest.builder()
                .names("A").lastNames("Grisales").username("andres.grisalesg")
                .primaryEmailAddress("andres.grisalesg@eplatform.com")
                .password("123456abc").build();

        var result = client.perform(post("/api/v1/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userToCreate)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void whenFindAnUserByIdThatNotExist_thenReturns404() throws Exception{
        var result = client.perform(get("/api/v1/users/{id}", 1));

        result.andExpect(status().isNotFound());
    }

    @Test
    void whenFindAnUserThatExist_thenReturnsThatUser() throws Exception{
        UserSaveRequest userToCreate = UserSaveRequest.builder()
                .names("Andres").lastNames("Grisales").username("andres.grisalesg")
                .primaryEmailAddress("andres.grisales@eplatform.com")
                .password("123456abc")
                .primaryPhoneNumber("+573043567820").build();
        client.perform(post("/api/v1/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userToCreate)));

        var result = client.perform(get("/api/v1/users/{id}", 1));

        result.andExpect(status().isOk());
    }

    @Test
    void whenDeleteAnUserThatExists_thenReturns204() throws Exception{
        UserSaveRequest userToCreate = UserSaveRequest.builder()
                .names("Andres").lastNames("Grisales").username("andres.grisalesg")
                .primaryEmailAddress("andres.grisales@eplatform.com")
                .password("123456abc")
                .primaryPhoneNumber("+573043567820").build();
        client.perform(post("/api/v1/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userToCreate)));

        var result = client.perform(delete("/api/v1/users/{id}", 1));

        result.andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteAnUserThatNotExist_thenReturns404() throws Exception{
        var result = client.perform(delete("/api/v1/users/{id}", 1));

        result.andExpect(status().isNotFound());
    }
}
