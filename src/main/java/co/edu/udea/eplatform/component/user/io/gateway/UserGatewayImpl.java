package co.edu.udea.eplatform.component.user.io.gateway;

import co.edu.udea.eplatform.component.user.io.repository.UserRepository;
import co.edu.udea.eplatform.component.user.model.User;
import co.edu.udea.eplatform.component.user.service.UserGateway;
import co.edu.udea.eplatform.component.shared.web.exception.NotFoundException;
import co.edu.udea.eplatform.component.user.service.model.UserQuerySearchCmd;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String RESOURCE_NOT_FOUND = "User not found";

    private final UserRepository userRepository;

    @Override
    public User save(@NotNull User userToCreate) {
        logger.debug("Begin save: userToCreate = {}", userToCreate);

        final User userToBeCreated =
                userToCreate.toBuilder().createDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .active(true)
                        .build();

        final User userCreated = userRepository.save(userToBeCreated);


        logger.debug("End save: userCreated = {}", userCreated);
        return userCreated;
    }

    @Override
    public User findById(@NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        final User userFound = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        logger.debug("End findById: userFound = {}", userFound);
        return userFound;
    }

    @Override
    public User update(@NotNull User userToUpdate) {
        logger.debug("Begin update: userToUpdate = {}", userToUpdate);

        final User userToBeUpdated =
                userToUpdate.toBuilder().updateDate(LocalDateTime.now()).build();

        final User userUpdated = userRepository.save(userToBeUpdated);

        logger.debug("End update = userUpdated = {}", userUpdated);
        return userUpdated;
    }

    @Override
    public void deleteById(@NotNull Long id) {
        logger.debug("Begin findById = {}", id);

        findById(id);
        userRepository.deleteById(id);

        logger.debug("End deleteById");
    }

    @Override
    public Page<User> findByParameters(UserQuerySearchCmd queryCriteria, Pageable pageable) {
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        Specification<User> specification = buildCriteria(queryCriteria);

        Page<User> usersFound = userRepository.findAll(specification, pageable);

        logger.debug("End findByParameters: usersFound = {}", usersFound);
        return usersFound;
    }

    private Specification<User> buildCriteria(@NotNull UserQuerySearchCmd queryCriteria){
        logger.debug("Begin buildCriteria: queryCriteria = {}", queryCriteria);

        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(nonNull(queryCriteria.getNames())){
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.like(root.get("names"), "%" + queryCriteria.getNames() + "%" )));
            }

            if(nonNull(queryCriteria.getLastNames())){
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.like(root.get("lastNames"), "%" + queryCriteria.getLastNames() + "%")));
            }

            if(nonNull(queryCriteria.getUsername())){
                predicates.add(criteriaBuilder
                .and(criteriaBuilder.like(root.get("username"), queryCriteria.getUsername() + "%")));
            }

            if(nonNull(queryCriteria.getPrimaryEmailAddress())){
                predicates.add(criteriaBuilder
                        .and(criteriaBuilder.like(root.get("primaryEmailAddress"), "%" + queryCriteria.getPrimaryEmailAddress() + "%")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
