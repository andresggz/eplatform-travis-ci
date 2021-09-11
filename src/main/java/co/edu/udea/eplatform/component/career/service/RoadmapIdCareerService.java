package co.edu.udea.eplatform.component.career.service;

import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;
import co.edu.udea.eplatform.component.career.service.model.RoadmapIdSaveCmd;

import javax.validation.constraints.NotNull;

public interface RoadmapIdCareerService {

    RoadmapIdCareer registerRoadmapId(@NotNull RoadmapIdSaveCmd roadmapIdToRegisterCmd);

    RoadmapIdCareer findById(@NotNull Long id);
}
