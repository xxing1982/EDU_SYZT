package com.syzton.sunread.service.user;

import java.util.List;

import com.syzton.sunread.dto.user.UserExtraDTO;
import com.syzton.sunread.model.user.Parent;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.model.user.User;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by jerry on 3/9/15.
 */
public interface UserService {

    public User findById(Long id);

    public User addUser(User user);
    
    public User findByUserId(String userId);

    void deleteById(Long id);

    public Student addStudent(Student student);

    public User updateUser(long userId,UserExtraDTO userExtraDTO);

    public Student findByStudentId(Long id);

    public Student deleteByStudentId(Long id);

    public Parent addParent(Parent parent,Long studentId);

    public Parent addChildren(Long id, String userId);

    public Parent findByParentId(Long id);
    
    public User getSingleUser(String userName);

    public Teacher addTeacher(Teacher teacher);

    public Teacher findByTeacherId(Long id);

    void deleteByTeacherId(Long id);
    
    public User authenticate(String username, String password);

    public Student  addTask(long teacherId,long studentId,int targetBookNum,int targetPoint);
    
    public Student saveStudent(Student student);

    public Page<Student> hotReadersInCampus(long campusId,Pageable pageable);

    public Page<Student> hotReadersInClazz(long clazzId,Pageable pageable);

    public Page<Student> hotReadersInGrade(long clazzId,Pageable pageable);
    
    public List<Student> addStudentsFromExcel(Sheet sheet);
    
    public List<Teacher> addTeachersFromExcel(Sheet sheet);
}
