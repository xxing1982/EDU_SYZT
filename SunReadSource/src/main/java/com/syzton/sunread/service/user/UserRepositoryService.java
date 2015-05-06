package com.syzton.sunread.service.user;

import com.syzton.sunread.dto.user.UserExtraDTO;
import com.syzton.sunread.exception.common.AuthenticationException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.EduGroup;
import com.syzton.sunread.model.organization.School;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.model.user.*;
import com.syzton.sunread.model.user.User.GenderType;
import com.syzton.sunread.repository.SemesterRepository;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.organization.EduGroupRepository;
import com.syzton.sunread.repository.organization.SchoolRepository;
import com.syzton.sunread.repository.user.*;
import com.syzton.sunread.service.organization.EduGroupService;
import com.syzton.sunread.service.organization.SchoolService;
import com.syzton.sunread.util.ExcelUtil;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

/**
 * Created by jerry on 3/16/15.
 */
@Service
public class UserRepositoryService implements UserService,UserDetailsService{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserRepositoryService.class);

    private UserRepository userRepository;
    
    private PasswordEncoder passwordEncoder;

    private StudentRepository studentRepository;

    private ParentRepository parentRepository;
    
    private RoleRepository roleRepository;

    private TeacherRepository teacherRepository;

    private TeacherClazzRepository teacherClazzRepository;

    private SemesterRepository semesterRepository;

    private ClazzRepository clazzRepository;
    
    private CampusRepository campusRepository;
    
    private EduGroupRepository eduGroupRepo;
    
    private SchoolRepository schoolRepo;
    
    


    @Autowired
    public UserRepositoryService(UserRepository userRepository,
                                 StudentRepository studentRepository,
                                 ParentRepository parentRepository,
                                 TeacherRepository teacherRepository,
                                 TeacherClazzRepository teacherClazzRepository,
                                 SemesterRepository semesterRepository,
                                 ClazzRepository clazzRepository,
                                 PasswordEncoder passwordEncoder,
                                 CampusRepository campusRepository,
                                 EduGroupRepository eduGroupRepo,SchoolRepository schoolRepo) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.teacherRepository = teacherRepository;
        this.teacherClazzRepository = teacherClazzRepository;
        this.semesterRepository = semesterRepository;
        this.clazzRepository = clazzRepository;
        this.passwordEncoder = passwordEncoder;
        this.campusRepository = campusRepository;
        this.eduGroupRepo = eduGroupRepo;
        this.schoolRepo = schoolRepo;
        
    }

    @Override
    public User findById(Long id){
        User user = userRepository.findOne(id);
        if(user == null){
            throw new NotFoundException("user id =" + id +" does not exits...");
        }
        return user;

    }
    
//    @Transactional
//    @Override
//    public User addUser(User user) {
//        return userRepository.save(user);
//    }
    
    @Transactional
    @Override
    public User addUser(User user) {
        // should support duplicate username
//    	User repeat = userRepository.findByUsername(user.getUsername());
//    	if(repeat != null){
//    		throw new DuplicateException("User "+user.getUsername()+" duplicate.");
//    	}else{
            user.setPassword(encodePassword(user.getPassword()));
            return userRepository.save(user);
//    	}
    }
    

    @Transactional
    @Override
    public void deleteById(Long id)
    {
        User user = this.findById(id);
        userRepository.delete(user);
    }

    @Override
    public Student addStudent(Student student) {
    	student.setPassword(encodePassword(student.getPassword()));
        adjustClazzStatistic(student);
        
        return studentRepository.save(student);
    }

    private void adjustClazzStatistic(Student student) {
        Clazz clazz = clazzRepository.findOne(student.getClazzId());
        if(clazz == null){
            throw new NotFoundException("clazz id = "+student.getClazzId()+" not found..");
        }
        Number studentNum = clazzRepository.countStudentsInClazz(student.getClazzId(),student.getCampusId());
        clazz.getClazzStatistic().setStudentNum(studentNum.intValue());
        clazz.getClazzStatistic().setAvgPoints();
        clazz.getClazzStatistic().setAvgReads();
        clazzRepository.save(clazz);
    }

    @Override
    @Transactional(rollbackFor = NotFoundException.class)
    public User updateUser(long userId, UserExtraDTO userExtraDTO) {
        User user = userRepository.findOne(userId);
        if(user == null){
            throw new NotFoundException("user id ="+ userId+" not found.." );
        }
        if(userExtraDTO.getPassword()!=null && !"".equals(userExtraDTO.getPassword())){
            user.setPassword(encodePassword(userExtraDTO.getPassword().trim()));
        }else{
            if(userExtraDTO.getEmail()!=null && !"".equals(userExtraDTO.getEmail()))
                user.setEmail(userExtraDTO.getEmail());
            if(userExtraDTO.getPhoneNumber()!=null && !"".equals(userExtraDTO.getPhoneNumber()))
                user.setPhoneNumber(userExtraDTO.getPhoneNumber());
            if(userExtraDTO.getQqId()!=null && !"".equals(userExtraDTO.getQqId()))
                user.setQqId(userExtraDTO.getQqId());
            if(userExtraDTO.getWechatId() !=null && !"".equals(userExtraDTO.getWechatId()))
                user.setWechatId(userExtraDTO.getWechatId());
            if(userExtraDTO.getNickname()!=null && !"".equals(userExtraDTO.getNickname()))
                user.setNickname(userExtraDTO.getNickname());
            if(userExtraDTO.getPicture()!=null && !"".equals(userExtraDTO.getPicture())){
                user.setPicture(userExtraDTO.getPicture());
            }

        }

        return userRepository.save(user);

    }

    @Override
    public Student findByStudentId(Long id) {

        Student student = studentRepository.findOne(id);
        if(student == null)
            throw new NotFoundException("Student id ="+id+" not found..");

        return student;
    }

    @Override
    public Student deleteByStudentId(Long id) {
        Student student =  this.findByStudentId(id);
        studentRepository.delete(student);
        adjustClazzStatistic(student);
        return student;
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public Parent addParent(Parent parent, Long studentId) {

        //student can add her/his parent,each add operation will be as add a new parent.
        Student student = this.findByStudentId(studentId);
        parent.getChildren().add(student);
        parent.setPassword(encodePassword(parent.getPassword()));

        return parentRepository.save(parent);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public Parent addChildren(Long id, String userId) {

        Parent parent = this.findByParentId(id);

        Student student = studentRepository.findByUserId(userId);
        if(student == null)
            throw new NotFoundException("student with id = "+userId+" not found..");

        parent.getChildren().add(student);

        return parentRepository.save(parent);

    }

    @Override
    public Parent findByParentId(Long id) {

        Parent parent = parentRepository.findOne(id);
        if(parent == null)
            throw new NotFoundException("parent with id = "+ id +" not found..");

        return parent;
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return locateUser(userId);
    }
    
    @Override
    public User authenticate(String username, String password) {
        User user = locateUser(username);
        if(!passwordEncoder.encode(password).equals(user.getPassword())) {
            throw new AuthenticationException("User "+ username +" password error");
        }
        return user;
    }
    @Transactional
    @Override
    public Student addTask(long teacherId,long studentId,  int targetBookNum, int targetPoint) {
        Teacher teacher = teacherRepository.findOne(teacherId);
        if(teacher==null){
            throw new NotFoundException("teacher with id = "+teacherId+" not found..");
        }

        Student student = studentRepository.findOne(studentId);
        if(student == null){
            throw new NotFoundException("student with id ="+studentId+" not found..");
        }

        Task task = student.getTask();

        task.setTargetBookNum(targetBookNum);
        task.setTargetPoint(targetPoint);
        task.setTeacherId(teacherId);
        student.setTask(task);

        return studentRepository.save(student);

    }


    /**
     * Locate the user and throw an exception if not found.
     *
     * @return a User object is guaranteed.
     * @throws AuthenticationException if user not located.
     */
    private User locateUser(final String userId) {
        notNull(userId, "Mandatory argument 'userId' missing.");
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            LOGGER.debug("Credentials [{}] failed to locate a user.", userId.toLowerCase());
            throw new AuthenticationException("User "+userId+" didn't exist.");
        }
        return user;
    }

    private String encodePassword(String password) {
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

	@Override
	public User getSingleUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

    @Transactional
    @Override
    public Teacher addTeacher(Teacher teacher) {
        teacher.setPassword(encodePassword(teacher.getPassword()));
        teacher = teacherRepository.save(teacher);
//        for (Long clazzId: teacher.getClazzIds()){
//            TeacherClazz teacherClazz = new TeacherClazz();
//            teacherClazz.setClazzId(clazzId);
//            teacherClazz.setTeacherId(teacher.getId());
//            teacherClazzRepository.save(teacherClazz);
//
//        }
        return teacher ;
    }
    @Override
    public Page<Teacher> findTeacherByCampusId(long campusId,Pageable pageable){
        return teacherRepository.findByCampusId(campusId,pageable);
    }

    @Override
    public Teacher findByTeacherId(Long id) {
        return teacherRepository.findOne(id);
    }

    @Override
    public void deleteByTeacherId(Long id) {
        teacherRepository.delete(id);
    }

	@Override
	public Student saveStudent(Student student) {
		Student stu = studentRepository.save(student);
		return stu;
	}

    @Override
    public Page<Student> hotReadersInCampus(long campusId, Pageable pageable) {


        Page<Student> studentPage = studentRepository.findByCampusId(campusId,pageable);

        return studentPage;
    }


    @Override
    public Page<Student> hotReadersInClazz(long clazzId, Pageable pageable) {


        Page<Student> studentPage = studentRepository.findByClazzId(clazzId, pageable);

        return studentPage;
    }


    @Override
    public Page<Student> hotReadersInGrade(long gradeId, Pageable pageable) {


        Page<Student> studentPage = studentRepository.findByGradeId(gradeId, pageable);

        return studentPage;
    }

    @Override
	public User findByUserId(String userId) {
		User user = userRepository.findByUserId(userId);
		return user;
	}

	@Override
	public Map<Integer,String> batchSaveOrUpdateStudentFromExcel(Sheet sheet) {
		Map<Integer,String> failMap = new HashMap<Integer,String>();
		
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
			Row row = sheet.getRow(i);  
			String userId = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			if("".equals(userId)){
				failMap.put(i+1, "学号不能为空");
				break;
			}
			Student student = studentRepository.findByUserId(userId);
			if(student == null){
				student = new Student();
				student.setUserId(userId);
			}else{
				failMap.put(i+1, "导入失败,学号重复,数据库已经存在该学号学生:"+userId);
				continue;
			}
		   
		    student.setUsername(ExcelUtil.getStringFromExcelCell(row.getCell(1)));
		   	String password = ExcelUtil.getStringFromExcelCell(row.getCell(2));
		   	if("".equals(password)){
		   		password = "123456";
		   	}
		   	password = passwordEncoder.encode(password);
		   	student.setPassword(password);
		   	student.setAddress(ExcelUtil.getStringFromExcelCell(row.getCell(3)));
		   	String birthday = ExcelUtil.getStringFromExcelCell(row.getCell(4));
		   	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   	Date date;
			try {
				date = format.parse(birthday);
			} catch (ParseException e) {
				failMap.put(row.getRowNum()+1, "birthday date must yyyy-MM-dd,eg:1987-01-12."+e.getMessage());
				e.printStackTrace();
				continue;
			}
		   	student.setBirthday(date.getTime());
		   	Calendar calendar = Calendar.getInstance();
		   	calendar.setTime(date);
		   	int year = calendar.get(Calendar.YEAR);
		   	Calendar now  = Calendar.getInstance();
		   	now.setTime(new Date());
		   	int age = now.get(Calendar.YEAR)-year;
		   	student.setAge(age);
		   	student.setEmail(ExcelUtil.getStringFromExcelCell(row.getCell(5)));
		    String sex = ExcelUtil.getStringFromExcelCell(row.getCell(6));
		    if(sex.equals("女")){
		    	student.setGender(GenderType.famale);
		    }else{
		    	student.setGender(GenderType.male);
		    }
		    student.setNickname(ExcelUtil.getStringFromExcelCell(row.getCell(7)));
		    student.setPhoneNumber(ExcelUtil.getStringFromExcelCell(row.getCell(8)));
		    student.setPicture(ExcelUtil.getStringFromExcelCell(row.getCell(9)));
		    student.setQqId(ExcelUtil.getStringFromExcelCell(row.getCell(10)));
		    student.setWechatId(ExcelUtil.getStringFromExcelCell(row.getCell(11)));
		    student.setContactPhone(ExcelUtil.getStringFromExcelCell(row.getCell(12)));
		    student.setIdentity(ExcelUtil.getStringFromExcelCell(row.getCell(14)));
		    UserStatistic statistic = student.getStatistic();
			if(statistic == null){
				statistic = new UserStatistic();
				student.setStatistic(statistic);
			}
			statistic.setCoin(ExcelUtil.getIntFromExcelCell(row.getCell(15)));
			statistic.setPoint(ExcelUtil.getIntFromExcelCell(row.getCell(16)));
			statistic.setLevel(ExcelUtil.getIntFromExcelCell(row.getCell(17)));
			String campusName = ExcelUtil.getStringFromExcelCell(row.getCell(18));
			String schoolName = ExcelUtil.getStringFromExcelCell(row.getCell(19));
			String eduGroupName = ExcelUtil.getStringFromExcelCell(row.getCell(20));
			
			
			EduGroup group = eduGroupRepo.findByName(eduGroupName);
			if(group == null){
				failMap.put(i+1, "查询不到该教育集团:"+eduGroupName);
				continue;
			}
			School school = schoolRepo.findByNameAndEduGroup(schoolName, group);
			if(school == null){
				failMap.put(i+1,  "查询不到该学校:"+schoolName);
				continue;
			}
			Campus campus = campusRepository.findByNameAndSchool(campusName, school);
			if(campus == null){
				failMap.put(i+1,  "查询不到该校区:"+campusName);
				continue; 
			}
			
			student.setCampusId(campus.getId());
			String className = ExcelUtil.getStringFromExcelCell(row.getCell(13));
			Clazz clazz = clazzRepository.findByNameAndCampus(className,campus);
			if(clazz == null){
				failMap.put(row.getRowNum()+1, "can't find clazz with name:" + className);
				continue;
			}
			student.setClazzId(clazz.getId());
			studentRepository.save(student);
		}  
		return failMap;
	}

	@Override
	public Map<Integer,String> batchSaveOrUpdateTeacherFromExcel(Sheet sheet) {
		Map<Integer,String> failMap = new HashMap<Integer,String>();
		
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
			Row row = sheet.getRow(i);  
			String userId = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			if("".equals(userId)){
				break;
			}
			Teacher teacher = teacherRepository.findByUserId(userId);
			if(teacher == null){
				teacher = new Teacher();
			}else{
				failMap.put(i+1, "导入失败,教师ID重复,数据库已经存在该教师ID:"+userId);
				continue;
			}
			teacher.setUserId(userId);
		   
		    teacher.setUsername(ExcelUtil.getStringFromExcelCell(row.getCell(1)));
		   	String password = ExcelUtil.getStringFromExcelCell(row.getCell(2));
		   	if("".equals(password)){
		   		password = "123456";
		   		failMap.put(i+1, "未发现密码,使用系统默认密码:"+123456);
		   	}
		   	password = passwordEncoder.encode(password);
		   	teacher.setPassword(password);
		   	teacher.setAddress(ExcelUtil.getStringFromExcelCell(row.getCell(3)));
		  
		   	String birthday = ExcelUtil.getStringFromExcelCell(row.getCell(4));
		   	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   	Date date;
			try {
				date = format.parse(birthday);
			} catch (ParseException e) {
				failMap.put(row.getRowNum()+1, "birthday date must yyyy-MM-dd,eg:1987-01-12."+e.getMessage());
				e.printStackTrace();
				continue;
			}
		   	teacher.setBirthday(date.getTime());
		   	Calendar calendar = Calendar.getInstance();
		   	calendar.setTime(date);
		   	int year = calendar.get(Calendar.YEAR);
		   	Calendar now  = Calendar.getInstance();
		   	now.setTime(new Date());
		   	int age = now.get(Calendar.YEAR)-year;
		   	teacher.setAge(age);
		   	teacher.setEmail(ExcelUtil.getStringFromExcelCell(row.getCell(5)));
		    String sex = ExcelUtil.getStringFromExcelCell(row.getCell(6));
		    if(sex.equals("女")){
		    	teacher.setGender(GenderType.famale);
		    }else{
		    	teacher.setGender(GenderType.male);
		    }
		    teacher.setNickname(ExcelUtil.getStringFromExcelCell(row.getCell(7)));
		    teacher.setPhoneNumber(ExcelUtil.getStringFromExcelCell(row.getCell(8)));
		    teacher.setPicture(ExcelUtil.getStringFromExcelCell(row.getCell(9)));
		    teacher.setQqId(ExcelUtil.getStringFromExcelCell(row.getCell(10)));
		    teacher.setWechatId(ExcelUtil.getStringFromExcelCell(row.getCell(11)));
		    teacher.setContactPhone(ExcelUtil.getStringFromExcelCell(row.getCell(12)));
		    teacher.setGraduateSchool(ExcelUtil.getStringFromExcelCell(row.getCell(14)));
		    teacher.setRank(ExcelUtil.getIntFromExcelCell(row.getCell(15)));
		 	teacher.setExperience(ExcelUtil.getIntFromExcelCell(row.getCell(16)));
			teacher.setTeaching(ExcelUtil.getStringFromExcelCell(row.getCell(17)));
		 	String campusName = ExcelUtil.getStringFromExcelCell(row.getCell(18));
			String schoolName = ExcelUtil.getStringFromExcelCell(row.getCell(19));
			String eduGroupName = ExcelUtil.getStringFromExcelCell(row.getCell(20));
			
			
			EduGroup group = eduGroupRepo.findByName(eduGroupName);
			if(group == null){
				failMap.put(i+1, "查询不到该教育集团:"+eduGroupName);
				continue;
			}
			School school = schoolRepo.findByNameAndEduGroup(schoolName, group);
			if(school == null){
				failMap.put(i+1,  "查询不到该学校:"+schoolName);
				continue;
			}
			Campus campus = campusRepository.findByNameAndSchool(campusName, school);
			if(campus == null){
				failMap.put(i+1,  "查询不到该校区:"+campusName);
				continue; 
			}
			
			
			teacher.setCampusId(campus.getId());
			String className = ExcelUtil.getStringFromExcelCell(row.getCell(13));
			Clazz clazz = clazzRepository.findByNameAndCampus(className,campus);
			if(clazz == null){
				failMap.put(row.getRowNum()+1, "can't find clazz with name:" + className);
				continue;
			}
			teacher.setClassId(clazz.getId());
			teacherRepository.save(teacher);
		}  
		return failMap;
	}

}
