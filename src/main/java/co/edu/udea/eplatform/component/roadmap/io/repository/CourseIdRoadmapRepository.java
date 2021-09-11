package co.edu.udea.eplatform.component.roadmap.io.repository;

import co.edu.udea.eplatform.component.roadmap.model.CourseIdRoadmap;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseIdRoadmapRepository extends PagingAndSortingRepository<CourseIdRoadmap, Long> {
}
