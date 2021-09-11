package co.edu.udea.eplatform.component.career.service;

import co.edu.udea.eplatform.component.career.model.Career;
import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;
import co.edu.udea.eplatform.component.career.service.model.CareerQuerySearchCmd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface CareerGateway {

    Career save(@NotNull Career careerToCreate);

    Career findById(@NotNull Long id);

    Page<Career> findByParameters(@NotNull CareerQuerySearchCmd queryCriteria, @NotNull Pageable pageable);

    Career addRoadmap(@NotNull Long careerId, @NotNull RoadmapIdCareer roadmapIdInDataBase);

    Career update(@NotNull Career careerToUpdate);

    void deleteById(@NotNull Long id);
}
