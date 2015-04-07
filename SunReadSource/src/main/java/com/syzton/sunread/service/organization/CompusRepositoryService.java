package com.syzton.sunread.service.organization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.organization.CompusDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.organization.Compus;
import com.syzton.sunread.model.organization.School;
import com.syzton.sunread.model.region.Region;
import com.syzton.sunread.repository.organization.CompusRepository;
import com.syzton.sunread.repository.organization.SchoolRepository;
import com.syzton.sunread.repository.region.RegionRepository;

/**
 * @author Morgan-Leon
 * @Date 2015年4月7日
 * 
 */
@Service
public class CompusRepositoryService implements CompusService{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CompusRepositoryService.class);
    private CompusRepository repository;
    private RegionRepository regionRepository;
    private SchoolRepository schoolRepository;
	private Region region;
	private School school;
 
    @Autowired
    public CompusRepositoryService(CompusRepository repository,RegionRepository regionRepository
    		,SchoolRepository schoolRepository) {
        this.repository = repository;
        this.regionRepository = regionRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    @Transactional(rollbackFor = {NotFoundException.class})
    public Compus add(CompusDTO add, Long regionId, Long schoolId) {

        LOGGER.debug("Adding a new compus entry with information: {}", add);
        
        region = regionRepository.findOne(regionId);
        school = schoolRepository.findOne(schoolId);
        if (region == null) {
			throw new NotFoundException("no region found with name:"+ region.getProvince()
					+region.getCity()+region.getDistrict());
		}
        if (school == null) {
			throw new NotFoundException("no school found with name :"+school.getName());
		}             
        Compus model = Compus.getBuilder(add.getName(),add.getHeadmaster(),region,school)
        		.description(add.getDescription()).build();  
        
        return repository.save(model);

    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Compus deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a compus entry with id: {}", id);
        
        Compus deleted = findById(id);
        LOGGER.debug("Deleting compus entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Compus update(CompusDTO updated)throws  NotFoundException{
        LOGGER.debug("Updating contact with information: {}", updated);

        Compus model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName(),updated.getHeadmaster());
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Compus findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a compus entry with id: {}", id);

        Compus found = repository.findOne(id);
        LOGGER.debug("Found compus entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No compus entry found with id: " + id);
        }

        return found;
    }



    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<Compus> findAll(Pageable pageable) throws NotFoundException {
        LOGGER.debug("Finding all compus entries");
        return repository.findAll(pageable);
    }

}
