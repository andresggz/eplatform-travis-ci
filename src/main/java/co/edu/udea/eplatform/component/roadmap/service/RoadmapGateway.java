package co.edu.udea.eplatform.component.roadmap.service;

import co.edu.udea.eplatform.component.roadmap.model.CourseIdRoadmap;
import co.edu.udea.eplatform.component.roadmap.model.Roadmap;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapQuerySearchCmd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface RoadmapGateway {

    Roadmap save(@NotNull Roadmap roadmapToCreate);

    Roadmap findById(@NotNull Long id);

    Page<Roadmap> findByParameters(@NotNull RoadmapQuerySearchCmd queryCriteria, @NotNull Pageable pageable);

    Roadmap addCourse(@NotNull Long roadmapId, @NotNull CourseIdRoadmap courseIdRoadmapInDataBase);

    Roadmap update(@NotNull Roadmap roadmapToUpdate);

    void deleteById(@NotNull Long id);
}
