package com.syzton.sunread.service.region;

import java.util.HashMap;
import java.util.Map;

import com.syzton.sunread.model.region.RegionType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import com.syzton.sunread.util.ExcelUtil;

/**
 * @author Morgan-Leon
 * @Date 2015年3月24日
 * 
 */
@Service
public class RegionRepositoryService implements RegionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RegionRepositoryService.class);

	private RegionRepository regionRepo;

	@Autowired
	public RegionRepositoryService(RegionRepository repository) {
		// TODO Auto-generated constructor stub
		this.regionRepo = repository;
	}

	@Transactional(rollbackFor = { DuplicateException.class })
	@Override
	public Region add(RegionDTO added) {
		LOGGER.debug("Adding a new Region with information: {}", added);

		Region province = getRegion(added.getProvince(),RegionType.province,null);
		Region city = getRegion(added.getCity(),RegionType.city,province);
		city.setParent(province);
		province.getSubRegion().add(city);

		Region district = getRegion(added.getDistrict(),RegionType.district,city);
		district.setParent(city);
		city.getSubRegion().add(district);
		return regionRepo.save(district);
	}

	private Region getRegion(String regionName,RegionType regionType,Region parent){
		Region region = regionRepo.findByNameAndRegionTypeAndParent(regionName, regionType,parent);
		if (region == null){
			region = new Region();
			region.setName(regionName);
			region.setRegionType(regionType);
			regionRepo.save(region);
		}
		return region;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Region deleteById(long id) {
		LOGGER.debug("Deleting a Region with id: {}", id);

		Region deleted = findOne(id);
		if (deleted == null)
			throw new NotFoundException("No Region found with id: " + id);

		LOGGER.debug("Deleting Region entry: {}", deleted);
		regionRepo.delete(deleted);
		return deleted;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Region update(Region updated) {
		LOGGER.debug("Updating contact with information: {}", updated);

		Region model = findOne(updated.getId());
		if (model == null)
			throw new NotFoundException("No Region found with id: "
					+ updated.getId());
		LOGGER.debug("Found a note entry: {}", model);
		regionRepo.save(updated);
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
	
//	@Transactional
//	@Override
//	public Map<Integer, String> batchSaveOrUpdateRegionFromExcel(Sheet sheet) {
//		Map<Integer, String> failMap = new HashMap<Integer, String>();
//
//		for (int i = sheet.getFirstRowNum()+1; i < sheet
//				.getPhysicalNumberOfRows(); i++) {
//			Row row = sheet.getRow(i);
//
//			String province = ExcelUtil.getStringFromExcelCell(row.getCell(0));
//			String city = ExcelUtil.getStringFromExcelCell(row.getCell(1));
//			String district = ExcelUtil.getStringFromExcelCell(row.getCell(2));
//			Region region = regionRepo.findByProvinceAndCityAndDistrict(province, city, district);
//			if(region == null){
//				region = new Region();
//				region.setProvince(province);
//				region.setCity(city);
//				region.setDistrict(district);
//			}
//			region.setDescription(ExcelUtil.getStringFromExcelCell(row.getCell(3)));
//			regionRepo.save(region);
//		}
//		return failMap;
//	}

}
