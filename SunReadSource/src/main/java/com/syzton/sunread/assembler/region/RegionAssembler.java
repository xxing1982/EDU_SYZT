package com.syzton.sunread.assembler.region;

import com.syzton.sunread.dto.region.RegionDTO;
import com.syzton.sunread.model.region.Region;

/**
 * @author Morgan-Leon
 * @Date 2015年3月24日
 * 
 */
public class RegionAssembler {

	public Region fromDTOtoModel(final RegionDTO DTO) {
		Region region =  new Region();
		region.setProvince(DTO.getProvince());
		region.setCity(DTO.getCity());
		region.setDistrict(DTO.getDistrict());
		region.setDescription(DTO.getDescription());
		return region;
	}
}
