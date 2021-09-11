package co.edu.udea.eplatform.component.career.io.web.v1;

import co.edu.udea.eplatform.component.career.io.web.v1.model.*;
import co.edu.udea.eplatform.component.career.model.Career;
import co.edu.udea.eplatform.component.career.service.CareerService;
import co.edu.udea.eplatform.component.career.service.model.CareerQuerySearchCmd;
import co.edu.udea.eplatform.component.career.service.model.CareerSaveCmd;
import co.edu.udea.eplatform.component.career.service.model.RoadmapAddCmd;
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
@RequestMapping(path = "/api/v1/careers", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CareerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CareerService careerService;

    @PostMapping
    @ApiOperation(value = "Create a career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
    public ResponseEntity<Void> create(@Valid @NotNull @RequestBody CareerSaveRequest careerToCreate){
        logger.debug("Begin create: careerToCreate = {}", careerToCreate);

        CareerSaveCmd careerToCreateCmd = CareerSaveRequest.toModel(careerToCreate);

        Career careerCreated = careerService.create(careerToCreateCmd);

        URI location = fromUriString("/api/v1/careers").path("/{id}")
                .buildAndExpand(careerCreated.getId()).toUri();

        logger.debug("End create: careerCreated = {}", careerCreated);
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find a career by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = CareerSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    public ResponseEntity<CareerSaveResponse> findById(@Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin findById: id = {}", id);

        Career careerFound = careerService.findById(id);

        CareerSaveResponse careerToResponse = CareerSaveResponse.fromModel(careerFound);

        List<String> roadmapLinksToResponse = careerFound.getRoadmapIds()
                .stream()
                .map( roadmapIdCareer -> String.format("/api/v1/roadmaps/%s", roadmapIdCareer.getId()))
                .collect(Collectors.toList());

        careerToResponse.setRoadmaps(roadmapLinksToResponse);

        logger.debug("End findById: careerFound = {}", careerFound);
        return ResponseEntity.ok(careerToResponse);
    }

    @GetMapping
    @ApiOperation(value = "Find careers by parameters.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CareerListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)

    })
    public ResponsePagination<CareerListResponse> findByParameters(@Valid @NotNull CareerQuerySearchRequest queryCriteria,
                                                                   @PageableDefault(page = 0, size = 15,
                                                                   direction = Sort.Direction.DESC, sort = "id") Pageable pageable){
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        CareerQuerySearchCmd queryCriteriaCmd = CareerQuerySearchRequest.toModel(queryCriteria);

        Page<Career> careersFound = careerService.findByParameters(queryCriteriaCmd, pageable);

        List<CareerListResponse> careersFoundList = careersFound.stream()
                .map(CareerListResponse::fromModel)
                .collect(Collectors.toList());

        logger.debug("End findByParameters: careersFound = {}", careersFound);
        return ResponsePagination.fromObject(careersFoundList, careersFound.getTotalElements(), careersFound.getNumber(),
                careersFoundList.size());
    }

    @PatchMapping(path = "/{id}/roadmaps")
    @ApiOperation(value = "Add roadmap to career.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CareerSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)

    })
    public ResponseEntity<CareerSaveResponse> addRoadmap(@Valid @PathVariable("id") @NotNull Long id,
                                                         @Valid @RequestBody @NotNull RoadmapAddRequest roadmapToAdd){
        logger.debug("Begin addRoadmap: id = {}, roadmapToAdd = {}", id, roadmapToAdd);

        RoadmapAddCmd roadmapToAddCmd = RoadmapAddRequest.toModel(roadmapToAdd);

        Career careerUpdated = careerService.addRoadmap(id, roadmapToAddCmd);

        CareerSaveResponse careerUpdatedToResponse = CareerSaveResponse.fromModel(careerUpdated);

        List<String> roadmapLinksToResponse = careerUpdated.getRoadmapIds()
                .stream()
                .map( roadmapIdCareer -> String.format("/api/v1/roadmaps/%s", roadmapIdCareer.getId()))
                .collect(Collectors.toList());

        careerUpdatedToResponse.setRoadmaps(roadmapLinksToResponse);

        logger.debug("End addRoadmap: careerUpdated = {}", careerUpdated);
        return ResponseEntity.ok(careerUpdatedToResponse);
    }

    @PostMapping("/upload-sheets")
    @ApiOperation(value = "Create careers from sheets", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> createFromSheets(@RequestParam("file") @NotNull MultipartFile careersToCreateFromSheets){
        logger.debug("Begin createFromSheets: careersToCreate");

        careerService.createFromSheets(careersToCreateFromSheets);

        logger.debug("End createFromSheets");
       return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update a career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = CareerSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    public ResponseEntity<CareerSaveResponse> update(@Valid @RequestBody @NotNull CareerSaveRequest careerToUpdate,
                                                     @Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin update: careerToUpdate = {}, id = {}", careerToUpdate, id);

        CareerSaveCmd careerToUpdateCmd = CareerSaveRequest.toModel(careerToUpdate);

        Career careerUpdated = careerService.update(id, careerToUpdateCmd);

        logger.debug("End update: careerUpdated = {}", careerUpdated);

        return ResponseEntity.ok(CareerSaveResponse.fromModel(careerUpdated));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete a career", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin delete: id = {}", id);

        careerService.deleteById(id);

        logger.debug("End delete: id = {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
