package com.syzton.sunread.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.user.Admin;
import com.syzton.sunread.model.user.SystemAdmin;
import com.syzton.sunread.service.user.UserService;

@Controller
@RequestMapping(value = "/api")
public class AdminController extends BaseController {
	private UserService userService;
	
	@Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }
	
	@RequestMapping(value = "/admin", method = RequestMethod.POST)
    @ResponseBody
    public String addRootSystem(
			@RequestParam("password") String password) {
		return userService.addAdmin(password);
    }
	
	@RequestMapping(value = "/systemadmin", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestParam("userid") String userId,
			@RequestParam("password") String password) {
		return userService.addSystemAdmin(userId, password);
    }
	
	@RequestMapping(value = "/superschooladmin", method = RequestMethod.POST)
    @ResponseBody
    public String addSchoolSuperAdmin(@RequestParam("campusid") long campusId,@RequestParam("userid") String userId,
			@RequestParam("password") String password) {
		return userService.addSchoolSuperAdmin(userId, password, campusId);
    }
	
	@RequestMapping(value = "/schooladmin", method = RequestMethod.POST)
    @ResponseBody
    public String addSchoolAdmin(@RequestParam("campusid") long campusId,@RequestParam("userid") String userId,
			@RequestParam("password") String password) {
		return userService.addSchoolAdmin(userId, password, campusId);
    }
	
	@RequestMapping(value = "/systemadmin", method = RequestMethod.PUT)
    @ResponseBody
    public String update(@RequestParam("userid") String userId,
			@RequestParam("oldpassword") String oldPassword,@RequestParam("newpassword") String newPassword) {
        return userService.updateSystemAdmin(userId, oldPassword, newPassword);
    }
	
	@RequestMapping(value = "/supersystemadmin", method = RequestMethod.PUT)
    @ResponseBody
    public String update(
			@RequestParam("oldpassword") String oldPassword,@RequestParam("newpassword") String newPassword) {
        return userService.updateSuperAdminPassword(newPassword, oldPassword);
    }
	
	@RequestMapping(value = "/superschooladmin", method = RequestMethod.PUT)
    @ResponseBody
    public String updateSchoolSuperAdmin(@RequestParam("userid") String userId,
			@RequestParam("oldpassword") String oldPassword,@RequestParam("newpassword") String newPassword) {
        return userService.updateSchoolSuperAdminPassword(userId, oldPassword, newPassword);
    }
	
	@RequestMapping(value = "/schooladmin", method = RequestMethod.PUT)
    @ResponseBody
    public String updateSchoolAdmin(@RequestParam("userid") String userId,
			@RequestParam("oldpassword") String oldPassword,@RequestParam("newpassword") String newPassword) {
        return userService.updateSchoolAdminPassword(userId, oldPassword, newPassword);
    }
	
	@RequestMapping(value = "/systemadmin", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<SystemAdmin> get(@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("sortBy") String sortBy) {
		sortBy = sortBy == null ? "id" : sortBy;
		Pageable pageable = new PageRequest(page, size, new Sort(sortBy));
		Page<SystemAdmin> pageResult = userService.getSystemAdmins(false, pageable);
        return new PageResource<>(pageResult, "page", "size");
    }
	
	@RequestMapping(value = "/allschooladmins", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Admin> getAllSchoolAdmin(@RequestParam("campusid") long campusId,@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("sortBy") String sortBy) {
		sortBy = sortBy == null ? "id" : sortBy;
		Pageable pageable = new PageRequest(page, size, new Sort(sortBy));
		Page<Admin> pageResult = userService.getAllSchoolAdmins(campusId,pageable);
        return new PageResource<>(pageResult, "page", "size");
    }
	
	@RequestMapping(value = "/superschooladmins", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Admin> getSpuerSchoolAdmins(@RequestParam("campusid") long campusId,@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("sortBy") String sortBy) {
		sortBy = sortBy == null ? "id" : sortBy;
		Pageable pageable = new PageRequest(page, size, new Sort(sortBy));
		Page<Admin> pageResult = userService.getSchoolAdmins(campusId,true,pageable);
        return new PageResource<>(pageResult, "page", "size");
    }
	
	@RequestMapping(value = "/schooladmins", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Admin> getSchoolAdmins(@RequestParam("campusid") long campusId,@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("sortBy") String sortBy) {
		sortBy = sortBy == null ? "id" : sortBy;
		Pageable pageable = new PageRequest(page, size, new Sort(sortBy));
		Page<Admin> pageResult = userService.getSchoolAdmins(campusId,false,pageable);
        return new PageResource<>(pageResult, "page", "size");
    }
	
	@RequestMapping(value = "/systemadmin", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestParam("id") long id) {
        return userService.deleteSystemAdminId(id);
    }
	
	@RequestMapping(value = "/schooladmin", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteSchoolAdmin(@RequestParam("id") long id) {
        return userService.deleteAdminId(id);
    }
	
	@RequestMapping(value = "/superschooladmin", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteSuperSchoolAdmin(@RequestParam("id") long id) {
        return userService.deleteAdminId(id);
    }
	
	 
}
