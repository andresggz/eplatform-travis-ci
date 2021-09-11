package co.edu.udea.eplatform.component.roadmap.service;

import co.edu.udea.eplatform.component.career.model.Career;
import co.edu.udea.eplatform.component.roadmap.model.CourseIdRoadmap;
import co.edu.udea.eplatform.component.roadmap.model.Roadmap;
import co.edu.udea.eplatform.component.roadmap.service.model.CourseAddCmd;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapAddedToCareerCmd;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapQuerySearchCmd;
import co.edu.udea.eplatform.component.roadmap.service.model.RoadmapSaveCmd;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class RoadmapServiceImpl implements RoadmapService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RoadmapEventPublisher roadmapEventPublisher;

    private final RoadmapGateway roadmapGateway;

    private final CourseIdRoadmapService courseIdService;

    @Override
    public Roadmap create(@NotNull RoadmapSaveCmd roadmapToCreateCmd) {
        logger.debug("Begin create: roadmapToCreateCmd");

        Roadmap roadmapToCreate = RoadmapSaveCmd.toModel(roadmapToCreateCmd);

        activateOrNot(roadmapToCreate);

        Roadmap roadmapCreated = roadmapGateway.save(roadmapToCreate);

        roadmapEventPublisher.publishRoadmapCreated(roadmapCreated);

        logger.debug("End create: roadmapCreated = {}", roadmapCreated);
        return roadmapCreated;
    }

    @Override
    @Transactional(readOnly = true)
    public Roadmap findById(@NotNull Long id) {
        logger.debug("Begin findById = {}", id);

        Roadmap roadmapFound = roadmapGateway.findById(id);

        logger.debug("End findById: roadmapFound = {}", roadmapFound);
        return roadmapFound;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Roadmap> findByParameters(@NotNull RoadmapQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        Page<Roadmap> roadmapsFound = roadmapGateway.findByParameters(queryCriteria, pageable);

        logger.debug("End findByParameters: roadmapsFound = {}", roadmapsFound);
        return roadmapsFound;
    }

    @Override
    public Roadmap addCourse(@NotNull Long roadmapId, @NotNull CourseAddCmd courseToAddCmd) {
        logger.debug("Begin addCourse: roadmapId = {}, courseToAddCmd = {}", roadmapId, courseToAddCmd);

        final Long courseIdToAdd = courseToAddCmd.getCourseId();

        CourseIdRoadmap courseIdRoadmapInDataBase = courseIdService.findById(courseIdToAdd);

        Roadmap roadmapUpdated = roadmapGateway.addCourse(roadmapId, courseIdRoadmapInDataBase);

        logger.debug("End addCourse: roadmapUpdated = {}", roadmapUpdated);
        return roadmapUpdated;
    }

    @Override
    public Roadmap update(@NotNull RoadmapAddedToCareerCmd roadmapAddedToCareerCmd) {
        logger.debug("Begin update: roadmapAddedToCareerCmd = {}", roadmapAddedToCareerCmd);

        Long roadmapIdAddedToCareer = roadmapAddedToCareerCmd.getId();

        Roadmap roadmapInDataBase = findById(roadmapIdAddedToCareer);

        Roadmap roadmapToUpdate = roadmapInDataBase.toBuilder()
                .linkedToRoute(true)
                .build();

        Roadmap roadmapUpdated = roadmapGateway.update(roadmapToUpdate);

        logger.debug("End update: roadmapUpdated = {}", roadmapUpdated);

        return roadmapUpdated;
    }

    @Override
    public void deleteById(@NotNull Long id) {
        logger.debug("Begin delete: id = {}", id);

        roadmapGateway.deleteById(id);

        logger.debug("End delete");
    }

    @Override
    public List<Roadmap> createFromSheets(@NotNull MultipartFile roadmapsToCreateFromSheets) {
        logger.debug("Begin createFromSheets");
        try {
            List<Roadmap> roadmapsToCreate =
                    extractRoadmapsFromSheets(new BufferedInputStream(roadmapsToCreateFromSheets.getInputStream()));

            List<Roadmap> roadmapsCreated = roadmapsToCreate.stream()
                    .map(this::activateOrNot)
                    .map(roadmapGateway::save)
                    .collect(Collectors.toList());

            roadmapsCreated.stream()
                    .forEach(roadmapEventPublisher::publishRoadmapCreated);

            logger.debug("End createFromSheets");

            return roadmapsCreated;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Roadmap> extractRoadmapsFromSheets(InputStream inputStreamRoadmaps) {
        logger.debug("Begin extractRoadmapsFromSheets");

        List<Roadmap> roadmapsToCreate = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(inputStreamRoadmaps);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Sheet> sheets = workbook.sheetIterator();

            while (sheets.hasNext()) {
                Sheet sh = sheets.next();
                Iterator<Row> iteratorRow = sh.iterator();
                iteratorRow.next();
                while (iteratorRow.hasNext()) {
                    Row row = iteratorRow.next();

                    roadmapsToCreate.add(
                            Roadmap.builder()
                                    .name(dataFormatter.formatCellValue(row.getCell(0)))
                                    .description(dataFormatter.formatCellValue(row.getCell(1)))
                                    .detail(dataFormatter.formatCellValue(row.getCell(2)))
                                    .build());
                }
            }

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.debug("End extractRoadmapsFromSheets");
        return roadmapsToCreate;

    }

    private Roadmap activateOrNot(@NotNull Roadmap roadmapToCreate) {
        roadmapToCreate.setActive(true);
        return roadmapToCreate;
    }

}
