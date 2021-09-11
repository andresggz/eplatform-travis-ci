package co.edu.udea.eplatform.component.course.io.gateway;

import co.edu.udea.eplatform.component.course.io.repository.CourseRepository;
import co.edu.udea.eplatform.component.course.model.Course;
import co.edu.udea.eplatform.component.course.service.CourseGateway;
import co.edu.udea.eplatform.component.course.service.model.CourseQuerySearchCmd;
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
public class CourseGatewayImpl implements CourseGateway {

    private static final String RESOURCE_NOT_FOUND = "Course not found";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CourseRepository courseRepository;

    @Override
    public Course save(@NotNull Course courseToCreate) {
        logger.debug("Begin save: courseToCreate = {}", courseToCreate);

        final Course courseToBeCreated =
                courseToCreate.toBuilder().createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        final Course courseCreated = courseRepository.save(courseToBeCreated);

        logger.debug("End save: courseCreated = {}", courseCreated);
        return courseCreated;
    }

    @Override
    public Course findById(@NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        Course courseFound = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        logger.debug("End findById: courseFound = {}", courseFound);
        return courseFound;
    }

    @Override
    public Page<Course> findByParameters(@NotNull CourseQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {
        logger.debug("Begin findByParameters: queryCriteria = {}. pageable = {}", queryCriteria, pageable);

        Specification<Course> specification = buildCriteria(queryCriteria);

        Page<Course> coursesFound = courseRepository.findAll(specification, pageable);

        logger.debug("End findByParameters: coursesFound = {}", coursesFound);
        return coursesFound;
    }

    private Specification<Course> buildCriteria(CourseQuerySearchCmd queryCriteria) {
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

            if(nonNull(queryCriteria.getLevel())){
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("level"), queryCriteria.getLevel())));
            }

            if(nonNull(queryCriteria.getReleaseDateGreaterThan())){
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.greaterThan(root.get("releaseDate"), queryCriteria.getReleaseDateGreaterThan())));
            }

            if(nonNull(queryCriteria.getReleaseDateLessThan())){
                predicates
                        .add(criteriaBuilder.and(
                                criteriaBuilder.lessThan(root.get("releaseDate"), queryCriteria.getReleaseDateLessThan())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }
}
