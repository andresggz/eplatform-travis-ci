package co.edu.udea.eplatform.component.roadmap.io.gateway;

import co.edu.udea.eplatform.component.roadmap.io.repository.CourseIdRoadmapRepository;
import co.edu.udea.eplatform.component.roadmap.model.CourseIdRoadmap;
import co.edu.udea.eplatform.component.roadmap.service.CourseIdRoadmapGateway;
import co.edu.udea.eplatform.component.shared.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
@RequiredArgsConstructor
public class CourseIdRoadmapGatewayImpl implements CourseIdRoadmapGateway {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String RESOURCE_NOT_FOUND = "CourseIdRoadmap not found.";

    private final CourseIdRoadmapRepository courseIdRoadmapRepository;

    @Override
    public CourseIdRoadmap register(@NotNull CourseIdRoadmap courseIdRoadmapToRegister) {
        logger.debug("Begin register: courseIdRoadmapToRegister = {}", courseIdRoadmapToRegister);

        CourseIdRoadmap courseIdRoadmapRegistered = courseIdRoadmapRepository.save(courseIdRoadmapToRegister);

        logger.debug("End register: courseIdRoadmapRegistered = {}", courseIdRoadmapRegistered);
        return courseIdRoadmapRegistered;
    }

    @Override
    public CourseIdRoadmap findById(@NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        CourseIdRoadmap courseIdRoadmapFound = courseIdRoadmapRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        logger.debug("End findById: courseIdRoadmapFound = {}", courseIdRoadmapFound);
        return courseIdRoadmapFound;
    }
}
