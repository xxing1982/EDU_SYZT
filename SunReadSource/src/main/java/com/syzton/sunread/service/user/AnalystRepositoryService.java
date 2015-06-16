package com.syzton.sunread.service.user;

import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.EduGroup;
import com.syzton.sunread.model.region.Region;
import com.syzton.sunread.model.region.SchoolDistrict;
import com.syzton.sunread.model.user.Analyst;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.organization.EduGroupRepository;
import com.syzton.sunread.repository.region.RegionRepository;
import com.syzton.sunread.repository.region.SchoolDistrictRepository;
import com.syzton.sunread.repository.user.AnalystRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by jerry on 6/13/15.
 */

@Service
public class AnalystRepositoryService implements AnalystService{

    private AnalystRepository analystRepository;

    private CampusRepository campusRepository;

    private EduGroupRepository eduGroupRepository;

    private SchoolDistrictRepository schoolDistrictRepository;

    private RegionRepository regionRepository;

    @Autowired
    public AnalystRepositoryService(AnalystRepository analystRepository, CampusRepository campusRepository, EduGroupRepository eduGroupRepository, SchoolDistrictRepository schoolDistrictRepository, RegionRepository regionRepository) {
        this.analystRepository = analystRepository;
        this.campusRepository = campusRepository;
        this.eduGroupRepository = eduGroupRepository;
        this.schoolDistrictRepository = schoolDistrictRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public void add(Analyst analyst) {
        this.analystRepository.save(analyst);
    }

    @Override
    public void delete(Long Id) {
        this.analystRepository.delete(Id);
    }

    @Override
    public Analyst findByUserId(String userId) {

        Analyst analyst = analystRepository.findByUserId(userId);

        if (analyst == null){
            throw new NotFoundException("Analyst wiht userId = "+ userId +" not found..");
        }

        return analyst;
    }

    @Override
    public Page<Analyst> findByCampusId(Pageable pageable, long campusId) {

        Campus campus = campusRepository.findOne(campusId);
        if (campus == null){
            throw new NotFoundException("Campus with id = "+ campusId +" not found.. ");
        }

        return analystRepository.findByCampusId(pageable,campusId);
    }

    @Override
    public Page<Analyst> findByEduGroupId(Pageable pageable, long eduGroupId) {
        EduGroup eduGroup = eduGroupRepository.findOne(eduGroupId);
        if(eduGroup == null){
            throw new NotFoundException("EduGroup with id ="+eduGroupId+" not found..");
        }

        return analystRepository.findByEduGroupId(pageable,eduGroupId);
    }

    @Override
    public Page<Analyst> findBySchoolDistrictId(Pageable pageable, long schoolDistrictId) {

        SchoolDistrict schoolDistrict = schoolDistrictRepository.findOne(schoolDistrictId);
        if(schoolDistrict == null){
            throw new NotFoundException("SchoolDistrict with id ="+schoolDistrictId+" not found..");
        }
        return analystRepository.findByEduGroupId(pageable,schoolDistrictId);
    }

    @Override
    public Page<Analyst> findByRegionId(Pageable pageable, long regionId) {
        Region region = regionRepository.findOne(regionId);
        if (region == null){
            throw new NotFoundException("Region with id = "+regionId+" not found..");
        }

        return analystRepository.findByRegionId(pageable,regionId);
    }
}
