package com.syzton.sunread.service.organization;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.organization.CampusDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.EduGroup;
import com.syzton.sunread.model.region.Region;
import com.syzton.sunread.model.region.RegionType;
import com.syzton.sunread.model.region.SchoolDistrict;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.organization.EduGroupRepository;
import com.syzton.sunread.repository.region.RegionRepository;
import com.syzton.sunread.repository.region.SchoolDistrictRepository;
import com.syzton.sunread.repository.user.UserRepository;
import com.syzton.sunread.util.ExcelUtil;
import com.syzton.sunread.util.UserUtil;

/**
 * @author Morgan-Leon
 * @Date 2015年4月7日
 * 
 */
@Service
public class CampusRepositoryService implements CampusService{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CampusRepositoryService.class);
    private CampusRepository repository;
    private RegionRepository regionRepository;
    private SchoolDistrictRepository schoolDistrictRepository;
    private EduGroupRepository eduGroupRepository;
    private UserRepository userRepo;
	private EduGroup eduGroup;
	private SchoolDistrict schoolDistrict;
	private Region region;
	
 
    @Autowired
    public CampusRepositoryService(CampusRepository repository,RegionRepository regionRepository
    		,SchoolDistrictRepository schoolDistrictRepository,EduGroupRepository eduGroupRepository,UserRepository userRepo) {
        this.repository = repository;
        this.regionRepository = regionRepository;
        this.schoolDistrictRepository = schoolDistrictRepository;
        this.eduGroupRepository = eduGroupRepository;
        this.userRepo = userRepo;
    }

//    @Override
//    @Transactional(rollbackFor = {NotFoundException.class})
//    public Campus add(CampusDTO add, Long regionId, Long schoolId) {
//
//        LOGGER.debug("Adding a new campus entry with information: {}", add);
//        
//        region = regionRepository.findOne(regionId);
//        school = schoolDistrictRepository.findOne(schoolId);
//        if (region == null) {
//			throw new NotFoundException("no region found with name:"+ region.getProvince()
//					+region.getCity()+region.getDistrict());
//		}
//        if (school == null) {
//			throw new NotFoundException("no school found with name :"+school.getName());
//		}             
//        Campus model = Campus.getBuilder(add.getName(),add.getHeadmaster(),region,school)
//        		.description(add.getDescription()).wish(add.getWish()).build();
//        
//        return repository.save(model);
//
//    }
  @Override
  @Transactional(rollbackFor = {NotFoundException.class})
  public Campus add(CampusDTO add,Long regionId) {
	  
    LOGGER.debug("Adding a new campus entry with information: {}", add);
    
    region = regionRepository.findOne(regionId);
    if (region == null) {
		throw new NotFoundException("no region found with name:"+ region.getId());
	}
    
    if (add.getSchoolDistrictName() != null) {
        schoolDistrict = schoolDistrictRepository.findByName(add.getSchoolDistrictName());
        if(schoolDistrict == null)
    		 throw new NotFoundException("no schoolDistrict found with name :"+add.getSchoolDistrictName());
	}
  
    if(add.getEduGroupName() != null){
    	eduGroup = eduGroupRepository.findByName(add.getEduGroupName());
	  	if(eduGroup == null)
			 throw new NotFoundException("no edu Group found with name :"+add.getEduGroupName());
	  }
    Campus model = Campus.getBuilder(add.getName(),add.getHeadmaster(),add.getWish(),add.getNoteScore(),region,eduGroup,schoolDistrict)
    		.description(add.getDescription()).wish(add.getWish()).build();
    
    return repository.save(model);
  }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Campus deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a campus entry with id: {}", id);
        
        Campus deleted = findById(id);
        LOGGER.debug("Deleting campus entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Campus update(CampusDTO updated)throws  NotFoundException{
        LOGGER.debug("Updating contact with information: {}", updated);

        Campus model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName(),updated.getHeadmaster());
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Campus findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a campus entry with id: {}", id);

        Campus found = repository.findOne(id);
        LOGGER.debug("Found campus entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No campus entry found with id: " + id);
        }

        return found;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Campus findByCompusName(String campusName) throws NotFoundException {
        LOGGER.debug("Finding a campus entry with id: {}", campusName);

        Campus found = repository.findByName(campusName);
        LOGGER.debug("Found campus entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No campus entry found with id: " + campusName);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<Campus> findAll(Pageable pageable) throws NotFoundException {
        LOGGER.debug("Finding all campus entries");
        return repository.findAll(pageable);
    }
    
    @Override
	public Map<Integer,String> batchSaveOrUpdateCampusFromExcel(Sheet sheet) {
		Map<Integer,String> failMap = new HashMap<Integer,String>();
		String userId = UserUtil.getUser();
		
		User user = userRepo.findByUserId(userId);
		if(!UserUtil.isPlatformAdmin(user)){
			failMap.put(0, "非法用户");
			return failMap;
		}
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
			Row row = sheet.getRow(i); 
			String campusName = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			if("".equals(campusName)){
				break;
			}
			
			String eduGroupName = ExcelUtil.getStringFromExcelCell(row.getCell(1));
		
			EduGroup group = eduGroupRepository.findByName(eduGroupName);
			if(group == null){
				failMap.put(i+1, "查询不到该教育集团:"+eduGroupName);
				continue;
			}
			
			Campus campus = repository.findByName(campusName);
			if(campus == null){
				campus = new Campus();
				campus.setName(campusName);
			}
			campus.setEduGroup(group);
			campus.setHeadmaster(ExcelUtil.getStringFromExcelCell(row.getCell(2)));
			String province = ExcelUtil.getStringFromExcelCell(row.getCell(3));
			String city = ExcelUtil.getStringFromExcelCell(row.getCell(4));
			String district = ExcelUtil.getStringFromExcelCell(row.getCell(5));
			Region smallRegion = null;
			Region provinceRegion = regionRepository.findByNameAndRegionTypeAndParent(province, RegionType.province, null);
			
			
			if(provinceRegion == null){
				failMap.put(i+1, "Can't find area:"+province);
				continue;
			}
			smallRegion = provinceRegion;
			Region cityRegion = regionRepository.findByNameAndRegionTypeAndParent(city, RegionType.city, provinceRegion);
			
			if(cityRegion!=null){
				smallRegion = cityRegion;
			}
			
			Region districtRegion = regionRepository.findByNameAndRegionTypeAndParent(district, RegionType.district, cityRegion);
			
			if(districtRegion!=null){
				smallRegion = districtRegion;
			}
			
			
			campus.setRegion(smallRegion);
			String des = ExcelUtil.getStringFromExcelCell(row.getCell(6));
			campus.setDescription(des);
			String wish = ExcelUtil.getStringFromExcelCell(row.getCell(7));
			campus.setWish(wish);
			int noteScore = ExcelUtil.getIntFromExcelCell(row.getCell(8));
			campus.setNoteScore(noteScore);
			String name = ExcelUtil.getStringFromExcelCell(row.getCell(9));
			SchoolDistrict sd = schoolDistrictRepository.findByName(name);
			campus.setSchoolDistrict(sd);
			repository.save(campus);
		}
		return failMap;
	}
    
   

}
