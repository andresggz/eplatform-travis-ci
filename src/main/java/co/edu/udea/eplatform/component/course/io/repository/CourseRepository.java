package co.edu.udea.eplatform.component.course.io.repository;

import co.edu.udea.eplatform.component.course.model.Course;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, Long>, JpaSpecificationExecutor<Course>{
}
