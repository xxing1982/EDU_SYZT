package com.syzton.sunread.service.user;

import com.syzton.sunread.dto.user.UserExtraDTO;
import com.syzton.sunread.exception.common.AuthenticationException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.model.user.*;
import com.syzton.sunread.model.user.User.GenderType;
import com.syzton.sunread.repository.SemesterRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.user.*;

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
import java.util.Date;
import java.util.List;

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
    


    @Autowired
    public UserRepositoryService(UserRepository userRepository,
                                 StudentRepository studentRepository,
                                 ParentRepository parentRepository,
                                 TeacherRepository teacherRepository,
                                 TeacherClazzRepository teacherClazzRepository,
                                 SemesterRepository semesterRepository,
                                 ClazzRepository clazzRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.teacherRepository = teacherRepository;
        this.teacherClazzRepository = teacherClazzRepository;
        this.semesterRepository = semesterRepository;
        this.clazzRepository = clazzRepository;
        this.passwordEncoder = passwordEncoder;

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
        Clazz clazz = clazzRepository.findOne(student.getClazzId());
        if(clazz == null){
            throw new NotFoundException("clazz id = "+student.getClazzId()+" not found..");
        }
        clazz.getClazzStatistic().increaseStudentNum();
        clazzRepository.save(clazz);
        
        return studentRepository.save(student);
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

        Task task = new Task();

        task.setTargetBookNum(targetBookNum);
        task.setTargetPoint(targetPoint);
        task.setTeacherId(teacherId);

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
        for (Long clazzId: teacher.getClazzIds()){
            TeacherClazz teacherClazz = new TeacherClazz();
            teacherClazz.setClazzId(clazzId);
            teacherClazz.setTeacherId(teacher.getId());
            teacherClazzRepository.save(teacherClazz);

        }
        return teacher ;
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
	public User findByUserId(String userId) {
		User user = userRepository.findByUserId(userId);
		return user;
	}

	@Override
	public List<Student> addStudentsFromExcel(Sheet sheet) {
		
		for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {  
		    Student student = new Student();
			Row row = sheet.getRow(i);  
		    student.setUserId(row.getCell(0).toString());
		    student.setUsername(row.getCell(1).toString());
		    //Can't handle grade, repository must provide method to get grade by name & school
		    //Excel can't found school campus
		    //student.setGradeId(gradeId);
		    //student.setClazzId(clazzId);
		    //unknow sexy field value
		    String sex = row.getCell(4).toString();
		    if(sex.equals("女")){
		    	student.setGender(GenderType.famale);
		    }else{
		    	student.setGender(GenderType.male);
		    }
		   
			try {
				Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(row.getCell(5).toString());
				student.setBirthday(birthday.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			student.setEmail(row.getCell(6).toString());
			//Contact phone ? phone
			student.setPhoneNumber(row.getCell(6).toString());
			
			
		      
		 
		}  
		return null;
	}

	@Override
	public List<Teacher> addTeachersFromExcel(Sheet sheet) {
		// TODO Auto-generated method stub
		return null;
	}

}
