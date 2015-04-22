package com.syzton.sunread.controller.fish;

import com.syzton.sunread.dto.fish.StudentFishDTO;
import com.syzton.sunread.model.fish.Fish;
import com.syzton.sunread.service.fish.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jerry on 4/21/15.
 */
@Controller
@RequestMapping("/api")
public class FishController {

    FishService fishService;

    @Autowired
    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @RequestMapping(value = "/fishes", method = RequestMethod.POST)
    @ResponseBody
    public void addFish(@RequestBody Fish fish) {

        fishService.addFish(fish);

    }

    @RequestMapping(value = "/fishes/{fishId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteFish(@PathVariable("fishId") long fishId) {

        fishService.deleteFish(fishId);

    }

    @RequestMapping(value = "/fishes/{fishId}", method = RequestMethod.PUT)
    @ResponseBody
    public void updateFishPic(@PathVariable("fishId") long fishId, @RequestParam("fishPic") String fishPic) {

        fishService.updateFishPic(fishId, fishPic);

    }

    @RequestMapping(value = "/students/{studentId}/fishes/{fishId}", method = RequestMethod.PUT)
    @ResponseBody
    public void saveOrUpdateStudentFish(@PathVariable("studentId") long studentId, @PathVariable("fishId") long fishId) {

        fishService.saveOrUpdateStudentFish(studentId, fishId);

    }

    @RequestMapping(value = "/fishes/{fishId}", method = RequestMethod.GET)
    @ResponseBody
    public Fish getFish(@PathVariable("fishId") long fishId) {


        return fishService.findFish(fishId);

    }

    @RequestMapping(value = "/fishes", method = RequestMethod.GET)
    @ResponseBody
    public List<Fish> getAllFishes() {

        return fishService.findAllFish();

    }

    @RequestMapping(value = "/clazzs/{clazzId}/fishes", method = RequestMethod.GET)
    @ResponseBody
    public List<StudentFishDTO> getStudentFishDTO(@PathVariable("clazzId") long clazzId) {

        return fishService.getFishStudentDTOsByClazzId(clazzId);

    }


}
