package co.edu.udea.eplatform.component.user.io.web.v1;

import co.edu.udea.eplatform.component.shared.model.ErrorMessage;
import co.edu.udea.eplatform.component.shared.model.ResponsePagination;
import co.edu.udea.eplatform.component.user.io.web.v1.model.UserListResponse;
import co.edu.udea.eplatform.component.user.io.web.v1.model.UserQuerySearchRequest;
import co.edu.udea.eplatform.component.user.service.model.UserQuerySearchCmd;
import co.edu.udea.eplatform.component.user.service.model.UserSaveCmd;
import co.edu.udea.eplatform.component.user.io.web.v1.model.UserSaveRequest;
import co.edu.udea.eplatform.component.user.io.web.v1.model.UserSaveResponse;
import co.edu.udea.eplatform.component.user.model.User;
import co.edu.udea.eplatform.component.user.service.UserService;
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
@RequestMapping(path = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @PostMapping
    @ApiOperation(value = "Create an user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(exposedHeaders = {HttpHeaders.LOCATION})
    public ResponseEntity<Void> create(@Valid @NotNull @RequestBody UserSaveRequest userToCreate){
        logger.debug("Begin create: userToCreate = {}", userToCreate);

        UserSaveCmd userToCreateCmd = UserSaveRequest.toModel(userToCreate);

        User userCreated = userService.create(userToCreateCmd);

        URI location = fromUriString("/api/v1/users").path("/{id}")
                .buildAndExpand(userCreated.getId()).toUri();

        logger.debug("End create: userCreated = {}", userCreated);
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find an User by id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = UserSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)
    })
    public ResponseEntity<UserSaveResponse> findById(@Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin findById = {}", id);

        User userFound = userService.findById(id);

        logger.debug("End findById = {}", userFound);
        return ResponseEntity.ok(UserSaveResponse.fromModel(userFound));
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update an user.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success.", response = UserSaveResponse.class),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)

    })
    public ResponseEntity<UserSaveResponse> update(@Valid @RequestBody @NotNull UserSaveRequest userToUpdate,
                                                   @Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin update: id = {}, userToUpdate = {}", id, userToUpdate);

        UserSaveCmd userToUpdateCmd = UserSaveRequest.toModel(userToUpdate);

        User userUpdated = userService.update(id, userToUpdateCmd);

        logger.debug("End update: userUpdated = {}", userUpdated);
        return ResponseEntity.ok(UserSaveResponse.fromModel(userUpdated));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete an user.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Success."),
            @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Resource not found.", response = ErrorMessage.class),
            @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)

    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@Valid @PathVariable("id") @NotNull Long id){
        logger.debug("Begin delete: id = {}", id);

        userService.deleteById(id);

        logger.debug("End delete: id = {}", id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

   @GetMapping
   @ApiOperation(value = "Find users by parameters.", produces = MediaType.APPLICATION_JSON_VALUE)
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success", response = UserListResponse.class),
           @ApiResponse(code = 400, message = "Payload is invalid.", response = ErrorMessage.class),
           @ApiResponse(code = 500, message = "Internal server error.", response = ErrorMessage.class)

   })
    public ResponsePagination<UserListResponse> findByParameters(@Valid @NotNull UserQuerySearchRequest queryCriteria,
                                                     @PageableDefault(page = 0, size = 15,
                                 direction = Sort.Direction.DESC, sort = "id") Pageable pageable){
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable = {}", queryCriteria, pageable);

       UserQuerySearchCmd queryCriteriaCmd = UserQuerySearchRequest.toModel(queryCriteria);

       Page<User> usersFound = userService.findByParameters(queryCriteriaCmd, pageable);

       List<UserListResponse> usersFoundList = usersFound.stream()
               .map(UserListResponse::fromModel)
               .collect(Collectors.toList());

       logger.debug("End findByParameter: usersFound = {}", usersFound);

       return ResponsePagination.fromObject(usersFoundList, usersFound.getTotalElements(), usersFound.getNumber(),
               usersFoundList.size());
    }
}
