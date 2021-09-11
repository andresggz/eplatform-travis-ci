package co.edu.udea.eplatform.component.career.io.gateway;

import co.edu.udea.eplatform.component.career.io.repository.CareerRepository;
import co.edu.udea.eplatform.component.career.model.Career;
import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;
import co.edu.udea.eplatform.component.career.service.CareerGateway;
import co.edu.udea.eplatform.component.career.service.model.CareerQuerySearchCmd;
import co.edu.udea.eplatform.component.shared.web.exception.NotFoundException;
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
public class CareerGatewayImpl implements CareerGateway {

    private static final String RESOURCE_NOT_FOUND = "Career not found";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CareerRepository careerRepository;

    @Override
    public Career save(@NotNull Career careerToCreate) {
        logger.debug("Begin save: careerToCreate = {}", careerToCreate);

        final Career careerToBeCreated =
                careerToCreate.toBuilder().createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        final Career careerCreated = careerRepository.save(careerToBeCreated);

        logger.debug("End save: careerCreated = {}", careerCreated);
        return careerCreated;
    }

    @Override
    public Career findById(@NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        Career careerFound = careerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        logger.debug("End findById: careerFound = {}", careerFound);
        return careerFound;
    }

    @Override
    public Page<Career> findByParameters(@NotNull CareerQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {
        logger.debug("Begin findByParameters: queryCriteria = {}. pageable = {}", queryCriteria, pageable);

        Specification<Career> specification = buildCriteria(queryCriteria);

        Page<Career> roadmapsFound = careerRepository.findAll(specification, pageable);

        logger.debug("End findByParameters: roadmapsFound = {}", roadmapsFound);
        return roadmapsFound;
    }

    @Override
    public Career addRoadmap(@NotNull Long careerId, @NotNull RoadmapIdCareer roadmapIdInDataBase) {
        logger.debug("Begin addRoadmap: careerId = {}, roadmapIdInDataBase = {}", careerId, roadmapIdInDataBase);

        Career careerToBeUpdated = findById(careerId);

        careerToBeUpdated.getRoadmapIds().add(roadmapIdInDataBase);

        Career careerUpdated = careerRepository.save(careerToBeUpdated);

        logger.debug("End addRoadmap: careerUpdated = {}", careerUpdated);
        return careerUpdated;
    }

    @Override
    public Career update(@NotNull Career careerToUpdate) {
        logger.debug("Begin update: careerToUpdate = {}", careerToUpdate);

        final Career careerToBeUpdated =
                careerToUpdate.toBuilder().updateDate(LocalDateTime.now()).build();

        final Career careerUpdated = careerRepository.save(careerToBeUpdated);

        logger.debug("End update: careerUpdated = {}", careerUpdated);
        return careerUpdated;
    }

    @Override
    public void deleteById(@NotNull Long id) {
        logger.debug("Begin deleteById: id = {}", id);

        findById(id);
        careerRepository.deleteById(id);

        logger.debug("End deleteById");
    }

    private Specification<Career> buildCriteria(CareerQuerySearchCmd queryCriteria) {
        logger.debug("Begin buildCriteria: queryCriteria = {}", queryCriteria);

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(nonNull(queryCriteria.getName())){
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.like(
                                        criteriaBuilder.upper(root.get("name")), "%" + queryCriteria.getName().toUpperCase() + "%")));
            }

            if(nonNull(queryCriteria.getActive())){
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("active"),  queryCriteria.getActive())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }
}
