package co.edu.udea.eplatform.component.course.service;

import co.edu.udea.eplatform.component.course.model.Course;

import javax.validation.constraints.NotNull;

public interface CourseEventPublisher {

    void publishCourseCreated(@NotNull Course courseCreated);
}
