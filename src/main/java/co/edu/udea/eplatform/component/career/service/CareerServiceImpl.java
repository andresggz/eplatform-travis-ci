package co.edu.udea.eplatform.component.career.service;

import co.edu.udea.eplatform.component.career.model.Career;
import co.edu.udea.eplatform.component.career.model.RoadmapIdCareer;
import co.edu.udea.eplatform.component.career.service.model.CareerQuerySearchCmd;
import co.edu.udea.eplatform.component.career.service.model.CareerSaveCmd;
import co.edu.udea.eplatform.component.career.service.model.RoadmapAddCmd;
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
public class CareerServiceImpl implements CareerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CareerGateway careerGateway;

    private final RoadmapIdCareerService roadmapIdCareerService;

    private final CareerEventPublisher careerEventPublisher;


    @Override
    public Career create(@NotNull CareerSaveCmd careerToCreateCmd) {
        logger.debug("Begin create: careerToCreateCmd");

        Career careerToCreate = CareerSaveCmd.toModel(careerToCreateCmd);

        activateOrNot(careerToCreate);

        Career careerCreated = careerGateway.save(careerToCreate);

        logger.debug("End create: careerCreated = {}", careerCreated);
        return careerCreated;
    }

    @Override
    @Transactional(readOnly = true)
    public Career findById(@NotNull Long id) {
        logger.debug("Begin findById = {}", id);

        Career careerFound = careerGateway.findById(id);

        logger.debug("End findById: careerFound = {}", careerFound);
        return careerFound;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Career> findByParameters(@NotNull CareerQuerySearchCmd queryCriteria, @NotNull Pageable pageable) {
        logger.debug("Begin findByParameters: queryCriteria = {}, pageable = {}", queryCriteria, pageable);

        Page<Career> careersFound = careerGateway.findByParameters(queryCriteria, pageable);

        logger.debug("End findByParameters: careersFound = {}", careersFound);
        return careersFound;
    }

    @Override
    public Career addRoadmap(@NotNull Long careerId, @NotNull RoadmapAddCmd roadmapToAddCmd) {
        logger.debug("Begin addRoadmap: careerId = {}, roadmapToAddCmd = {}", careerId, roadmapToAddCmd);

        final Long roadmapIdToAdd = roadmapToAddCmd.getRoadmapId();

        RoadmapIdCareer roadmapIdInDataBase = roadmapIdCareerService.findById(roadmapIdToAdd);

        Career careerUpdated = careerGateway.addRoadmap(careerId, roadmapIdInDataBase);

        careerEventPublisher.publishRoadmapAddedToCareer(roadmapIdInDataBase);

        logger.debug("End addRoadmap: careerUpdated = {}", careerUpdated);
        return careerUpdated;
    }

    @Override
    public List<Career> createFromSheets(@NotNull MultipartFile careersToCreateFromSheets) {
        logger.debug("Begin createFromSheets");
        try {
            List<Career> careersToCreate =
                    extractCareersFromSheets(new BufferedInputStream(careersToCreateFromSheets.getInputStream()));

            List<Career> careersCreated = careersToCreate.stream()
                    .map(careerGateway::save)
                    .collect(Collectors.toList());

            logger.debug("End createFromSheets");

            return careersCreated;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Career update(@NotNull Long id, @NotNull CareerSaveCmd careerToUpdateCmd) {
        logger.debug("Begin update: id = {}, careerToUpdateCmd = {}", id, careerToUpdateCmd);

        Career careerInDataBase = findById(id);

        Career careerToUpdate = careerInDataBase.toBuilder().name(careerToUpdateCmd.getName()).description(careerToUpdateCmd.getDescription())
                .build();

        Career careerUpdated = careerGateway.update(careerToUpdate);

        logger.debug("End update: careerUpdated = {}", careerUpdated);
        return careerUpdated;
    }

    @Override
    public void deleteById(@NotNull Long id) {
        logger.debug("Begin deleteById id = {}", id);

        careerGateway.deleteById(id);

        logger.debug("End deleteById");
    }

    private void activateOrNot(@NotNull Career careerToCreate) {
        careerToCreate.setActive(true);
    }

    private List<Career> extractCareersFromSheets(@NotNull InputStream inputStreamCareers) {
        logger.debug("Begin extractCareersFromSheets");

        List<Career> careersToCreate = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(inputStreamCareers);
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Sheet> sheets = workbook.sheetIterator();

            while (sheets.hasNext()) {
                Sheet sh = sheets.next();
                Iterator<Row> iteratorRow = sh.iterator();
                iteratorRow.next();
                while (iteratorRow.hasNext()) {
                    Row row = iteratorRow.next();

                    careersToCreate.add(
                            Career.builder()
                                    .name(dataFormatter.formatCellValue(row.getCell(0)))
                                    .description(dataFormatter.formatCellValue(row.getCell(1)))
                                    .build());
                }
            }

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.debug("End extractCareersFromSheets");
        return careersToCreate;
    }

}
