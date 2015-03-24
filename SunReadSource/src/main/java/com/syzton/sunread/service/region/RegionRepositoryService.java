package com.syzton.sunread.service.region;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.assembler.region.RegionAssembler;
import com.syzton.sunread.dto.region.RegionDTO;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.region.Region;
import com.syzton.sunread.repository.region.RegionRepository;

/**
 * @author Morgan-Leon
 * @Date 2015年3月24日
 * 
 */
@Service
public class RegionRepositoryService implements RegionService{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionRepositoryService.class);

    private RegionRepository regionRepo;
    
    @Autowired
    public RegionRepositoryService(RegionRepository repository) {
		// TODO Auto-generated constructor stub
    	this.regionRepo = repository;
	}
    
    @Transactional(rollbackFor = {DuplicateException.class})
	@Override
	public RegionDTO add(RegionDTO added) {
        LOGGER.debug("Adding a new Region with information: {}", added);
        
        Region exits = regionRepo.findOne(added.getId());        
        if(exits != null){
            throw new DuplicateException("Region with id: "+added.getId()+" is already exits..");
        }
        
        RegionAssembler assembler = new RegionAssembler();
        
        Region model = assembler.fromDTOtoModel(added);
        model = regionRepo.save(model);
        added.setId(model.getId());
        return added;
	}

    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Region deleteById(long id) {
        LOGGER.debug("Deleting a Region with id: {}", id);

        Region deleted = findOne(id);
        if(deleted == null)
            throw new NotFoundException("No Region found with id: " + id);
        
        LOGGER.debug("Deleting Region entry: {}", deleted);
        regionRepo.delete(deleted);
        return deleted;
	}

    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public RegionDTO update(RegionDTO updated) {
        LOGGER.debug("Updating contact with information: {}", updated);

        Region model = findOne(updated.getId());
        if(model == null)
            throw new NotFoundException("No Region found with id: " + updated.getId());
        LOGGER.debug("Found a note entry: {}", model);
        
        RegionAssembler assembler = new RegionAssembler();     
        Region Region = assembler.fromDTOtoModel(updated);
        updated.setId(Region.getId());
        return updated;
	}

	@Override
	public Region findOne(Long id) {
        LOGGER.debug("Finding a Region with id: {}", id);

        Region found = regionRepo.findOne(id);
        LOGGER.debug("Found Region entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No Region found with id: " + id);
        }
        return found;
	}

	@Override
	public Page<Region> findAll(Pageable pageable) {
        LOGGER.debug("Finding all Region entries");
        Page<Region> eduPages = regionRepo.findAll(pageable);
        if (eduPages == null) {
            throw new NotFoundException("No Region found");
        }
        return eduPages;
    }
	


}
