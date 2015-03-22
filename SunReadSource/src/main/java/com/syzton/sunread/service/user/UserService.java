package com.syzton.sunread.service.user;

import com.syzton.sunread.model.user.Parent;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;

/**
 * Created by jerry on 3/9/15.
 */
public interface UserService {

    public User findById(Long id);

    public User addUser(User user);

    void deleteById(Long id);

    public Student addStudent(Student student);

    public Student findByStudentId(Long id);

    void deleteByStudentId(Long id);

    public Parent addParent(Parent parent,Long studentId);

    public Parent addChildren(Long id, Long userId);

    public Parent findByParentId(Long id);

}
