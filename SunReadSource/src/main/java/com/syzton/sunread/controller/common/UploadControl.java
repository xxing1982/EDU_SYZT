package com.syzton.sunread.controller.common;

import java.io.File;  
import java.io.IOException;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;  

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;  
import org.springframework.ui.ModelMap;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;  
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;  

import com.syzton.sunread.controller.note.CommentController;
import com.syzton.sunread.util.FtpUtil;
  
@Controller  
public class UploadControl {  
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadControl.class);
	
    @RequestMapping(value = "/userpicture", method = RequestMethod.POST)  
    @ResponseBody
    public String userPicUpload(@RequestParam MultipartFile  myfile, HttpServletRequest request) throws IOException {  
            if(myfile.isEmpty()){  
                throw new RuntimeException("File is empty"); 
            }else{  
                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));  
            }  
          
        //return ftp path;
        return "file upload success";  
    }  
    
    @RequestMapping(value = "/usericonupload",  method = RequestMethod.POST)  
    @ResponseBody
    public String bookPicUpload(@RequestParam MultipartFile  myfile, HttpServletRequest request) throws Exception {  
            if(myfile.isEmpty()){  
                throw new RuntimeException("File is empty"); 
            }else{  
                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
                LOGGER.debug(realPath);
                String path = realPath + myfile.getOriginalFilename();
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));  
                FtpUtil ftpUtil = new FtpUtil("182.92.238.68", 21, "syzt", "syzt2015", "/pic/userImages/");
                ftpUtil.login();
                ftpUtil.upload(path, myfile.getOriginalFilename());
                String[] strs = ftpUtil.listFiles("/pic/userImages/");
                LOGGER.debug(strs.toString());;
            }  
          
        return "file upload success";  
    } 
    
    @RequestMapping(value = "/upload")  
    @ResponseBody
    public String upload(@RequestParam MultipartFile[] myfiles, HttpServletRequest request) throws IOException {  
    
        for(MultipartFile myfile : myfiles){  
            if(myfile.isEmpty()){  
                System.out.println("文件未上传");  
            }else{  
                System.out.println("文件长度: " + myfile.getSize());  
                System.out.println("文件类型: " + myfile.getContentType());  
                System.out.println("文件名称: " + myfile.getName());  
                System.out.println("文件原名: " + myfile.getOriginalFilename());  
                System.out.println("========================================");  
                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));  
            }  
        }  
     
        return "redirect:/user/list";  
    }
  
}  
