package com.syzton.sunread.service.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syzton.sunread.dto.organization.ClazzDTO;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.ClazzStatistic;
import com.syzton.sunread.model.organization.ClazzSumStatistic;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.organization.EduGroupRepository;
import com.syzton.sunread.repository.region.SchoolDistrictRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import javassist.NotFoundException;

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
public class ClazzRepositoryService implements ClazzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClazzRepositoryService.class);
    
    private ClazzRepository repository;
    
    private StudentRepository studentRepository;
    
    private CampusRepository campusRepository;
    
    private EduGroupRepository eduRepo;
    
    private SchoolDistrictRepository schoolRepo;

    @Autowired
    public ClazzRepositoryService(ClazzRepository repository,CampusRepository campusRepository,StudentRepository studentRepository,EduGroupRepository eduRepo,SchoolDistrictRepository schoolRepo) {
        this.repository = repository;
        this.campusRepository = campusRepository;
        this.studentRepository = studentRepository;
        this.eduRepo = eduRepo;
        this.schoolRepo = schoolRepo;
    }

    @Override
    public Clazz add(ClazzDTO add, Long campusId) throws NotFoundException{

        LOGGER.debug("Adding a new clazz entry with information: {}", add);
        Campus campus = campusRepository.findOne(campusId);
        if(campus == null){
            throw new NotFoundException("campus id ="+campusId+" not found...");
        }
        Clazz model = Clazz.getBuilder(add.getName(),add.getGrade(),campus)
        		.description(add.getDescription()).build();        
        return repository.save(model);

    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Clazz deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a clazz entry with id: {}", id);
        
        Clazz deleted = findById(id);
        LOGGER.debug("Deleting clazz entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Clazz update(ClazzDTO updated)throws  NotFoundException{
        LOGGER.debug("Updating contact with information: {}", updated);

        Clazz model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName());
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Clazz findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a clazz entry with id: {}", id);

        Clazz found = repository.findOne(id);
        LOGGER.debug("Found clazz entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No clazz entry found with id: " + id);
        }

        return found;
    }
    
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Clazz findByClazzName(String clazzName) throws NotFoundException {
        LOGGER.debug("Finding a clazz entry with name: {}", clazzName);

        Clazz found = repository.findByName(clazzName);
        LOGGER.debug("Found clazz entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No clazz entry found with name: " + clazzName);
        }

        return found;
    }

    


    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<Clazz> findAll(Pageable pageable) throws NotFoundException {
        LOGGER.debug("Finding all clazz entries");
        return repository.findAll(pageable);
    }

    @Override
    public Page<Clazz> findByCampus(Long campusId, Pageable pageable) throws NotFoundException {
        Campus campus = campusRepository.findOne(campusId);
        if(campus == null){
            throw new NotFoundException("campus id "+campusId+"not found...");
        }

        Page<Clazz> clazzPage = repository.findByCampus(campus,pageable);
        return clazzPage;
    }

    @Override
	public List<Student> findAllStudentFromClazz(Long clazzId) {
		return studentRepository.findByClazzId(clazzId);
	
	}
    
    
 
	@Override
	public int getAveragePointsfromClass(Long clazzId) throws NotFoundException {
		List<Student> students = findAllStudentFromClazz(clazzId);
		int total = 0;
		if(students.size() == 0){
			throw new NotFoundException("Can't find class id:"+clazzId);
		}
		for(int i=0;i<students.size();i++){
			Student student = students.get(i);
			total = total + student.getStatistic().getPoint();
		}
		return total/students.size();
	}

	@Override
	public int getAverageReadingBookFromClass(Long clazzId)
			throws NotFoundException {
		List<Student> students = findAllStudentFromClazz(clazzId);
		int total = 0;
		if(students.size() == 0){
			throw new NotFoundException("Can't find class id:"+clazzId);
		}
		for(int i=0;i<students.size();i++){
			Student student = students.get(i);
			total = total + student.getStatistic().getReadNum();
		}
		return total/students.size();
	}

    @Override
    public ClazzSumStatistic getSumClazzStatistic(int grade) throws NotFoundException{

        ClazzSumStatistic clazzSumStatistic = repository.getSumStatisticClazz(grade);

        return clazzSumStatistic;
    }

    @Override
    public List<Clazz> findByGrade(int grade) {
        return repository.findByGrade(grade);
    }
    
    @Override
	public Map<Integer,String> batchSaveOrUpdateClazzFromExcel(Sheet sheet) {
		Map<Integer,String> failMap = new HashMap<Integer,String>();
//		
//		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
//			Row row = sheet.getRow(i); 
//			String className = ExcelUtil.getStringFromExcelCell(row.getCell(0));
//			if("".equals(className)){
//				break;
//			}
//			
//			String eduGroupName = ExcelUtil.getStringFromExcelCell(row.getCell(4));
//			String schoolName = ExcelUtil.getStringFromExcelCell(row.getCell(3));
//			EduGroup group = eduRepo.findByName(eduGroupName);
//			if(group == null){
//				failMap.put(i+1, "查询不到该教育集团:"+eduGroupName);
//				continue;
//			}
//			School school = schoolRepo.findByNameAndEduGroup(schoolName, group);
//			if(school == null){
//				failMap.put(i+1,  "查询不到该学校:"+schoolName);
//				continue;
//			}
//			String campusName = ExcelUtil.getStringFromExcelCell(row.getCell(2));
//			Campus campus = campusRepository.findByNameAndSchool(campusName, school);
//			if(campus == null){
//				failMap.put(i+1,  "查询不到校区:"+campusName);
//				continue;
//			}
//			
//			Clazz clazz = repository.findByNameAndCampus(className, campus);
//			if(clazz == null){
//				clazz = new Clazz();
//				clazz.setName(className);
//				clazz.setCampus(campus);
//				ClazzStatistic statistic = new ClazzStatistic();
//				clazz.setClazzStatistic(statistic);
//			}
//			clazz.setGrade(ExcelUtil.getIntFromExcelCell(row.getCell(1)));
//			clazz.setDescription(ExcelUtil.getStringFromExcelCell(row.getCell(5)));
//			repository.save(clazz);
//		}
		return failMap;
	}
}
