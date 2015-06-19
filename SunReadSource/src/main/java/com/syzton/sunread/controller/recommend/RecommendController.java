/**
 * 
 */
package com.syzton.sunread.controller.recommend;

import java.util.ArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.controller.book.BookController;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.recommend.RecommendDTO;
import com.syzton.sunread.service.recommend.RecommendService;

/**
 * @author Morgan-Leon
 * @Date 2015年5月12日
 * 
 */
@Controller
@RequestMapping(value = "/api")
public class RecommendController extends BaseController{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private RecommendService recommendService;
    
    @Autowired
    public RecommendController(RecommendService recommendService){
    	this.recommendService = recommendService;
    }

    @RequestMapping(value = "/teacher/{teacherId}/student/{studentId}/recommend", method = RequestMethod.POST)
    @ResponseBody
    public RecommendDTO add(@Valid @RequestBody RecommendDTO recommendDTO
    		,@PathVariable("teacherId") long teacherId,@PathVariable("studentId") long studentId) {
        LOGGER.debug("Adding a new book entry with information: {}", recommendDTO);

        RecommendDTO added = recommendService.add(recommendDTO,teacherId,studentId);

        LOGGER.debug("Added a book entry with information: {}", added);

        return added;
    }
    
    @RequestMapping(value = "/teacher/{teacherId}/clazz/{clazzId}/recommends", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<RecommendDTO> addToClazz(@Valid @RequestBody RecommendDTO recommendDTO
    			,@PathVariable("teacherId") long teacherId,@PathVariable("clazzId") long clazzId){
    	
    	return recommendService.addToClazz(recommendDTO, teacherId, clazzId);
    }

    
    @RequestMapping(value = "/teacher/{teacherId}/recommends", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<RecommendDTO> getExchanges(@PathVariable("teacherId") long teacherId,
                                       @RequestParam("page") int page,
                                       @RequestParam("size") int size,
                                       @RequestParam(value = "sortBy",required = false) String sortBy) {
    	sortBy = sortBy ==null ? "creationTime" : sortBy;
    	
        Pageable pageable = this.getPageable(page,size,sortBy,"desc");

        Page<RecommendDTO> recommendByTeacher = recommendService.findByTeacher(teacherId,pageable);

        return new PageResource<>(recommendByTeacher,"page","size");

    }
    
    
    
    
}
