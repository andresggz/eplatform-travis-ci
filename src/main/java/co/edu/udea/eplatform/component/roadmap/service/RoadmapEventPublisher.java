package co.edu.udea.eplatform.component.roadmap.service;

import co.edu.udea.eplatform.component.roadmap.model.Roadmap;

import javax.validation.constraints.NotNull;

public interface RoadmapEventPublisher {

    void publishRoadmapCreated(@NotNull Roadmap roadmapCreated);
}
