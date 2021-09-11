package co.edu.udea.eplatform.component.roadmap.io.web.v1;

import co.edu.udea.eplatform.component.roadmap.io.web.v1.model.*;
import co.edu.udea.eplatform.component.roadmap.model.Roadmap;
import co.edu.udea.eplatform.component.roadmap.service.RoadmapService;
import co.edu.udea.eplatform.component.roadmap.service.model.CourseAddCmd;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapQuerySearchCmd;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapSaveCmd;
import co.edu.udea.eplatform.component.shared.model.ErrorMessage;
import co.edu.udea.eplatform.component.shared.model.ResponsePagination;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/roadmaps", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoadmapController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RoadmapService roadmapService;

    @PostMapping
    @ApiOperation(value = "Create a roadmap", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
    public ResponseEntity<Void> create(@Valid @NotNull @RequestBody RoadmapSaveRequest roadmapToCreate){
        logger.debug("Begin create: roadmapToCreate = {}", roadmapToCreate);

        RoadmapSaveCmd roadmapToCreateCmd = RoadmapSaveRequest.toModel(roadmapToCreate);

        Roadmap roadmapCreated = roadmapService.create(roadmapToCreateCmd);

        URI location = fromUriString("/api/v1/roadmaps").path("/{id}")
                .buildAndExpand(roadmapCreated.getId()).toUri();

        logger.debug("End create: roadmapCreated = {}", roadmapCreated);
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find a roadmap by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = RoadmapSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    public ResponseEntity<RoadmapSaveResponse> findById(@Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin findById: id = {}", id);

        Roadmap roadmapFound = roadmapService.findById(id);

        RoadmapSaveResponse roadmapToResponse = RoadmapSaveResponse.fromModel(roadmapFound);

        List<String> courseLinksToResponse = roadmapFound.getCoursesIds()
                .stream()
                .map(courseIdRoadmap -> String.format("/api/v1/courses/%s", courseIdRoadmap.getId()))
                .collect(Collectors.toList());

        roadmapToResponse.setCourses(courseLinksToResponse);


        logger.debug("End findById: roadmapFound = {}", roadmapFound);
        return ResponseEntity.ok(roadmapToResponse);
    }

    @GetMapping
    @ApiOperation(value = "Find roadmaps by parameters.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RoadmapSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)

    })
    public ResponsePagination<RoadmapListResponse> findByParameters(@Valid @NotNull RoadmapQuerySearchRequest queryCriteria,
                                                                    @PageableDefault(page = 0, size = 15,
                                                                   direction = Sort.Direction.DESC, sort = "id") Pageable pageable){
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        RoadmapQuerySearchCmd queryCriteriaCmd = RoadmapQuerySearchRequest.toModel(queryCriteria);

        Page<Roadmap> roadmapsFound = roadmapService.findByParameters(queryCriteriaCmd, pageable);

        List<RoadmapListResponse> roadmapsFoundList = roadmapsFound.stream()
                .map(RoadmapListResponse::fromModel)
                .collect(Collectors.toList());

        logger.debug("End findByParameters: roadmapsFound = {}", roadmapsFound);
        return ResponsePagination.fromObject(roadmapsFoundList, roadmapsFound.getTotalElements(), roadmapsFound.getNumber(),
                roadmapsFoundList.size());
    }

    @PatchMapping(path = "/{id}/courses")
    @ApiOperation(value = "Add course to roadmap.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RoadmapSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)

    })
    public ResponseEntity<RoadmapSaveResponse> addCourse(@Valid @PathVariable("id") @NotNull Long id,
                                                         @Valid @RequestBody @NotNull CourseAddRequest courseToAdd){
        logger.debug("Begin addCourse: id = {}, courseToAdd = {}", id, courseToAdd);

        CourseAddCmd courseToAddCmd = CourseAddRequest.toModel(courseToAdd);

        Roadmap roadmapUpdated = roadmapService.addCourse(id, courseToAddCmd);

        RoadmapSaveResponse roadmapToResponse = RoadmapSaveResponse.fromModel(roadmapUpdated);

        List<String> courseLinksToResponse = roadmapUpdated.getCoursesIds()
                .stream()
                .map(courseIdRoadmap -> String.format("/api/v1/courses/%s", courseIdRoadmap.getId()))
                .collect(Collectors.toList());

        roadmapToResponse.setCourses(courseLinksToResponse);


        logger.debug("End addCourse: roadmapUpdated = {}", roadmapUpdated);
        return ResponseEntity.ok(roadmapToResponse);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete a roadmap", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin delete: id = {}", id);

        roadmapService.deleteById(id);

        logger.debug("End delete: id = {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload-sheets")
    @ApiOperation(value = "Create roadmaps from sheets", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<URI>> createFromSheets(@RequestParam("file") @NotNull MultipartFile careersToCreateFromSheets){
        logger.debug("Begin createFromSheets: careersToCreate");

        List<Roadmap> roadmapsCreated = roadmapService.createFromSheets(careersToCreateFromSheets);

        List<URI> roadmapsUrisCreated = roadmapsCreated.stream()
                .map(roadmap -> fromUriString("/api/v1/roadmaps").path("/{id}").buildAndExpand(roadmap.getId()).toUri())
                .collect(Collectors.toList());

        logger.debug("End createFromSheets");
        return ResponseEntity.ok(roadmapsUrisCreated);
    }
}
