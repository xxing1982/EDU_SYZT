package com.syzton.sunread.controller.common;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.syzton.sunread.controller.util.SecurityContextUtil;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.util.FtpUtil;

@Controller
public class UploadControl {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UploadControl.class);
	
	private SecurityContextUtil securityUtil;
	
	@Autowired
	public UploadControl(SecurityContextUtil securityUtil) {
		this.securityUtil = securityUtil;
	}

	@RequestMapping(value = "/api/upload/notepic", method = RequestMethod.POST)
	@ResponseBody
	public String notePicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws IOException {
		long prefix = Calendar.getInstance().getTimeInMillis();
		String fileName = prefix + myfile.getOriginalFilename();
		String ftpPath = "/notes/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {

			String realPath = request.getSession().getServletContext()
					.getRealPath("/upload/note");
			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
					realPath, prefix + myfile.getOriginalFilename()));
			try {
				FtpUtil ftpUtil = new FtpUtil("182.92.238.68", 21, "syzt",
						"syzt2015", "/");
				ftpUtil.login();
				ftpUtil.upload(realPath + "/" + fileName, ftpPath + fileName);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				return "upload file to image sever error";
			}
		}

		return ftpPath + fileName;
	}

	@RequestMapping(value = "/api/upload/usericon", method = RequestMethod.POST)
	@ResponseBody
	public String userPicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		long prefix = Calendar.getInstance().getTimeInMillis();
		String fileName = prefix + myfile.getOriginalFilename();
		String ftpPath = "/userImages/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			String realPath = request.getSession().getServletContext()
					.getRealPath("/upload/usericon");
			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
					realPath, prefix + myfile.getOriginalFilename()));
			try {
				FtpUtil ftpUtil = new FtpUtil("182.92.238.68", 21, "syzt",
						"syzt2015", "/");
				ftpUtil.login();
				ftpUtil.upload(realPath + "/" + fileName, ftpPath + fileName);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				return "upload file to image sever error";
			}
		}

		return ftpPath + fileName;
	}

	@RequestMapping(value = "/api/upload/bookpic", method = RequestMethod.POST)
	@ResponseBody
	public String bookPicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		long prefix = Calendar.getInstance().getTimeInMillis();
		String originalFileName = new String(myfile.getOriginalFilename().getBytes("iso-8859-1"),"utf-8");
		String fileName = prefix + originalFileName;
		String ftpPath = "/bookscover/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			String realPath = request.getSession().getServletContext()
					.getRealPath("/upload/bookpic");

			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
					realPath, prefix + myfile.getOriginalFilename()));

			try {
				FtpUtil ftpUtil = new FtpUtil("182.92.238.68", 21, "syzt",
						"syzt2015", "/");
				ftpUtil.login();
				ftpUtil.upload(realPath + "/" + fileName, ftpPath + fileName);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				return "upload file to image sever error";
			}
		}
		return ftpPath + fileName;
	}

	@RequestMapping(value = "/api/upload/excel/book", method = RequestMethod.POST)
	@ResponseBody
	public String bookExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		
		String fileName = myfile.getOriginalFilename();

		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			if (fileName.endsWith(".xls")) {
				HSSFWorkbook wb = new HSSFWorkbook(myfile.getInputStream());
				HSSFSheet sheet = wb.getSheetAt(0);
			} else if (fileName.endsWith(".xlsx")) {
				XSSFWorkbook xwb = new XSSFWorkbook(myfile.getInputStream());
				XSSFSheet sheet = xwb.getSheetAt(0);
			}

		}
		return "Excel parse OK";
	}
	
	@RequestMapping(value = "/api/upload/excel/student", method = RequestMethod.POST)
	@ResponseBody
	public String studentExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		
		User user = securityUtil.getUser();
		
		String fileName = myfile.getOriginalFilename();

		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			if (fileName.endsWith(".xls")) {
				HSSFWorkbook wb = new HSSFWorkbook(myfile.getInputStream());
				HSSFSheet sheet = wb.getSheetAt(0);
			} else if (fileName.endsWith(".xlsx")) {
				XSSFWorkbook xwb = new XSSFWorkbook(myfile.getInputStream());
				XSSFSheet sheet = xwb.getSheetAt(0);
			}

		}
		return "Excel parse OK";
	}

}
