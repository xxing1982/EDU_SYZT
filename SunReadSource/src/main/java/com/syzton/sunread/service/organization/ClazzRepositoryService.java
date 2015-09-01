package com.syzton.sunread.service.organization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.validation.constraints.Max;

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

import com.syzton.sunread.dto.clazz.ClazzSumStatisticDTO;
import com.syzton.sunread.dto.clazz.ClazzSumStatisticDTO.ClassCategoryData;
import com.syzton.sunread.dto.clazz.ClazzSumStatisticDTO.ClassSum;
import com.syzton.sunread.dto.organization.ClazzDTO;
import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.ClazzCategoryCount;
import com.syzton.sunread.model.organization.ClazzStatistic;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.organization.EduGroupRepository;
import com.syzton.sunread.repository.region.SchoolDistrictRepository;
import com.syzton.sunread.repository.user.AdminRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import com.syzton.sunread.repository.user.UserRepository;
import com.syzton.sunread.util.ExcelUtil;
import com.syzton.sunread.util.UserUtil;

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
    
    private UserRepository userRepo;
    
    private AdminRepository adminRepo;
    
   

    @Autowired
    public ClazzRepositoryService(ClazzRepository repository,CampusRepository campusRepository,StudentRepository studentRepository,EduGroupRepository eduRepo,SchoolDistrictRepository schoolRepo,UserRepository userRepo,AdminRepository adminRepo) {
        this.repository = repository;
        this.campusRepository = campusRepository;
        this.studentRepository = studentRepository;
        this.eduRepo = eduRepo;
        this.schoolRepo = schoolRepo;
        this.userRepo = userRepo;
        this.adminRepo = adminRepo;
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
    public ClazzSumStatisticDTO getSumClazzStatistic(int grade, long campusId) throws NotFoundException{
    	    	
    	// Initliazte the clazzSumStatistic instance
    	ClazzSumStatisticDTO clazzSumStatisticDTO = new ClazzSumStatisticDTO();
    	clazzSumStatisticDTO.maxCoin = Long.MIN_VALUE;
    	clazzSumStatisticDTO.minCoin = Long.MAX_VALUE;
    	clazzSumStatisticDTO.maxReadNum = Long.MIN_VALUE;
    	clazzSumStatisticDTO.minReadNum = Long.MAX_VALUE;
    	clazzSumStatisticDTO.maxReadWord = Long.MIN_VALUE;
    	clazzSumStatisticDTO.minReadWord = Long.MAX_VALUE;
    	double totalCoin = 0;
    	double totalReadNum = 0;
    	double totalReadWord = 0;
    	
    	// Initlizate ClassCategoryData of clazzSumStatisticDTO
    	clazzSumStatisticDTO.classCategoryData = clazzSumStatisticDTO.new ClassCategoryData();
    	clazzSumStatisticDTO.classCategoryData.labels = new ArrayList<String>();
    	clazzSumStatisticDTO.classCategoryData.dataList = new ArrayList<ClassCategoryData.Data>();
    	
    	// Initlizate categoryCount labelMap
    	Set<String> labelSet = new TreeSet<String>();
    	List<TreeMap<String, Integer>> dataMapList = new ArrayList<TreeMap<String, Integer>>();

    	// Get clazz list 
    	List<Clazz> clazzes = repository.findByGradeAndCampus(grade, campusId);
    	clazzSumStatisticDTO.classSums = new ArrayList<ClazzSumStatisticDTO.ClassSum>();
    	
    	for ( Clazz clazz : clazzes ) {
    		
    		// Get data from clazzStatistic
    		ClazzStatistic clazzStatistic = clazz.getClazzStatistic();
    		long coin = clazzStatistic.getTotalCoin();
    		long readNum = clazzStatistic.getTotalReads();
    		long readWord = clazzStatistic.getTotalReadWords();
    		
    		// Save data to DTO
    		ClazzSumStatisticDTO.ClassSum classSum = clazzSumStatisticDTO.new ClassSum();
    		classSum.name = clazz.getName();
    		classSum.coin = coin;
    		classSum.readNum = readNum;
    		classSum.readWord = readWord;
    		clazzSumStatisticDTO.classSums.add(classSum);
    		
    		// Update clazzSumStatisticDTO maxim minimum variables
    		clazzSumStatisticDTO.maxCoin = Math.max(clazzSumStatisticDTO.maxCoin, coin);
    		clazzSumStatisticDTO.minCoin = Math.min(clazzSumStatisticDTO.minCoin, coin);
    		totalCoin += coin;
    		clazzSumStatisticDTO.maxReadNum = Math.max(clazzSumStatisticDTO.maxReadNum, readNum);
    		clazzSumStatisticDTO.minReadNum = Math.min(clazzSumStatisticDTO.minReadNum, readNum);
    		totalReadNum += readNum;
    		clazzSumStatisticDTO.maxReadWord = Math.max(clazzSumStatisticDTO.maxReadWord, readWord);
    		clazzSumStatisticDTO.minReadWord = Math.min(clazzSumStatisticDTO.maxReadWord, readWord);
    		totalReadWord += readWord;
    		
    		// Get data from categoryCount
    		Set<ClazzCategoryCount> categoryCount = clazz.getCategoryCount();
    		TreeMap<String, Integer> dataMap = new TreeMap<String, Integer>();
    		for (ClazzCategoryCount clazzCategoryCount : categoryCount) {
    			
    			// Save label to set
    			Dictionary dictionary = clazzCategoryCount.getDictionary();
    			String label = dictionary.getName();
    			labelSet.add(label);
    			
    			// Save label to map
    			int count = clazzCategoryCount.getCount();
    			dataMap.put(label, count);
    		}
    		dataMapList.add(dataMap);
    	}
    	
		// Update clazzSumStatisticDTO average variables
    	clazzSumStatisticDTO.avgCoin = (long) (totalCoin / clazzes.size());
    	clazzSumStatisticDTO.avgReadNum = (long) (totalReadNum / clazzes.size());
    	clazzSumStatisticDTO.avgReadWord = (long) (totalReadWord / clazzes.size());
    	
    	
    	// Save labels to DTO
		for (String label : labelSet) {
			clazzSumStatisticDTO.classCategoryData.labels.add(label);
		}
    	
		// Save dataList to DTO
		clazzSumStatisticDTO.classCategoryData.maxData = Long.MIN_VALUE;
		clazzSumStatisticDTO.classCategoryData.minData = Long.MAX_VALUE;
		double totalData = 0;
		Long dataNum = 0L;
    	
		for (TreeMap<String, Integer> dataMap : dataMapList) {
			ClassCategoryData.Data data = clazzSumStatisticDTO.classCategoryData.new Data();
			data.data = new ArrayList<Integer>();
    		for (String label : labelSet) {
    			
    			// Save count to data of DTO
    			Integer count = dataMap.get(label);
    			count = count == null ? 0 : count;
    			data.data.add(count);
    			
    			// Update total minimum maximum of data
    			clazzSumStatisticDTO.classCategoryData.maxData = Math.max(clazzSumStatisticDTO.classCategoryData.maxData, count);
    			clazzSumStatisticDTO.classCategoryData.minData = Math.min(clazzSumStatisticDTO.classCategoryData.minData, count);
    			totalData += count;  
    			dataNum ++ ;
    		}
			clazzSumStatisticDTO.classCategoryData.dataList.add(data);
    	}
		
		clazzSumStatisticDTO.classCategoryData.avgData = (long) ( totalData / dataNum );
    	
    	return clazzSumStatisticDTO;
    }

    @Override
    public List<Clazz> findByGrade(int grade) {
        return repository.findByGrade(grade);
    }
    
    @Override
    public List<Clazz> findByCampus(long campus) {
        return repository.findByCampus(campus);
    }
    
    @Override
	public Map<Integer,String> batchSaveOrUpdateClazzFromExcel(Sheet sheet) {
		Map<Integer,String> failMap = new HashMap<Integer,String>();
		String currentUserId = UserUtil.getUser();
		User user = userRepo.findByUserId(currentUserId);
		if(user==null||!UserUtil.isAdmin(user)){
			failMap.put(1, "非法用户");
		}
		long schoolCampusId = -1;
		if(UserUtil.isSchoolAdmin(user)){
			schoolCampusId = adminRepo.findByUserId(currentUserId).getCampusId();
		}
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
			Row row = sheet.getRow(i); 
			String className = ExcelUtil.getStringFromExcelCell(row.getCell(0));

			if("".equals(className)){
				failMap.put(i+1,  "class must not null:");
				break;
			}
			Campus campus;
			if(schoolCampusId==-1){
				String campusName = ExcelUtil.getStringFromExcelCell(row.getCell(2));
				campus = campusRepository.findByName(campusName);
				if(campus == null){
					failMap.put(i+1,  "查询不到校区:"+campusName);
					continue;
				}
			}else{
				campus = campusRepository.findOne(schoolCampusId);
			}
			
			Clazz clazz = repository.findByNameAndCampus(className, campus);
			if(clazz == null){
				clazz = new Clazz();
				clazz.setName(className);
				clazz.setCampus(campus);
				ClazzStatistic statistic = new ClazzStatistic();
				clazz.setClazzStatistic(statistic);
			}
			clazz.setGrade(ExcelUtil.getIntFromExcelCell(row.getCell(1)));
			clazz.setDescription(ExcelUtil.getStringFromExcelCell(row.getCell(3)));
			repository.save(clazz);
		}
		return failMap;
	}

    @Override
    public Clazz clazzUpgrade(long clazzId) throws NotFoundException{

        Clazz found =  this.findById(clazzId);

        int currentGrade = found.getGrade();
        currentGrade++;
        found.setGrade(currentGrade);
        return repository.save(found);
    }

    @Override
	public Clazz findByClazzNameAndCampus(String clazzName, Campus campus)
			throws NotFoundException {
		Clazz clazz = repository.findByNameAndCampus(clazzName, campus);
		return clazz;
	}
}
