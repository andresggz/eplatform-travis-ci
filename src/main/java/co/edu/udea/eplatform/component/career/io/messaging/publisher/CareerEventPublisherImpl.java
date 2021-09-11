package co.edu.udea.eplatform.component.career.io.messaging.publisher;

import co.edu.udea.eplatform.component.career.io.messaging.publisher.model.RoadmapAddedToCareerEvent;
import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;
import co.edu.udea.eplatform.component.career.service.CareerEventPublisher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class CareerEventPublisherImpl implements CareerEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApplicationEventPublisher publisher;

    @Override
    public void publishRoadmapAddedToCareer(@NotNull RoadmapIdCareer roadmapIdCareerAdded) {
        logger.debug("Begin publishRoadmapAddedToCareer: roadmapIdCareerAdded = {}", roadmapIdCareerAdded);

        RoadmapAddedToCareerEvent roadmapAdded = RoadmapAddedToCareerEvent.builder()
                .id(roadmapIdCareerAdded.getId())
                .build();

        publisher.publishEvent(roadmapAdded);
        logger.debug("End publishRoadmapAddedToCareer");
    }
}
