package co.edu.udea.eplatform.component.career.service;

import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;
import co.edu.udea.eplatform.component.career.service.model.RoadmapIdSaveCmd;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
@RequiredArgsConstructor
public class RoadmapIdCareerServiceImpl implements RoadmapIdCareerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RoadmapIdCareerGateway roadmapIdCareerGateway;

    @Override
    public RoadmapIdCareer registerRoadmapId(@NotNull RoadmapIdSaveCmd roadmapIdToRegisterCmd) {
        logger.debug("Begin registerRoadmapId: roadmapIdToRegisterCmd = {}", roadmapIdToRegisterCmd);

        RoadmapIdCareer roadmapIdToRegister = RoadmapIdSaveCmd.toModel(roadmapIdToRegisterCmd);

        RoadmapIdCareer roadmapIdRegistered = roadmapIdCareerGateway.register(roadmapIdToRegister);

        logger.debug("End registerRoadmapId: roadmapIdRegistered = {}", roadmapIdRegistered);
        return roadmapIdRegistered;
    }

    @Override
    @Transactional(readOnly = true)
    public RoadmapIdCareer findById(@NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        RoadmapIdCareer roadmapIdFound = roadmapIdCareerGateway.findById(id);

        logger.debug("End findById: roadmapIdFound = {}", roadmapIdFound);
        return roadmapIdFound;
    }
}
