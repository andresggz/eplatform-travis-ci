package co.edu.udea.eplatform.component.career.io.repository;

import co.edu.udea.eplatform.component.career.model.Career;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends PagingAndSortingRepository<Career, Long>, JpaSpecificationExecutor<Career>{
}
