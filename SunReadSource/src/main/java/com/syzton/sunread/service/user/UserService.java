package com.syzton.sunread.service.user;

import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.user.UserExtraDTO;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.model.user.Admin;
import com.syzton.sunread.model.user.Parent;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.SystemAdmin;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.model.user.User;

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
    
    public Teacher saveTeacher(Teacher teacher);

    public Teacher findByTeacherId(Long id);

    void deleteByTeacherId(Long id);
    
    public User authenticate(String username, String password);

    public Task addTask(long teacherId,long studentId,int targetBookNum,int targetPoint);

	public void addTasks(long teacherId, int targetBookNum, int targetPoint);
    
    public Student saveStudent(Student student);

    public Page<Student> hotReadersInCampus(long campusId,Pageable pageable);

    public Page<Student> hotReadersInClazz(long clazzId,Pageable pageable);

    public Page<Student> hotReadersInGrade(long clazzId,Pageable pageable);


    public Page<Teacher> findTeacherByCampusId(long campusId,Pageable pageable);


    public Map<Integer,String> batchSaveOrUpdateStudentFromExcel(Sheet sheet);

    
    public Map<Integer,String> batchSaveOrUpdateTeacherFromExcel(Sheet sheet);

    public Map<Integer,String> batchSaveOrUpdateCMSAdminFromExcel(Sheet sheet);
    
    public String updateSuperAdminPassword(String newPassword, String oldPassword);
    
    public String addSystemAdmin(String userId, String password);
    
    public String updateSystemAdmin(String userId, String oldPassword,
			String newPassword);
    public Page<SystemAdmin> getSystemAdmins(boolean isSuperAdmin,Pageable pageable);
    
    public String deleteSystemAdminId(Long id);
    
    public String addAdmin(String password);
    
    public SystemAdmin findBySystemAdminId(Long id);
    
    public Admin findByAdminId(Long id);
    
    public String deleteAdminId(Long id);
    
    public String addSchoolSuperAdmin(String userId, String password,long campusId);
    
    public String updateSchoolSuperAdminPassword(String userId,String oldPassword,String newPassword);
    
    public String addSchoolAdmin(String userId, String password,long campusId);
    
    public String updateSchoolAdminPassword(String userId,String oldPassword,String newPassword);
    
    public Page<Admin> getSchoolAdmins(long campusId,boolean isSuperAdmin,Pageable pageable);
	
	public Page<Admin> getAllSchoolAdmins(long campusId,Pageable pageable);
	
	public String addSuperSystemAdmin(String userId, String password);

	Page<Teacher> searchTeachersByName(String name, Pageable pageable);


}
