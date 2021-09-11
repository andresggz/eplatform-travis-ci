package co.edu.udea.eplatform.component.course.service;

import co.edu.udea.eplatform.component.course.model.Course;
import co.edu.udea.eplatform.component.course.service.model.CourseQuerySearchCmd;
import co.edu.udea.eplatform.component.course.service.model.CourseSaveCmd;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CourseEventPublisher courseEventPublisher;

    private final CourseGateway courseGateway;

    @Override
    public Course create(@NotNull CourseSaveCmd courseToCreateCmd) {
        logger.debug("Begin create: courseToCreateCmd");

        Course courseToCreate = CourseSaveCmd.toModel(courseToCreateCmd);

        activateOrNot(courseToCreate, courseToCreateCmd);

        Course courseCreated = courseGateway.save(courseToCreate);

        courseEventPublisher.publishCourseCreated(courseCreated);

        logger.debug("End create: courseCreated = {}", courseCreated);
        return courseCreated;
    }

    @Override
    @Transactional(readOnly = true)
    public Course findById(@NotNull Long id) {
        logger.debug("Begin findById = {}", id);

        Course courseFound = courseGateway.findById(id);

        logger.debug("End findById: courseFound = {}", courseFound);
        return courseFound;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Course> findByParameters(@NotNull CourseQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        Page<Course> coursesFound = courseGateway.findByParameters(queryCriteria, pageable);

        logger.debug("End findByParameters: coursesFound = {}", coursesFound);
        return coursesFound;
    }

    private void activateOrNot(@NotNull Course courseToCreate,
                                     @NotNull CourseSaveCmd courseToCreateCmd) {
        courseToCreate.setActive(
                nonNull(courseToCreateCmd.getReleaseDate()));
    }

}
