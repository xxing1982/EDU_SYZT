package com.syzton.sunread.service.fish;

import java.util.List;

import com.syzton.sunread.dto.fish.StudentFishDTO;
import com.syzton.sunread.model.fish.Fish;

/**
 * Created by jerry on 4/21/15.
 */
public interface FishService {

    public void addFish(Fish fish);

    public void deleteFish(Long fishId);

    public void updateFishPic(Long fishId,String fishPic);

    public Fish findFish(long fishId);

    public List<Fish> findAllFish();

    public List<StudentFishDTO> getFishStudentDTOsByClazzId(long clazzId);

    public void saveOrUpdateStudentFish(long studentId, long fishId);

    public Fish findFishByStudentId(long studentId);

}
