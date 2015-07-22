 package com.syzton.sunread.service.user;

import static org.springframework.util.Assert.notNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.user.UserExtraDTO;
import com.syzton.sunread.exception.common.AuthenticationException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.security.Role;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.model.user.Admin;
import com.syzton.sunread.model.user.Parent;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.SystemAdmin;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.model.user.User.GenderType;
import com.syzton.sunread.model.user.UserStatistic;
import com.syzton.sunread.repository.SemesterRepository;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.organization.EduGroupRepository;
import com.syzton.sunread.repository.region.SchoolDistrictRepository;
import com.syzton.sunread.repository.user.AdminRepository;
import com.syzton.sunread.repository.user.ParentRepository;
import com.syzton.sunread.repository.user.RoleRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import com.syzton.sunread.repository.user.SystemAdminRepository;
import com.syzton.sunread.repository.user.TeacherClazzRepository;
import com.syzton.sunread.repository.user.TeacherRepository;
import com.syzton.sunread.repository.user.UserRepository;
import com.syzton.sunread.service.bookshelf.BookshelfService;
import com.syzton.sunread.service.task.TaskRepositoryService;
import com.syzton.sunread.service.task.TaskService;
import com.syzton.sunread.util.ExcelUtil;

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
    
    private SchoolDistrictRepository schoolRepo;

    private BookshelfService bookshelfService;

    
    private AdminRepository adminRepo;
    
    private SystemAdminRepository systemAdminRepo;

    private TaskService taskService;


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
                                 EduGroupRepository eduGroupRepo,SchoolDistrictRepository schoolRepo,
                                 BookshelfService bookshelfService,
                                 RoleRepository roleRepository,
                                 AdminRepository adminRepo,
                                 TaskService taskService,
                                 SystemAdminRepository systemAdminRepo) {
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
        this.bookshelfService = bookshelfService;
        this.roleRepository = roleRepository;
        this.adminRepo = adminRepo;
        this.systemAdminRepo = systemAdminRepo;
        this.taskService = taskService;
        this.teacherClazzRepository = teacherClazzRepository;
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
    public Task addTask(long teacherId,long studentId,  int targetBookNum, int targetPoint) {
        Teacher teacher  = getTeacher(teacherId);
        if(teacher == null){
            throw new NotFoundException("teacher with id ="+teacherId+" not found..");
        }
        Student student = studentRepository.findOne(studentId);
        if(student == null){
            throw new NotFoundException("student with id ="+studentId+" not found..");
        }
        Clazz clazz = clazzRepository.findOne(student.getClazzId());
        Campus campus = clazz.getCampus();
        Semester semester = semesterRepository.findByTimeAndCampus(DateTime.now(),campus);
        if(semester == null){
            throw new NotFoundException("semester with id ="+campus.getId()+" not found..");
        }
        Task task = taskService.findByStudentIdAndSemesterId(studentId, semester.getId());
        if (task == null) {
        	task = new Task();
        }
        task.setSemesterId(semester.getId());
        task.setTargetBookNum(targetBookNum);
        task.setTargetPoint(targetPoint);
        task.setTeacherId(teacherId);
        task.setStudentId(studentId);

        taskService.add(task);
        return task;
    }

    private Teacher getTeacher(long teacherId) {
        Teacher teacher = teacherRepository.findOne(teacherId);
        if(teacher==null){
            throw new NotFoundException("teacher with id = "+teacherId+" not found..");
        }

        List<Clazz> clazzs = teacherClazzRepository.findByTeacherId(teacherId);
        teacher.setCurrentClassId(clazzs.get(0).getId());

        return  teacher;
    }

    @Transactional
    @Override
    public void addTasks(long teacherId, int targetBookNum, int targetPoint) {
        Teacher teacher = getTeacher(teacherId);

        List<Student> students = studentRepository.findByClazzId(teacher.getCurrentClassId());
        if(students == null){
            throw new NotFoundException("students with classId =" + teacher.getCurrentClassId() +" not found..");
        }

        for (Student student: students){
            Clazz clazz = clazzRepository.findOne(student.getClazzId());
            Campus campus = clazz.getCampus();
            Semester semester = semesterRepository.findByTimeAndCampus(DateTime.now(), campus);
            if(semester == null){
                throw new NotFoundException("semester with id ="+campus.getId()+" not found..");
            }
            Task task = taskService.findByStudentIdAndSemesterId(student.getId(), semester.getId());
            if (task == null) {
            	task = new Task();
            }
		    task.setTargetBookNum(targetBookNum);
		    task.setTargetPoint(targetPoint);
		    task.setTeacherId(teacherId);
            task.setStudentId(student.getId());
            task.setSemesterId(semester.getId());
            taskService.add(task);
        }
        
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
       // return teacherRepository.findOne(id);
        return this.getTeacher(id);
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
             if (birthday!=null && !birthday.equals("")) {
                 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                 Date date;
                 try {
                     date = format.parse(birthday);
                 } catch (ParseException e) {
                     failMap.put(row.getRowNum() + 1, "birthday date must yyyy-MM-dd,eg:1987-01-12." + e.getMessage());
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
             }
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
 			//String schoolName = ExcelUtil.getStringFromExcelCell(row.getCell(19));
 			//String eduGroupName = ExcelUtil.getStringFromExcelCell(row.getCell(20));
 			
 			
 			
 			Campus campus = campusRepository.findByName(campusName);
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
 
             Role role = roleRepository.findOne(3L);
             List<Role> roles = new ArrayList<Role>();
             roles.add(role);
             student.setRoles(roles);
  	 		 Student added = studentRepository.save(student);
             bookshelfService.addBookshelfByStudent(added);
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
			//String schoolName = ExcelUtil.getStringFromExcelCell(row.getCell(19));
			//String eduGroupName = ExcelUtil.getStringFromExcelCell(row.getCell(20));
			
//			
//			EduGroup group = eduGroupRepo.findByName(eduGroupName);
//			if(group == null){
//				failMap.put(i+1, "查询不到该教育集团:"+eduGroupName);
//				continue;
//			}
//			School school = schoolRepo.findByNameAndEduGroup(schoolName, group);
//			if(school == null){
//				failMap.put(i+1,  "查询不到该学校:"+schoolName);
//				continue;
//			}
			Campus campus = campusRepository.findByName(campusName);
			if(campus == null){
				failMap.put(i+1,  "查询不到该校区:"+campusName);
				continue; 
			}
//			
//			
			teacher.setCampusId(campus.getId());
			String className = ExcelUtil.getStringFromExcelCell(row.getCell(13));
			Clazz clazz = clazzRepository.findByNameAndCampus(className,campus);
			if(clazz == null){
				failMap.put(row.getRowNum()+1, "can't find clazz with name:" + className);
				continue;
			}
//
            Role role = roleRepository.findOne(2L);
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            teacher.setRoles(roles);
			//teacher.setClassId(clazz.getId());
			teacherRepository.save(teacher);
		}  
		return failMap;
	}
    @Override
    public Map<Integer,String> batchSaveOrUpdateCMSAdminFromExcel(Sheet sheet) {
        Map<Integer,String> failMap = new HashMap<Integer,String>();

//        for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {
//            Row row = sheet.getRow(i);
//            String userId = ExcelUtil.getStringFromExcelCell(row.getCell(0));
//            if("".equals(userId)){
//                break;
//            }
//            User user = userRepository.findByUserId(userId);
//            if(user == null){
//                user = new Teacher();
//            }else{
//                failMap.put(i+1, "导入失败,Admin ID重复,数据库已经存在该Admin ID:"+userId);
//                continue;
//            }
//            user.setUserId(userId);
//
//            user.setUsername(ExcelUtil.getStringFromExcelCell(row.getCell(1)));
//            String password = ExcelUtil.getStringFromExcelCell(row.getCell(2));
//            if("".equals(password)){
//                password = "123456";
//                failMap.put(i+1, "未发现密码,使用系统默认密码:"+123456);
//            }
//            password = passwordEncoder.encode(password);
//            user.setPassword(password);
//            user.setAddress(ExcelUtil.getStringFromExcelCell(row.getCell(3)));
//
//            String birthday = ExcelUtil.getStringFromExcelCell(row.getCell(4));
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date date;
//            try {
//                date = format.parse(birthday);
//            } catch (ParseException e) {
//                failMap.put(row.getRowNum()+1, "birthday date must yyyy-MM-dd,eg:1987-01-12."+e.getMessage());
//                e.printStackTrace();
//                continue;
//            }
//            user.setBirthday(date.getTime());
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            int year = calendar.get(Calendar.YEAR);
//            Calendar now  = Calendar.getInstance();
//            now.setTime(new Date());
//            int age = now.get(Calendar.YEAR)-year;
//            user.setAge(age);
//            user.setEmail(ExcelUtil.getStringFromExcelCell(row.getCell(5)));
//            String sex = ExcelUtil.getStringFromExcelCell(row.getCell(6));
//            if(sex.equals("女")){
//                user.setGender(GenderType.famale);
//            }else{
//                user.setGender(GenderType.male);
//            }
//            user.setNickname(ExcelUtil.getStringFromExcelCell(row.getCell(7)));
//            user.setPhoneNumber(ExcelUtil.getStringFromExcelCell(row.getCell(8)));
//            user.setPicture(ExcelUtil.getStringFromExcelCell(row.getCell(9)));
//            user.setQqId(ExcelUtil.getStringFromExcelCell(row.getCell(10)));
//            user.setWechatId(ExcelUtil.getStringFromExcelCell(row.getCell(11)));
//            user.setContactPhone(ExcelUtil.getStringFromExcelCell(row.getCell(12)));
//
//
//            Role role = roleRepository.findOne(5L);
//            List<Role> roles = new ArrayList<>();
//            roles.add(role);
//            user.setRoles(roles);
//            userRepository.save(user);
//        }
        return failMap;
    }
    
  

	private User getUser() {
		LOGGER.debug("Getting principal from the security context");

		User principal = null;
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication != null) {
			Object currentPrincipal = authentication.getPrincipal();
			if (currentPrincipal instanceof User) {
				principal = (User) currentPrincipal;
			}
		}

		if (principal != null) {
			user = findByUserId(principal.getUserId());
		}

		return user;
	}

	public String updateSuperAdminPassword(String newPassword, String oldPassword) {
		User user = this.getUser();
		Role role = new Role();
		role.setName("ROLE_SYSTEM_SUPER_ADMIN");
		if (user == null
				|| newPassword == null
				|| "".equals(newPassword.trim())
				|| !user.hasRole(role)
				|| passwordEncoder.encode(oldPassword).equals(
						user.getPassword())) {
			return "旧密码输入错误";
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		return "success";
	}

	public String addSystemAdmin(String userId, String password) {
		User currentUser = getUser();
		Role superRole = new Role();
		superRole.setName("ROLE_SYSTEM_SUPER_ADMIN");
		if (!currentUser.hasRole(superRole)) {
			return "只有超级管理员才有权限添加系统管理员";
		}
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			SystemAdmin systemAdmin = new SystemAdmin();
			systemAdmin.setUserId(userId);
			systemAdmin.setUsername("系统管理员");
			password = passwordEncoder.encode(password);
			systemAdmin.setPassword(password);
			systemAdmin.setAddress("");
			systemAdmin.setBirthday((new Date()).getTime());
			systemAdmin.setAge(1);
			systemAdmin.setEmail("");
			systemAdmin.setGender(GenderType.male);
			systemAdmin.setNickname("");
			systemAdmin.setPhoneNumber("123456");
			systemAdmin.setPicture("");
			systemAdmin.setQqId("");
			systemAdmin.setWechatId("");
			systemAdmin.setContactPhone("");
			systemAdmin.setSuperAdmin(false);
			Role role = roleRepository.findByName("ROLE_SYSTEM_ADMIN");
			if (role == null) {
				Role systemRole = new Role();
				systemRole.setName("ROLE_SYSTEM_ADMIN");
				systemRole.setDesc("ROLE_SYSTEM_ADMIN");
				role = roleRepository.save(systemRole);
			}
			systemAdmin.addRole(role);
			systemAdmin.setSuperAdmin(false);
			systemAdminRepo.save(systemAdmin);
			return "success";
		} else {
			return "该管理员已经存在";
		}
	}

	public String updateSystemAdmin(String userId, String oldPassword,
			String newPassword) {
		User currentUser = getUser();
		Role superRole = new Role();
		superRole.setName("ROLE_SYSTEM_SUPER_ADMIN");
		User user = findByUserId(userId);
		if (user == null) {
			return "该用户不存在";
		}
		if (currentUser.hasRole(superRole)) {
			user.setPassword(passwordEncoder.encode(newPassword));
		} else if (userId.equals(currentUser.getUserId())) {
			if (passwordEncoder.encode(oldPassword).equals(user.getPassword())) {
				user.setPassword(newPassword);
			} else {
				return "输入密码错误";
			}
		} else {
			return "只有超级管理员和管理员自己才有权限修改系统管理员密码";
		}
		userRepository.save(user);
		return "success";
	}
	
	public Page<SystemAdmin> getSystemAdmins(boolean isSuperAdmin,Pageable pageable){
		return systemAdminRepo.findBySuperAdmin(isSuperAdmin, pageable);
	}
	
	public Page<Admin> getSchoolAdmins(long campusId,boolean isSuperAdmin,Pageable pageable){
		return adminRepo.findByCampusIdAndSuperAdmin(campusId, isSuperAdmin, pageable);
	}
	
	public Page<Admin> getAllSchoolAdmins(long campusId,Pageable pageable){
		return adminRepo.findByCampusId(campusId, pageable);
	}
	
	 
	
    public String deleteSystemAdminId(Long id) {
    	User currentUser = getUser();
		Role superRole = new Role();
		superRole.setName("ROLE_SYSTEM_SUPER_ADMIN");
		if(currentUser.hasRole(superRole));
        SystemAdmin admin = this.findBySystemAdminId(id);  
        systemAdminRepo.delete(admin);
        return "success";
    }
    
   
    public SystemAdmin findBySystemAdminId(Long id) {
        SystemAdmin admin = systemAdminRepo.findOne(id);
        if(admin == null)
            throw new NotFoundException("Student id ="+id+" not found..");
        return admin;
    }
    
    public Admin findByAdminId(Long id) {
        Admin admin = adminRepo.findOne(id);
        if(admin == null)
            throw new NotFoundException("Student id ="+id+" not found..");
        return admin;
    }
    
    public String deleteAdminId(Long id) {
    	User currentUser = getUser();
		Role superRole = new Role("ROLE_SYSTEM_SUPER_ADMIN");
		Role systemRole = new Role("ROLE_SYSTEM_ADMIN");
		Role schoolSuperRole = new Role("ROLE_SCHOOLE_SUPER_ADMIN");
		Admin admin = this.findByAdminId(id);
		if(admin == null){
			return "该用户不存在";
		}
		if(!currentUser.hasRole(superRole)&&!currentUser.hasRole(systemRole)){
			if(admin.hasRole(schoolSuperRole)){
				return "不能删除同级别用户";
			}
			if(currentUser.hasRole(schoolSuperRole)){
				long campusId = this.findByAdminId(currentUser.getId()).getCampusId();
				if(admin.getCampusId() != campusId){
					return "不能删除其他学校管理员";
				}
			}else{
				return "无权限删除该管理员";
			}
		}
        adminRepo.delete(admin);
        return "success";
    }
	
	public String addSchoolSuperAdmin(String userId, String password,long campusId) {
		User currentUser = getUser();
		Role superSystemRole = new Role("ROLE_SYSTEM_SUPER_ADMIN");
		Role systemRole = new Role("ROLE_SYSTEM_ADMIN");
		if(!currentUser.hasRole(superSystemRole)&&!currentUser.hasRole(systemRole)){
			return "只有系统管理员和超级管理员有权限添加学校用户";
		}
		
		Admin schoolSuperAdmin = null;
		User user = this.findByUserId(userId);
		if(user!=null){
			schoolSuperAdmin = this.findByAdminId(user.getId());
		}
		if(schoolSuperAdmin!=null){
			return "该用户已经存在";
		}else{
			schoolSuperAdmin = new Admin();
			schoolSuperAdmin.setUserId(userId);
			schoolSuperAdmin.setUsername("学校系统管理员");
			password = passwordEncoder.encode(password);
			schoolSuperAdmin.setPassword(password);
			schoolSuperAdmin.setAddress("");
			schoolSuperAdmin.setBirthday((new Date()).getTime());
			schoolSuperAdmin.setAge(1);
			schoolSuperAdmin.setEmail("");
			schoolSuperAdmin.setGender(GenderType.male);
			schoolSuperAdmin.setNickname("");
			schoolSuperAdmin.setPhoneNumber("123456");
			schoolSuperAdmin.setPicture("");
			schoolSuperAdmin.setQqId("");
			schoolSuperAdmin.setWechatId("");
			schoolSuperAdmin.setContactPhone("");
			schoolSuperAdmin.setCampusId(campusId);
			schoolSuperAdmin.setSuperAdmin(true);
			Role role = roleRepository.findByName("ROLE_SCHOOLE_SUPER_ADMIN");
			schoolSuperAdmin.addRole(role);
			adminRepo.save(schoolSuperAdmin);
			return "success";
		}
	}
	


	public String updateSchoolSuperAdminPassword(String userId,String oldPassword,String newPassword) {
			User currentUser = getUser();
			Role superSystemRole = new Role("ROLE_SYSTEM_SUPER_ADMIN");
			Role systemRole = new Role("ROLE_SYSTEM_ADMIN");
			User user = findByUserId(userId);
			if (user == null) {
				return "该用户不存在";
			}
			if (currentUser.hasRole(systemRole)||currentUser.hasRole(superSystemRole)) {
				user.setPassword(passwordEncoder.encode(newPassword));
			} else if (userId.equals(currentUser.getUserId())) {
				if (passwordEncoder.encode(oldPassword).equals(user.getPassword())) {
					user.setPassword(newPassword);
				} else {
					return "输入密码错误";
				}
			} else {
				return "只有超级管理员和管理员自己才有权限修改系统管理员密码";
			}
			userRepository.save(user);
			return "修改成功";
	}

	
	public String addSchoolAdmin(String userId, String password,long campusId) {
		User currentUser = getUser();
		Role superSystemRole = new Role("ROLE_SYSTEM_SUPER_ADMIN");
		Role systemRole = new Role("ROLE_SYSTEM_ADMIN");
		Role schoolSuperRole = new Role("ROLE_SCHOOLE_SUPER_ADMIN");
		if(!currentUser.hasRole(superSystemRole)&&!currentUser.hasRole(systemRole)){
			if(currentUser.hasRole(schoolSuperRole)){
				Admin schoolSuperAdmin = this.findByAdminId(currentUser.getId());
				campusId = schoolSuperAdmin.getCampusId();
			}else{
				return "只有系统管理员和超级管理员有权限添加学校管理员用户";
			}
		}
		
		User user = this.findByUserId(userId);
		Admin schoolSuperAdmin = null;
		if(user != null){
			schoolSuperAdmin = this.findByAdminId(user.getId());
		}
		if(schoolSuperAdmin!=null){
			return "该用户已经存在";
		}else{
			schoolSuperAdmin = new Admin();
			schoolSuperAdmin.setUserId(userId);
			schoolSuperAdmin.setUsername("学校系统管理员");
			password = passwordEncoder.encode(password);
			schoolSuperAdmin.setPassword(password);
			schoolSuperAdmin.setAddress("");
			schoolSuperAdmin.setBirthday((new Date()).getTime());
			schoolSuperAdmin.setAge(1);
			schoolSuperAdmin.setEmail("");
			schoolSuperAdmin.setGender(GenderType.male);
			schoolSuperAdmin.setNickname("");
			schoolSuperAdmin.setPhoneNumber("123456");
			schoolSuperAdmin.setPicture("");
			schoolSuperAdmin.setQqId("");
			schoolSuperAdmin.setWechatId("");
			schoolSuperAdmin.setContactPhone("");
			schoolSuperAdmin.setCampusId(campusId);
			Role role = roleRepository.findByName("ROLE_SCHOOLE_ADMIN");
			schoolSuperAdmin.addRole(role);
			schoolSuperAdmin.setSuperAdmin(false);
			adminRepo.save(schoolSuperAdmin);
			return "success";
		}
	}
	
	public String updateSchoolAdminPassword(String userId,String oldPassword,String newPassword) {
		User currentUser = getUser();
		Role superSystemRole = new Role("ROLE_SYSTEM_SUPER_ADMIN");
		Role systemRole = new Role("ROLE_SYSTEM_ADMIN");
		Role schoolSuperRole = new Role("ROLE_SCHOOLE_SUPER_ADMIN");
		User user = findByUserId(userId);
		if (user == null) {
			return "该用户不存在";
		}
		if (currentUser.hasRole(systemRole)||currentUser.hasRole(superSystemRole)) {
			user.setPassword(passwordEncoder.encode(newPassword));
		}else if(currentUser.hasRole(schoolSuperRole)){
			Admin schoolSuperAdmin = this.findByAdminId(currentUser.getId());
			Admin schoolAdmin = this.findByAdminId(user.getId());
			if(schoolSuperAdmin.getCampusId()==schoolAdmin.getCampusId()){
				user.setPassword(passwordEncoder.encode(newPassword));
			}
		}else if (userId.equals(currentUser.getUserId())) {
			if (passwordEncoder.encode(oldPassword).equals(user.getPassword())) {
				user.setPassword(newPassword);
			} else {
				return "输入密码错误";
			}
		} else {
			return "只有超级管理员和管理员自己才有权限修改系统管理员密码";
		}
		userRepository.save(user);
		return "success";
	}

	@Override
	public String addAdmin(String password) {
		
		Role superRole = new Role();
		superRole.setName("ROLE_SYSTEM_SUPER_ADMIN");
		
		User user = userRepository.findByUserId("admin");
		if (user == null) {
			SystemAdmin systemAdmin = new SystemAdmin();
			systemAdmin.setUserId("admin");
			systemAdmin.setUsername("系统管理员");
			password = passwordEncoder.encode(password);
			systemAdmin.setPassword(password);
			systemAdmin.setAddress("");
			systemAdmin.setBirthday((new Date()).getTime());
			systemAdmin.setAge(1);
			systemAdmin.setEmail("");
			systemAdmin.setGender(GenderType.male);
			systemAdmin.setNickname("");
			systemAdmin.setPhoneNumber("123456");
			systemAdmin.setPicture("");
			systemAdmin.setQqId("");
			systemAdmin.setWechatId("");
			systemAdmin.setContactPhone("");
			systemAdmin.setSuperAdmin(true);

			Role role = roleRepository.findByName("ROLE_SYSTEM_SUPER_ADMIN");
			if (role == null) {
				Role systemRole = new Role();
				systemRole.setName("ROLE_SYSTEM_SUPER_ADMIN");
				systemRole.setDesc("ROLE_SYSTEM_SUPER_ADMIN");
				role = roleRepository.save(systemRole);
			}
			systemAdmin.addRole(role);
			systemAdmin.setSuperAdmin(false);
			systemAdminRepo.save(systemAdmin);
			return "success";
		} else {
			return "该管理员已经存在";
		}
	}
	
}
