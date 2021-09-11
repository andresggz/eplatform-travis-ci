package co.edu.udea.eplatform.component.career.service;

import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;

import javax.validation.constraints.NotNull;

public interface CareerEventPublisher {

    void publishRoadmapAddedToCareer(@NotNull RoadmapIdCareer roadmapIdCareerAdded);
}
