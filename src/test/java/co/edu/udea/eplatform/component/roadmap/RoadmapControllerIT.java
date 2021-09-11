package co.edu.udea.eplatform.component.roadmap;

import co.edu.udea.eplatform.component.roadmap.io.web.v1.model.RoadmapSaveRequest;
import co.edu.udea.eplatform.component.roadmap.service.RoadmapService;
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
public class RoadmapControllerIT {

    @Autowired
    private MockMvc client;

    @Autowired
    private RoadmapService roadmapService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateARoadmap_thenReturns201() throws Exception{

        RoadmapSaveRequest roadmapToCreate = RoadmapSaveRequest.builder()
                .name("Back-end con Java").description("This is a description about our back-end with java roadmap")
                .detail("This is our detail")
                .build();

        var result = client.perform(post("/api/v1/roadmaps")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roadmapToCreate)));

        result.andExpect(status().isCreated());
    }

    @Test
    void whenCreateARoadmapLackingNameSize_thenReturns400() throws Exception{
        RoadmapSaveRequest roadmapToCreate = RoadmapSaveRequest.builder()
                .name("A").description("This is a description about our front-end roadmap")
                .detail("This is our roadmap")
                .build();

        var result = client.perform(post("/api/v1/roadmaps")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roadmapToCreate)));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void whenFindARoadmapThatExists_thenReturns200() throws Exception{
        RoadmapSaveRequest roadmapToCreate = RoadmapSaveRequest.builder()
                .name("Front-end con React").detail("This is a detail about").description("Description about Angular")
                .build();
        client.perform(post("/api/v1/roadmaps")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(roadmapToCreate)));

        var result = client.perform(get("/api/v1/roadmaps/{id}", 1)
                .contentType("application/json"));

        result.andExpect(status().isOk());
    }

    @Test
    void whenFindARoadmapThatNotExist_thenReturns404() throws Exception{
        var result = client.perform(get("/api/v1/roadmaps/{id}", 1)
                .contentType("application/json"));

        result.andExpect(status().isNotFound());
    }
}
