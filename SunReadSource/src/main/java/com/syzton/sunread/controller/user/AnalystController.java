package com.syzton.sunread.controller.user;

import javax.validation.Valid;

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
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.user.Analyst;
import com.syzton.sunread.service.user.AnalystService;

/**
 * Created by jerry on 6/14/15.
 */
@Controller
@RequestMapping(value = "/api")
public class AnalystController extends BaseController {

    private AnalystService analystService;

    @Autowired
    public AnalystController(AnalystService analystService) {
        this.analystService = analystService;
    }

    @RequestMapping(value = "/analysts", method = RequestMethod.POST)
    @ResponseBody
    private void add(@Valid @RequestBody Analyst analyst) {

        analystService.add(analyst);

    }

    @RequestMapping(value = "/analysts/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    private void delete(@PathVariable("id") Long id) {

        analystService.delete(id);

    }

    @RequestMapping(value = "/analysts/{userId}", method = RequestMethod.GET)
    @ResponseBody
    private void findByUserId(@PathVariable("userId") String userId) {

        analystService.findByUserId(userId);

    }

    @RequestMapping(value = "/analysts/campuses/{campusId}", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Analyst> analystsInCampus(@PathVariable("campusId") Long campusId,
                                                @RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                @RequestParam(value = "sortBy", required = false) String sortBy,
                                                @RequestParam(value = "direction", required = false) String direction) {
        sortBy = sortBy == null ? "id" : sortBy;

        Pageable pageable = this.getPageable(page,size,sortBy,direction);
        Page<Analyst> analysts = analystService.findByCampusId(pageable,campusId);

        return new PageResource<>(analysts,"page","size") ;


    }

    @RequestMapping(value = "/analysts/edugroups/{eduGroupId}", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Analyst> analystsInEduGroup(@PathVariable("eduGroupId") Long eduGroupId,
                                                @RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                @RequestParam(value = "sortBy", required = false) String sortBy,
                                                @RequestParam(value = "direction", required = false) String direction) {
        sortBy = sortBy == null ? "id" : sortBy;

        Pageable pageable = this.getPageable(page,size,sortBy,direction);
        Page<Analyst> analysts = analystService.findByEduGroupId(pageable,eduGroupId);

        return new PageResource<>(analysts,"page","size") ;


    }

    @RequestMapping(value = "/analysts/regions/{regionId}", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Analyst> analystsInRegion(@PathVariable("regionId") Long regionId,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size,
                                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                                    @RequestParam(value = "direction", required = false) String direction) {
        sortBy = sortBy == null ? "id" : sortBy;

        Pageable pageable = this.getPageable(page,size,sortBy,direction);
        Page<Analyst> analysts = analystService.findByRegionId(pageable,regionId);

        return new PageResource<>(analysts,"page","size") ;


    }

    @RequestMapping(value = "/analysts/sdistricts/{districtId}", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Analyst> analystsInDistrict(@PathVariable("districtId") Long districtId,
                                                  @RequestParam("page") int page,
                                                  @RequestParam("size") int size,
                                                  @RequestParam(value = "sortBy", required = false) String sortBy,
                                                  @RequestParam(value = "direction", required = false) String direction) {
        sortBy = sortBy == null ? "id" : sortBy;

        Pageable pageable = this.getPageable(page,size,sortBy,direction);
        Page<Analyst> analysts = analystService.findBySchoolDistrictId(pageable,districtId);

        return new PageResource<>(analysts,"page","size") ;


    }



}
