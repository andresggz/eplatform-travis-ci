package co.edu.udea.eplatform.component.career;

import co.edu.udea.eplatform.component.career.io.web.v1.model.CareerSaveRequest;
import co.edu.udea.eplatform.component.career.service.CareerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CareerControllerIT {

    @Autowired
    private MockMvc client;

    @Autowired
    private CareerService careerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateACareer_thenReturns201() throws Exception{

        CareerSaveRequest careerToCreate = CareerSaveRequest.builder()
                .name("Programming and development").description("Career about programming")
                .build();

        var result = client.perform(post("/api/v1/careers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(careerToCreate)));

        result.andExpect(status().isCreated());
    }

    @Test
    void whenCreateACareerLackingNameSize_thenReturns400() throws Exception{
        CareerSaveRequest careerToCreate = CareerSaveRequest.builder()
                .name("A").description("This is a description about our career")
                .build();

        var result = client.perform(post("/api/v1/careers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(careerToCreate)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void whenFindACareerThatExists_thenReturns200() throws Exception{
        CareerSaveRequest careerToCreate = CareerSaveRequest.builder()
                .name("Design and UX").description("Description about Design and UX career")
                .build();
        client.perform(post("/api/v1/careers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(careerToCreate)));

        var result = client.perform(get("/api/v1/careers/{id}", 1)
                .contentType("application/json"));

        result.andExpect(status().isOk());
    }

    @Test
    void whenFindACareerThatNotExist_thenReturns404() throws Exception{
        var result = client.perform(get("/api/v1/careers/{id}", 1)
                .contentType("application/json"));

        result.andExpect(status().isNotFound());
    }
}
