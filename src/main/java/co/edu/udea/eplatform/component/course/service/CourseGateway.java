package co.edu.udea.eplatform.component.course.service;

import co.edu.udea.eplatform.component.course.model.Course;
import co.edu.udea.eplatform.component.course.service.model.CourseQuerySearchCmd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface CourseGateway {

    Course save(@NotNull Course courseToCreate);

    Course findById(@NotNull Long id);

    Page<Course> findByParameters(@NotNull CourseQuerySearchCmd queryCriteria, @NotNull Pageable pageable);
}
