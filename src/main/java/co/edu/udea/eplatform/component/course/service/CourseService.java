package co.edu.udea.eplatform.component.course.service;

import co.edu.udea.eplatform.component.course.model.Course;
import co.edu.udea.eplatform.component.course.service.model.CourseQuerySearchCmd;
import co.edu.udea.eplatform.component.course.service.model.CourseSaveCmd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface CourseService {

    Course create(@NotNull CourseSaveCmd courseToCreateCmd);

    Course findById(@NotNull Long id);

    Page<Course> findByParameters(@NotNull CourseQuerySearchCmd queryCriteria, @NotNull Pageable pageable);
}
