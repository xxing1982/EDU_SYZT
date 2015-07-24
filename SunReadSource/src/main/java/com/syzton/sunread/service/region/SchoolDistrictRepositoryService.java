package com.syzton.sunread.service.region;

import java.util.HashMap;
import java.util.Map;

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

import com.syzton.sunread.dto.region.SchoolDistrictDTO;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.EduGroup;
import com.syzton.sunread.model.region.Region;
import com.syzton.sunread.model.region.RegionType;
import com.syzton.sunread.model.region.SchoolDistrict;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.region.RegionRepository;
import com.syzton.sunread.repository.region.SchoolDistrictRepository;
import com.syzton.sunread.repository.user.UserRepository;
import com.syzton.sunread.util.ExcelUtil;
import com.syzton.sunread.util.UserUtil;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Service
public class SchoolDistrictRepositoryService implements SchoolDistrictService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolDistrictRepositoryService.class);
    private SchoolDistrictRepository repository;
    private RegionRepository regionRepository;
    private UserRepository userRepo;
    
    @Autowired
    public SchoolDistrictRepositoryService(SchoolDistrictRepository repository,RegionRepository regionRepository,UserRepository userRepo) {
        this.repository = repository;
        this.regionRepository = regionRepository;
        this.userRepo = userRepo;
    }

    @Override
    public SchoolDistrict add(SchoolDistrictDTO add, Long regionId) {

        LOGGER.debug("Adding a new school entry with information: {}", add);
//        EduGroup eduGroup =  eduRepository.findOne(eduId);
        Region region = regionRepository.findOne(regionId);
        SchoolDistrict schoolDistrict = SchoolDistrict.getBuilder(add.getName(),region)
        		.description(add.getDescription()).build();
        return repository.save(schoolDistrict);

    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public SchoolDistrict deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a school entry with id: {}", id);

        SchoolDistrict deleted = findById(id);
        LOGGER.debug("Deleting school entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public SchoolDistrict update(SchoolDistrictDTO updated)throws  NotFoundException{
        LOGGER.debug("Updating contact with information: {}", updated);

        SchoolDistrict model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName());
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public SchoolDistrict findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a school entry with id: {}", id);

        SchoolDistrict found = repository.findOne(id);
        LOGGER.debug("Found school entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No school entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<SchoolDistrict> findAll(Pageable pageable) throws NotFoundException {
        LOGGER.debug("Finding all school entries");
        return  repository.findAll(pageable);
    }
    
    @Transactional
    @Override
    public Map<Integer,String> batchSaveOrUpdateSchoolFromExcel(Sheet sheet){
    	Map<Integer,String> failMap = new HashMap<Integer,String>();
		String userId = UserUtil.getUser();
		
		User user = userRepo.findByUserId(userId);
		if(!UserUtil.isPlatformAdmin(user)){
			failMap.put(0, "非法用户");
			return failMap;
		}
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
			Row row = sheet.getRow(i); 
			String schoolDistrictName = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			if("".equals(schoolDistrictName)){
				failMap.put(i+1, "校区名不能为空");
				continue;
			}
			SchoolDistrict schoolDistrict = repository.findByName(schoolDistrictName);
			if(schoolDistrict == null){
				schoolDistrict = new SchoolDistrict();
				schoolDistrict.setName(schoolDistrictName);
			}
			
			String area = ExcelUtil.getStringFromExcelCell(row.getCell(1));
			RegionType type = RegionType.province;
			if("省".equals(area)){
				type = RegionType.province;
			}else if("市".equals(area)){
				type = RegionType.city;
			}else if("县".equals(area)||"区".equals(area)){
				type = RegionType.district;
			}
			
			String areaName = ExcelUtil.getStringFromExcelCell(row.getCell(2));
			Region region = regionRepository.findByNameAndRegionType(areaName, type);
			if(region == null){
				if("".equals(schoolDistrictName)){
					failMap.put(i+1, "region不存在:"+areaName);
					continue;
				}
			}
			schoolDistrict.setRegion(region);
			String desc = ExcelUtil.getStringFromExcelCell(row.getCell(3));
			schoolDistrict.setDescription(desc);
			repository.save(schoolDistrict);
		}
		return failMap;
    }
}
