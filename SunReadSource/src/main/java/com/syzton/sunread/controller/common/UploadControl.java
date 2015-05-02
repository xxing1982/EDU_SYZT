package com.syzton.sunread.controller.common;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.syzton.sunread.service.book.BookService;
import com.syzton.sunread.service.exam.ObjectiveQuestionService;
import com.syzton.sunread.service.exam.SubjectiveQuestionService;
import com.syzton.sunread.util.FtpUtil;

@Controller
public class UploadControl {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UploadControl.class);
	
	private SecurityContextUtil securityUtil;
	
	private BookService bookService;
	
	private SubjectiveQuestionService subjectQService;
	
	private ObjectiveQuestionService objectQService;
	
	private String ftpPrefix = "/pic";
	
	@Autowired
	public UploadControl(SecurityContextUtil securityUtil,BookService bookService,SubjectiveQuestionService subjectQService,ObjectiveQuestionService objectQService) {
		this.securityUtil = securityUtil;
		this.bookService = bookService;
		this.subjectQService = subjectQService;
		this.objectQService = objectQService;
	}

	@RequestMapping(value = "/api/upload/notepic", method = RequestMethod.POST)
	@ResponseBody
	public String notePicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws IOException {
		String prefix = Calendar.getInstance().getTimeInMillis()+ "" +myfile.getOriginalFilename().hashCode();
		int index = myfile.getOriginalFilename().lastIndexOf(".");
		String suffix = "";
		if(index!=-1){
			suffix = myfile.getOriginalFilename().substring(index); 
		}
		String ftpPath = "/notes/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {

			String realPath = request.getSession().getServletContext()
					.getRealPath("/upload/note");
//			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
//					realPath, prefix + myfile.getOriginalFilename()).hashCode()+suffix);
			try {
				FtpUtil ftpUtil = new FtpUtil("182.92.238.68", 21, "syzt",
						"syzt2015", "/");
				ftpUtil.login();
				ftpUtil.upload(myfile.getInputStream(), ftpPrefix + ftpPath + prefix + suffix);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				return "upload file to image sever error";
			}
		}

		return ftpPath + prefix + suffix;
	}

	@RequestMapping(value = "/api/upload/usericon", method = RequestMethod.POST)
	@ResponseBody
	public String userPicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		String prefix = Calendar.getInstance().getTimeInMillis()+ "" +myfile.getOriginalFilename().hashCode();
		int index = myfile.getOriginalFilename().lastIndexOf(".");
		String suffix = "";
		if(index!=-1){
			suffix = myfile.getOriginalFilename().substring(index); 
		}
		String ftpPath = "/userImages/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {

			String realPath = request.getSession().getServletContext()
					.getRealPath("/upload/note");
//			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
//					realPath, prefix + myfile.getOriginalFilename()).hashCode()+suffix);
			try {
				FtpUtil ftpUtil = new FtpUtil("182.92.238.68", 21, "syzt",
						"syzt2015", "/");
				ftpUtil.login();
				ftpUtil.upload(myfile.getInputStream(), ftpPrefix + ftpPath + prefix + suffix);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				return "upload file to image sever error";
			}
		}

		return ftpPath + prefix + suffix;
	}

	@RequestMapping(value = "/api/upload/bookpic", method = RequestMethod.POST)
	@ResponseBody
	public String bookPicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		String prefix = Calendar.getInstance().getTimeInMillis()+ "" +myfile.getOriginalFilename().hashCode();
		int index = myfile.getOriginalFilename().lastIndexOf(".");
		String suffix = "";
		if(index!=-1){
			suffix = myfile.getOriginalFilename().substring(index); 
		}
		String ftpPath = "/bookscover/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {

			String realPath = request.getSession().getServletContext()
					.getRealPath("/upload/note");
//			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
//					realPath, prefix + myfile.getOriginalFilename()).hashCode()+suffix);
			try {
				FtpUtil ftpUtil = new FtpUtil("182.92.238.68", 21, "syzt",
						"syzt2015", "/");
				ftpUtil.login();
				ftpUtil.upload(myfile.getInputStream(), ftpPrefix + ftpPath + prefix + suffix);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				return "upload file to image sever error";
			}
		}

		return ftpPath + prefix + suffix;
	}

	@RequestMapping(value = "/api/upload/excel/book", method = RequestMethod.POST)
	@ResponseBody
	public String bookExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			bookService.batchSaveOrUpdateBookFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		return "Excel parse OK";
	}
	
	@RequestMapping(value = "/api/upload/excel/objectquestion", method = RequestMethod.POST)
	@ResponseBody
	public String objectiveQuestionExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			objectQService.batchSaveOrUpdateObjectiveQuestionFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		return "Excel parse OK";
	}
	
	@RequestMapping(value = "/api/upload/excel/subjectivequestion", method = RequestMethod.POST)
	@ResponseBody
	public String subjectiveQuestionExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			subjectQService.batchSaveOrUpdateSubjectQuestionFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		return "Excel parse OK";
	}
	
	@RequestMapping(value = "/api/upload/excel/student", method = RequestMethod.POST)
	@ResponseBody
	public String studentExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {

		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			
		}
		return "Excel parse OK";
	}
	
	private Workbook getWorkBookFromExcel(MultipartFile myfile) throws IOException{
		String fileName = myfile.getOriginalFilename();
		Workbook wb = null;
		if (fileName.endsWith(".xls")) {
			wb = new HSSFWorkbook(myfile.getInputStream());
		} else if (fileName.endsWith(".xlsx")) {
			wb = new XSSFWorkbook(myfile.getInputStream());
		}
		return wb;
	}
}
