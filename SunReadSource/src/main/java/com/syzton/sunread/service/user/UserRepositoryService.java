package com.syzton.sunread.service.user;

import java.util.Collections;

import com.syzton.sunread.exception.common.AuthenticationException;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;

import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.model.user.*;
import com.syzton.sunread.repository.task.TaskRepository;
import com.syzton.sunread.repository.user.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Autowired
    public UserRepositoryService(UserRepository userRepository,
                                 StudentRepository studentRepository,
                                 ParentRepository parentRepository,
                                 TeacherRepository teacherRepository,
                                 TeacherClazzRepository teacherClazzRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.teacherRepository = teacherRepository;
        this.teacherClazzRepository = teacherClazzRepository;
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
        return studentRepository.save(student);
    }

    @Override
    public Student findByStudentId(Long id) {

        Student student = studentRepository.findOne(id);
        if(student == null)
            throw new NotFoundException("Student id ="+id+" not found..");

        return student;
    }

    @Override
    public void deleteByStudentId(Long id) {
        Student student =  this.findByStudentId(id);
        studentRepository.delete(student);
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return locateUser(username);
    }
    
    @Override
    public User authenticate(String username, String password) {
        User user = locateUser(username);
        if(!passwordEncoder.encode(password).equals(user.getPassword())) {
            throw new AuthenticationException("User "+ username +" password error");
        }
        return user;
    }

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
     * @param username
     * @return a User object is guaranteed.
     * @throws AuthenticationException if user not located.
     */
    private User locateUser(final String username) {
        notNull(username, "Mandatory argument 'username' missing.");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOGGER.debug("Credentials [{}] failed to locate a user.", username.toLowerCase());
            throw new AuthenticationException("User "+username+" didn't exist.");
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

}
