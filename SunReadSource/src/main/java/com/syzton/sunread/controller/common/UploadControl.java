package com.syzton.sunread.controller.common;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.syzton.sunread.exception.common.UpLoadException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
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
import com.syzton.sunread.service.book.BookService;
import com.syzton.sunread.service.exam.ObjectiveQuestionService;
import com.syzton.sunread.service.exam.SubjectiveQuestionService;
import com.syzton.sunread.service.organization.CampusService;
import com.syzton.sunread.service.organization.ClazzService;
import com.syzton.sunread.service.organization.EduGroupService;
import com.syzton.sunread.service.region.RegionService;
import com.syzton.sunread.service.region.SchoolDistrictService;
import com.syzton.sunread.service.user.TeacherClazzService;
import com.syzton.sunread.service.user.UserService;
import com.syzton.sunread.util.FtpUtil;

@Controller
public class UploadControl {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UploadControl.class);

	private SecurityContextUtil securityUtil;

	private BookService bookService;

	private SubjectiveQuestionService subjectQService;

	private ObjectiveQuestionService objectQService;

	private UserService userService;

	private RegionService regionService;

	private EduGroupService eduGroupService;

	private ClazzService clazzService;

	private SchoolDistrictService schoolService;

	private CampusService campusService;
	
	private TeacherClazzService tcService;

	private String ftpPrefix = "/pic";
	private final static String FTP_SERVER_IP = "182.92.238.68";
	private final static int FTP_PORT = 21;
	private final static String FTP_NAME = "syzt";
	private final static String FTP_PASSWD = "5G2kyBefbf";


	@Autowired
	public UploadControl(SecurityContextUtil securityUtil,
			BookService bookService, SubjectiveQuestionService subjectQService,
			ObjectiveQuestionService objectQService, UserService userService,
			RegionService regionService, EduGroupService eduGroupService,
			ClazzService clazzService, SchoolDistrictService schoolService,
			CampusService campusService,TeacherClazzService tcService) {
		this.securityUtil = securityUtil;
		this.bookService = bookService;
		this.subjectQService = subjectQService;
		this.objectQService = objectQService;
		this.userService = userService;
		this.regionService = regionService;
		this.eduGroupService = eduGroupService;
		this.clazzService = clazzService;
		this.schoolService = schoolService;
		this.campusService = campusService;
		this.tcService = tcService;
	}

	@RequestMapping(value = "/api/upload/notepic", method = RequestMethod.POST)
	@ResponseBody
	public String notePicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws IOException {
		String prefix = Calendar.getInstance().getTimeInMillis() + ""
				+ myfile.getOriginalFilename().hashCode();
		int index = myfile.getOriginalFilename().lastIndexOf(".");
		String suffix = "";
		if (index != -1) {
			suffix = myfile.getOriginalFilename().substring(index);
		}
		String ftpPath = "/notes/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {

			try {
				FtpUtil ftpUtil = new FtpUtil(FTP_SERVER_IP,FTP_PORT, FTP_NAME,
						FTP_PASSWD, "/");
				ftpUtil.login();
				ftpUtil.upload(myfile.getInputStream(), ftpPrefix + ftpPath
						+ prefix + suffix);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				throw new UpLoadException("upload note img error");
			}
		}

		return ftpPath + prefix + suffix;
	}

	@RequestMapping(value = "/api/upload/usericon", method = RequestMethod.POST)
	@ResponseBody
	public String userPicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		String prefix = Calendar.getInstance().getTimeInMillis() + ""
				+ myfile.getOriginalFilename().hashCode();
		int index = myfile.getOriginalFilename().lastIndexOf(".");
		String suffix = "";
		if (index != -1) {
			suffix = myfile.getOriginalFilename().substring(index);
		}
		String ftpPath = "/userImages/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {

			try {
				FtpUtil ftpUtil = new FtpUtil(FTP_SERVER_IP,FTP_PORT, FTP_NAME,
						FTP_PASSWD, "/");
				ftpUtil.login();
				ftpUtil.upload(myfile.getInputStream(), ftpPrefix + ftpPath
						+ prefix + suffix);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				throw new UpLoadException("upload user img error");
			}
		}

		return ftpPath + prefix + suffix;
	}

	@RequestMapping(value = "/api/upload/bookpic", method = RequestMethod.POST)
	@ResponseBody
	public String bookPicUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		String prefix = Calendar.getInstance().getTimeInMillis() + ""
				+ myfile.getOriginalFilename().hashCode();
		int index = myfile.getOriginalFilename().lastIndexOf(".");
		String suffix = "";
		if (index != -1) {
			suffix = myfile.getOriginalFilename().substring(index);
		}
		String ftpPath = "/bookscover/";
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			try {
				FtpUtil ftpUtil = new FtpUtil(FTP_SERVER_IP,FTP_PORT, FTP_NAME,
						FTP_PASSWD, "/");
				ftpUtil.login();
				ftpUtil.upload(myfile.getInputStream(), ftpPrefix + ftpPath
						+ prefix + suffix);
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				throw new UpLoadException("upload book cover img error");
			}
		}

		return ftpPath + prefix + suffix;
	}

	@RequestMapping(value = "/api/upload/excel/book", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> bookExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = bookService.batchSaveOrUpdateBookFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}

	@RequestMapping(value = "/api/upload/excel/objectquestion", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> objectiveQuestionExcelUpload(
			@RequestParam MultipartFile myfile, HttpServletRequest request)
			throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = objectQService.batchSaveOrUpdateObjectiveQuestionFromExcel(wb
					.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}

	@RequestMapping(value = "/api/upload/excel/subjectivequestion", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> subjectiveQuestionExcelUpload(
			@RequestParam MultipartFile myfile, HttpServletRequest request)
			throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = subjectQService.batchSaveOrUpdateSubjectQuestionFromExcel(wb
					.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}

	@RequestMapping(value = "/api/upload/excel/student", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> studentExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = userService.batchSaveOrUpdateStudentFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}
	//@RequestMapping(value = "/api/upload/excel/admin", method = RequestMethod.POST)
	//@ResponseBody
	//public Map<Integer,String> adminExcelUpload(@RequestParam MultipartFile myfile,
	//											  HttpServletRequest request) throws Exception {
	//	Map<Integer,String> map = new HashMap<Integer,String>();
	//	if (myfile.isEmpty()) {
	//		throw new RuntimeException("File is empty");
	//	} else {
	//		Workbook wb = getWorkBookFromExcel(myfile);
	//		map = userService.batchSaveOrUpdateCMSAdminFromExcel(wb.getSheetAt(0));
	//		wb.close();
	//	}
	//	map.put(0, "parser Excel complete");
	//	return map;
	//}

	@RequestMapping(value = "/api/upload/excel/teacher", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> teacherExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = userService.batchSaveOrUpdateTeacherFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}
	
	@RequestMapping(value = "/api/upload/excel/teacherclazz", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> tcRelationExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = tcService.batchSaveOrUpdateTeacherFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}

	@RequestMapping(value = "/api/upload/excel/region", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> regionExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = regionService.batchSaveOrUpdateRegionFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}
	
	@RequestMapping(value = "/api/upload/excel/edugroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> edugroupExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = eduGroupService.batchSaveOrUpdateEduGroupFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}
	
	@RequestMapping(value = "/api/upload/excel/school", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> schoolExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = schoolService.batchSaveOrUpdateSchoolFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}
	
	@RequestMapping(value = "/api/upload/excel/campus", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> campusExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = campusService.batchSaveOrUpdateCampusFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}
	
	@RequestMapping(value = "/api/upload/excel/clazz", method = RequestMethod.POST)
	@ResponseBody
	public Map<Integer,String> clazzExcelUpload(@RequestParam MultipartFile myfile,
			HttpServletRequest request) throws Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		if (myfile.isEmpty()) {
			throw new RuntimeException("File is empty");
		} else {
			Workbook wb = getWorkBookFromExcel(myfile);
			map = clazzService.batchSaveOrUpdateClazzFromExcel(wb.getSheetAt(0));
			wb.close();
		}
		map.put(0, "parser Excel complete");
		return map;
	}

	private Workbook getWorkBookFromExcel(MultipartFile myfile)
			throws IOException {
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
