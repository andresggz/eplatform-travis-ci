package co.edu.udea.eplatform.component.roadmap.service;

import co.edu.udea.eplatform.component.roadmap.model.Roadmap;
import co.edu.udea.eplatform.component.roadmap.service.model.CourseAddCmd;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapAddedToCareerCmd;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapQuerySearchCmd;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapSaveCmd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RoadmapService {

    Roadmap create(@NotNull RoadmapSaveCmd roadmapToCreateCmd);

    Roadmap findById(@NotNull Long id);

    Page<Roadmap> findByParameters(@NotNull RoadmapQuerySearchCmd queryCriteria, @NotNull Pageable pageable);

    Roadmap addCourse(@NotNull Long roadmapId, @NotNull CourseAddCmd courseToAddCmd);

    Roadmap update(@NotNull RoadmapAddedToCareerCmd roadmapAddedToCareerCmd);

    void deleteById(@NotNull Long id);

    List<Roadmap> createFromSheets(@NotNull MultipartFile roadmapsToCreateFromSheets);
}
