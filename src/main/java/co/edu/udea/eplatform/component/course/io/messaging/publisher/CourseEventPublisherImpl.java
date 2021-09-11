package co.edu.udea.eplatform.component.course.io.messaging.publisher;

import co.edu.udea.eplatform.component.course.io.messaging.publisher.model.CourseCreatedEvent;
import co.edu.udea.eplatform.component.course.model.Course;
import co.edu.udea.eplatform.component.course.service.CourseEventPublisher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class CourseEventPublisherImpl implements CourseEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApplicationEventPublisher publisher;


    @Override
    public void publishCourseCreated(@NotNull Course courseCreated) {
        logger.debug("Begin publishCourseCreated: courseCreated = {}", courseCreated);

        CourseCreatedEvent courseCreatedEventToPublish = CourseCreatedEvent
                .builder().id(courseCreated.getId()).name(courseCreated.getName())
                .description(courseCreated.getDescription())
                .active(courseCreated.getActive())
                .iconId(courseCreated.getIconId())
                .level(courseCreated.getLevel().name())
                .build();

        publisher.publishEvent(courseCreatedEventToPublish);

        logger.debug("End publishCourseCreated");
    }
}
