package com.syzton.sunread.service.organization;

import java.util.HashMap;
import java.util.Map;

import com.syzton.sunread.dto.region.SchoolDistrictDTO;
import com.syzton.sunread.model.organization.EduGroup;
import com.syzton.sunread.model.organization.School;
import com.syzton.sunread.repository.organization.EduGroupRepository;
import com.syzton.sunread.repository.organization.SchoolRepository;
import com.syzton.sunread.util.ExcelUtil;

import javassist.NotFoundException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Service
public class SchoolRepositoryService implements SchoolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolRepositoryService.class);
    private SchoolRepository repository;
    private EduGroupRepository eduRepository;

    @Autowired
    public SchoolRepositoryService(SchoolRepository repository,EduGroupRepository eduRepository) {
        this.repository = repository;
        this.eduRepository = eduRepository;
    }

    @Override
    public School add(SchoolDistrictDTO add, Long eduId) {

        LOGGER.debug("Adding a new school entry with information: {}", add);
        EduGroup eduGroup =  eduRepository.findOne(eduId);
        School school = School.getBuilder(add.getName(),eduGroup)
        		.description(add.getDescription()).build();
        return repository.save(school);

    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public School deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a school entry with id: {}", id);

        School deleted = findById(id);
        LOGGER.debug("Deleting school entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public School update(SchoolDistrictDTO updated)throws  NotFoundException{
        LOGGER.debug("Updating contact with information: {}", updated);

        School model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName());
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public School findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a school entry with id: {}", id);

        School found = repository.findOne(id);
        LOGGER.debug("Found school entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No school entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<School> findAll(Pageable pageable) throws NotFoundException {
        LOGGER.debug("Finding all school entries");
        return  repository.findAll(pageable);
    }
    
    @Transactional
    @Override
    public Map<Integer,String> batchSaveOrUpdateSchoolFromExcel(Sheet sheet){
    	Map<Integer,String> failMap = new HashMap<Integer,String>();
		
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
			Row row = sheet.getRow(i);  
			String name = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			String eduName = ExcelUtil.getStringFromExcelCell(row.getCell(1));
			if("".equals(name)){
				break;
			}
			if("".equals(eduName)){
				failMap.put(row.getRowNum()+1, "学校和教育集团不能为空！");
				continue;
			}
			EduGroup group = eduRepository.findByName(eduName);
			if(group == null){
				failMap.put(row.getRowNum()+1, "无效的教育集团，数据库中未能发现该教育集团记录！");
				continue;
			}
			School school = repository.findByNameAndEduGroup(name, group);
			if(school == null){
				school = new School();
				school.setName(name);
				school.setEduGroup(group);
			}
			school.setDescription(ExcelUtil.getStringFromExcelCell(row.getCell(2)));
			repository.save(school);
		}
		return failMap;
    }
}
