package co.edu.udea.eplatform.component.roadmap.io.repository;

import co.edu.udea.eplatform.component.roadmap.model.Roadmap;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadmapRepository extends PagingAndSortingRepository<Roadmap, Long>, JpaSpecificationExecutor<Roadmap>{
}
