package com.syzton.sunread.controller.user;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.controller.util.SecurityContextUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.user.UserDTO;

import com.syzton.sunread.dto.user.UserExtraDTO;

import com.syzton.sunread.model.user.Parent;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.service.bookshelf.BookshelfService;
import com.syzton.sunread.service.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by jerry on 3/9/15.
 */
@Controller
@RequestMapping(value = "/api")
public class UserController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private UserService userService;

    private DefaultTokenServices tokenServices;

    private PasswordEncoder passwordEncoder;

    private ClientDetailsService clientDetailsService;

    private BookshelfService bookshelfService;

    @Resource
    private SecurityContextUtil contextUtil;

    @Autowired
    public void setReviewService(UserService userService, DefaultTokenServices tokenServices,
                                 PasswordEncoder passwordEncoder,
                                 ClientDetailsService clientDetailsService,
                                 BookshelfService bookshelfService) {
        this.userService = userService;
        this.tokenServices = tokenServices;
        this.passwordEncoder = passwordEncoder;
        this.clientDetailsService = clientDetailsService;
        this.bookshelfService = bookshelfService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public UserDTO add(@Valid @RequestBody User user) {
        LOGGER.debug("PASSWORD:" + user.getPassword());
        User insertUser = userService.addUser(user);
        return new UserDTO(insertUser, createTokenForNewUser(
                insertUser.getUserId(), insertUser.getPassword(),
                "353b302c44574f565045687e534e7d6a", "ROLE_USER"));
    }

    @RequestMapping(value = "/user/fromtoken", method = RequestMethod.GET)
    @ResponseBody
    public User add() {
        User user = contextUtil.getUser();
        LOGGER.debug(user.getUserId());
        return user;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteById(@PathVariable("id") Long id) {

        userService.deleteById(id);

    }

    @Secured({"ROLE_USER"})
    @RequestMapping(value = "/users/test/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void test(@PathVariable("id") Long id) {


    }

    @RequestMapping(value = "/tokens/{token}", method = RequestMethod.POST)
    public void verifyToken(@PathVariable("token") String token) {
//        verificationTokenService.verify(token);
//        return Response.ok().build();
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    @ResponseBody
    public Student add(@Valid @RequestBody Student student) {
    	bookshelfService.addBookshelfByStudent(student);
        return userService.addStudent(student);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public User updateUserExtra(@PathVariable("userId") long userId,
                                @Valid @RequestBody UserExtraDTO userExtraDTO) {
        return userService.updateUser(userId, userExtraDTO);
    }

    @RequestMapping(value = "teachers/{teacherId}/students/{studentId}/tasks", method = RequestMethod.PUT)
    @ResponseBody
    public Student add(@PathVariable("teacherId") long teacherId,
                       @PathVariable("studentId") long studentId,
                       @RequestParam("targetBookNum") int targetBookNum,
                       @RequestParam("targetPoint") int targetPoint) {
        Student student = userService.addTask(teacherId, studentId, targetBookNum, targetPoint);
        bookshelfService.addBookshelfByStudent(student);
        return student;
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteByStudentId(@PathVariable("id") Long id) {

        Student student = userService.deleteByStudentId(id);
        bookshelfService.deleteBookshelfByStudent(student);

    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Student findByStudentId(@PathVariable("id") Long id) {
        return userService.findByStudentId(id);
    }

    /*student add parent*/
    //TODO Student role
    @RequestMapping(value = "/students/{id}/parents", method = RequestMethod.POST)
    @ResponseBody
    public Parent add(@Valid @RequestBody Parent parent, @PathVariable("id") Long id) {
        return userService.addParent(parent, id);
    }

    /*parent add student*/
    //TODO Parent role
    @RequestMapping(value = "/parents/{id}/students/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public Parent addChildren(@PathVariable("id") Long id, @PathVariable("userId") String userId) {

        return userService.addChildren(id, userId);
    }


    @RequestMapping(value = "/teachers", method = RequestMethod.POST)
    @ResponseBody
    public Teacher addTeacher(@Valid @RequestBody Teacher teacher) {
        return userService.addTeacher(teacher);
    }

    @RequestMapping(value = "/teachers/{teacherId}", method = RequestMethod.GET)
    @ResponseBody
    public Teacher findByTeacherId(@PathVariable("teacherId") Long teacherId) {
        return userService.findByTeacherId(teacherId);
    }

    @RequestMapping(value = "/teachers/{teacherId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTeacherId(@PathVariable("teacherId") Long teacherId) {
        userService.deleteByTeacherId(teacherId);
    }

    @RequestMapping(value = "/students/hotreaders/{campusId}", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Student> hotReaders(@PathVariable("campusId") Long campusId,
                                            @RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam(value = "sortBy", required = false) String sortBy) {

        Pageable pageable = this.getPageable(page,size,"statistic.testPasses","desc");
        Page<Student> hotReaders = userService.hotReadersInCampus(campusId,pageable);

        return new PageResource<>(hotReaders,"page","size") ;


    }

    private OAuth2AccessToken createTokenForNewUser(String username,
                                                    String password, String clientId, String role) {
        String hashedPassword = passwordEncoder.encode(password);
        UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
                username, hashedPassword,
                Collections.singleton(new SimpleGrantedAuthority(role)));
        ClientDetails authenticatedClient = clientDetailsService
                .loadClientByClientId(clientId);
        OAuth2Request oAuth2Request = createOAuth2Request(null, clientId,
                Collections.singleton(new SimpleGrantedAuthority(role)), true,
                authenticatedClient.getScope(), null, null, null, null);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(
                oAuth2Request, userAuthentication);
        return tokenServices.createAccessToken(oAuth2Authentication);
    }

    private OAuth2Request createOAuth2Request(
            Map<String, String> requestParameters, String clientId,
            Collection<? extends GrantedAuthority> authorities,
            boolean approved, Collection<String> scope,
            Set<String> resourceIds, String redirectUri,
            Set<String> responseTypes,
            Map<String, Serializable> extensionProperties) {
        return new OAuth2Request(requestParameters, clientId, authorities,
                approved, scope == null ? null : new LinkedHashSet<String>(
                scope), resourceIds, redirectUri, responseTypes,
                extensionProperties);
    }

}
