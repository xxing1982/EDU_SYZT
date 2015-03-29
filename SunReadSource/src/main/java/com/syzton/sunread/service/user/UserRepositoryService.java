package com.syzton.sunread.service.user;

import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.user.*;
import com.syzton.sunread.repository.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.Assert.notNull;

/**
 * Created by jerry on 3/16/15.
 */
@Service
public class UserRepositoryService implements UserService,UserDetailsService{

    private UserRepository userRepository;
    
    private PasswordEncoder passwordEncoder;

    private StudentRepository studentRepository;

    private ParentRepository parentRepository;

    private TeacherRepository teacherRepository;

    private TeacherClazzRepository teacherClazzRepository;

    @Autowired
    public UserRepositoryService(UserRepository userRepository,
                                 StudentRepository studentRepository,
                                 ParentRepository parentRepository,
                                 TeacherRepository teacherRepository,
                                 TeacherClazzRepository teacherClazzRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.teacherRepository = teacherRepository;
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
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
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
    
    /**
     * Locate the user and throw an exception if not found.
     *
     * @param username
     * @return a User object is guaranteed.
     * @throws AuthenticationException if user not located.
     */
    private User locateUser(final String username) {
        notNull(username, "Mandatory argument 'username' missing.");
        User user = userRepository.findByUsername(username.toLowerCase());
        if (user == null) {
//            LOG.debug("Credentials [{}] failed to locate a user.", username.toLowerCase());
//            throw new UsernameNotFoundException("failed to locate a user");
        }
        return user;
    }

	@Override
	public User getSingleUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
    @Transactional
    @Override
    public Teacher addTeacher(Teacher teacher) {
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
