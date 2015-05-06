package com.syzton.sunread.service.organization;

import java.util.HashMap;
import java.util.Map;

import com.syzton.sunread.dto.organization.EduGroupDTO;
import com.syzton.sunread.model.organization.EduGroup;
import com.syzton.sunread.repository.organization.EduGroupRepository;
import com.syzton.sunread.util.ExcelUtil;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;

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
public class EduGroupRepositoryService implements EduGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EduGroupRepositoryService.class);
    private EduGroupRepository repository;

    @Autowired
    public EduGroupRepositoryService(EduGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public EduGroup add(EduGroupDTO add) {

        LOGGER.debug("Adding a new edu group entry with information: {}", add);
        
        EduGroup exits = repository.findOne(add.getId());        
        if(exits != null){
            throw new DuplicateException("EduGroup with name: "+add.getName()+" is already exits..");
        }
        
        EduGroup model = EduGroup.getBuilder(add.getName())
        		.description(add.getDescription()).build();   
        return repository.save(model);

    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public EduGroup deleteById(Long id){
        LOGGER.debug("Deleting a edu group entry with id: {}", id);

        EduGroup deleted = findById(id);
        if(deleted == null)
            throw new NotFoundException("No Edu Group found with id: " + id);
        
        LOGGER.debug("Deleting edu group entry: {}", deleted);
        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public EduGroup update(EduGroupDTO updated){
        LOGGER.debug("Updating contact with information: {}", updated);

        EduGroup model = findById(updated.getId());
        if(model == null)
            throw new NotFoundException("No Edu Group found with id: " + updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName());
        return model;
    }

    @Override
    public EduGroup findById(Long id) {
        LOGGER.debug("Finding a edu group entry with id: {}", id);

        EduGroup found = repository.findOne(id);
        LOGGER.debug("Found edu group entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No edu group entry found with id: " + id);
        }

        return found;
    }

    @Override
    public Page<EduGroup> findAll(Pageable pageable){
        LOGGER.debug("Finding all edu Group entries");
        Page<EduGroup> eduPages = repository.findAll(pageable);
        if (eduPages == null) {
            throw new NotFoundException("No edu group entry found");
        }
        return eduPages;
    }
    
    @Override
    public Map<Integer,String> batchSaveOrUpdateEduGroupFromExcel(Sheet sheet) {
		Map<Integer,String> failMap = new HashMap<Integer,String>();
		
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
			Row row = sheet.getRow(i);  
			String name = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			if("".equals(name)){
				break;
			}
			EduGroup group = repository.findByName(name);
			if(group == null){
				group = new EduGroup();
			}
			group.setName(name);
			group.setDescription(ExcelUtil.getStringFromExcelCell(row.getCell(1)));
			repository.save(group);
		}
		return failMap;
	}
}
