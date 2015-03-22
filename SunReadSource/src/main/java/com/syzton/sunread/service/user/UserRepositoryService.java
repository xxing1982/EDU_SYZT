package com.syzton.sunread.service.user;

import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.user.Parent;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.user.ParentRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import com.syzton.sunread.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jerry on 3/16/15.
 */
@Service
public class UserRepositoryService implements UserService{

    private UserRepository userRepository;

    private StudentRepository studentRepository;

    private ParentRepository parentRepository;

    @Autowired
    public UserRepositoryService(UserRepository userRepository, StudentRepository studentRepository, ParentRepository parentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
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
        parent.getStudentSet().add(student);

        return parentRepository.save(parent);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public Parent addChildren(Long id, Long userId) {

        Parent parent = this.findByParentId(id);

        Student student = studentRepository.findByUserId(userId);
        if(student == null)
            throw new NotFoundException("student with id = "+userId+" not found..");

        parent.getStudentSet().add(student);

        return parentRepository.save(parent);

    }

    @Override
    public Parent findByParentId(Long id) {

        Parent parent = parentRepository.findOne(id);
        if(parent == null)
            throw new NotFoundException("parent with id = "+ id +" not found..");

        return parent;
    }
}
