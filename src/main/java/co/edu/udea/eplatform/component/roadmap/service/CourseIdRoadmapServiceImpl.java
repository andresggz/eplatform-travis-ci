package co.edu.udea.eplatform.component.roadmap.service;

import co.edu.udea.eplatform.component.roadmap.model.CourseIdRoadmap;
import co.edu.udea.eplatform.component.roadmap.service.model.CourseIdSaveCmd;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseIdRoadmapServiceImpl implements CourseIdRoadmapService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CourseIdRoadmapGateway courseIdRoadmapGateway;

    @Override
    public CourseIdRoadmap registerCourseId(@NotNull CourseIdSaveCmd courseIdToRegisterCmd) {
        logger.debug("Begin registerCourseId: courseIdRegisteredCmd = {}", courseIdToRegisterCmd);

        CourseIdRoadmap courseIdRoadmapToRegister = CourseIdSaveCmd.toModel(courseIdToRegisterCmd);

        CourseIdRoadmap courseIdRoadmapRegistered = courseIdRoadmapGateway.register(courseIdRoadmapToRegister);

        logger.debug("End registerCourseId = {}", courseIdRoadmapRegistered);
        return courseIdRoadmapRegistered;
    }

    @Override
    @Transactional(readOnly = true)
    public CourseIdRoadmap findById(@NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        CourseIdRoadmap courseIdRoadmapFound = courseIdRoadmapGateway.findById(id);

        logger.debug("End findById: courseIdRoadmapFound = {}", courseIdRoadmapFound);
        return courseIdRoadmapFound;
    }
}
