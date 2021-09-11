package co.edu.udea.eplatform.component.career.io.gateway;

import co.edu.udea.eplatform.component.career.io.repository.RoadmapIdCareerRepository;
import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;
import co.edu.udea.eplatform.component.career.service.RoadmapIdCareerGateway;
import co.edu.udea.eplatform.component.shared.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
@RequiredArgsConstructor
public class RoadmapIdCareerGatewayImpl implements RoadmapIdCareerGateway {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String RESOURCE_NOT_FOUND = "RoadmapIdCareer not found.";

    private final RoadmapIdCareerRepository roadmapIdRepository;

    @Override
    public RoadmapIdCareer register(@NotNull RoadmapIdCareer roadmapIdToRegister) {
        logger.debug("Begin register: roadmapIdToRegister = {}", roadmapIdToRegister);

        final RoadmapIdCareer roadmapIdRegistered =  roadmapIdRepository.save(roadmapIdToRegister);

        logger.debug("End register: roadmapIdRegistered = {}", roadmapIdRegistered);
        return roadmapIdRegistered;
    }

    @Override
    public RoadmapIdCareer findById(@NotNull Long id) {
        logger.debug("Begin findById: id = {}", id);

        final RoadmapIdCareer roadmapIdFound = roadmapIdRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        logger.debug("End findById: roadmapIdFound = {}", roadmapIdFound);
        return roadmapIdFound;
    }
}
