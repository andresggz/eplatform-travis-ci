package co.edu.udea.eplatform.component.course.io.web.v1;

import co.edu.udea.eplatform.component.course.io.web.v1.model.CourseListResponse;
import co.edu.udea.eplatform.component.course.io.web.v1.model.CourseQuerySearchRequest;
import co.edu.udea.eplatform.component.course.io.web.v1.model.CourseSaveRequest;
import co.edu.udea.eplatform.component.course.io.web.v1.model.CourseSaveResponse;
import co.edu.udea.eplatform.component.course.model.Course;
import co.edu.udea.eplatform.component.course.service.CourseService;
import co.edu.udea.eplatform.component.course.service.model.CourseQuerySearchCmd;
import co.edu.udea.eplatform.component.course.service.model.CourseSaveCmd;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(path = "/api/v1/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CourseService courseService;

    @PostMapping
    @ApiOperation(value = "Create a course", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
    public ResponseEntity<Void> create(@Valid @NotNull @RequestBody CourseSaveRequest courseToCreate){
        logger.debug("Begin create: courseToCreate = {}", courseToCreate);

        CourseSaveCmd courseToCreateCmd = CourseSaveRequest.toModel(courseToCreate);

        Course courseCreated = courseService.create(courseToCreateCmd);

        URI location = fromUriString("/api/v1/courses").path("/{id}")
                .buildAndExpand(courseCreated.getId()).toUri();

        logger.debug("End create: courseCreated = {}", courseCreated);
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find a course by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = CourseSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    public ResponseEntity<CourseSaveResponse> findById(@Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin findById: id = {}", id);

        Course courseFound = courseService.findById(id);

        logger.debug("End findById: courseFound = {}", courseFound);
        return ResponseEntity.ok(CourseSaveResponse.fromModel(courseFound));
    }

    @GetMapping
    @ApiOperation(value = "Find courses by parameters.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CourseListResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)

    })
    public ResponsePagination<CourseListResponse> findByParameters(@Valid @NotNull CourseQuerySearchRequest queryCriteria,
                                                                   @PageableDefault(page = 0, size = 15,
                                                                   direction = Sort.Direction.DESC, sort = "id") Pageable pageable){
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        CourseQuerySearchCmd queryCriteriaCmd = CourseQuerySearchRequest.toModel(queryCriteria);

        Page<Course> coursesFound = courseService.findByParameters(queryCriteriaCmd, pageable);

        List<CourseListResponse> coursesFoundList = coursesFound.stream().map(CourseListResponse::fromModel)
                .collect(Collectors.toList());

        logger.debug("End findByParameters: coursesFound = {}", coursesFound);
        return ResponsePagination.fromObject(coursesFoundList, coursesFound.getTotalElements(), coursesFound.getNumber(),
                coursesFoundList.size());
    }

}
