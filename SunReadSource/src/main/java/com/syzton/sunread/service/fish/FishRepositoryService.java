package com.syzton.sunread.service.fish;

import com.syzton.sunread.dto.fish.StudentFishDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.fish.Fish;
import com.syzton.sunread.model.fish.StudentFish;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.fish.FishRepository;
import com.syzton.sunread.repository.fish.StudentFishRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jerry on 4/21/15.
 */
@Service
public class FishRepositoryService implements FishService{

    private FishRepository fishRepository;

    private StudentRepository studentRepository;

    private StudentFishRepository studentFishRepository;

    private ClazzRepository clazzRepository;
    @Autowired
    public FishRepositoryService(FishRepository fishRepository, StudentRepository studentRepository, StudentFishRepository studentFishRepository, ClazzRepository clazzRepository) {
        this.fishRepository = fishRepository;
        this.studentRepository = studentRepository;
        this.studentFishRepository = studentFishRepository;
        this.clazzRepository = clazzRepository;
    }

    @Override
    public void addFish(Fish fish) {

        fishRepository.save(fish);

    }

    @Override
    public void deleteFish(Long fishId) {
        getFish(fishId);
        fishRepository.delete(fishId);

    }

    private Fish getFish(Long fishId) {
        Fish exits = fishRepository.findOne(fishId);
        if (exits == null){
            throw new NotFoundException("Fish with id ="+ fishId +" not found...");
        }
        return exits;
    }
    @Transactional
    @Override
    public void updateFishPic(Long fishId, String fishPic) {
        Fish fish = getFish(fishId);
        fish.setFishPic(fishPic);
        fishRepository.save(fish);
    }

    @Override
    public Fish findFish(long fishId) {
        return this.getFish(fishId);
    }

    @Override
    public List<Fish> findAllFish() {

        return fishRepository.findAll();
    }


//    @Transactional
//    @Override
//    public List<StudentFishDTO> getFishStudentDTOsByStudentId(long studentId) {
//
//
//        Student student = getStudent(studentId);
//        Clazz clazz = clazzRepository.findOne(student.getClazzId());
//
//        if (clazz == null)
//            throw new NotFoundException("clazz with id ="+student.getClazzId()+" not found...");
//        StudentFish studentFish = studentFishRepository.findByStudentId(studentId);
//        List<StudentFishDTO> studentFishDTOs = new ArrayList<>();
//        StudentFishDTO currentStudentFishDTO = createStudentFishDTO(clazz, student,studentFish.getFish());
//
//
//        List<Student> inClazzStudents = studentRepository.findByClazzId(student.getClazzId());
//        for (Student inClazzStudent : inClazzStudents)
//        {
//            StudentFish sFish = studentFishRepository.findByStudentId(inClazzStudent.getId());
//            if(student.getId() == inClazzStudent.getId() || sFish == null)
//                continue;
//
//            StudentFishDTO inClazzSFDTO = createStudentFishDTO(clazz, inClazzStudent,sFish.getFish());
//
//            studentFishDTOs.add(inClazzSFDTO);
//
//        }
//        int subCount = 20;
//        if(studentFishDTOs.size() > subCount){
//            studentFishDTOs = randomSubset(studentFishDTOs,subCount - 1);
//        }
//
//        studentFishDTOs.add(currentStudentFishDTO);
//
//        return studentFishDTOs;
//    }
    @Transactional
    @Override
    public List<StudentFishDTO> getFishStudentDTOsByClazzId(long clazzId) {


        Clazz clazz = clazzRepository.findOne(clazzId);

        if (clazz == null)
            throw new NotFoundException("clazz with id ="+clazzId+" not found...");
        List<StudentFishDTO> studentFishDTOs = new ArrayList<>();


        List<Student> inClazzStudents = studentRepository.findByClazzId(clazzId);
        for (Student inClazzStudent : inClazzStudents)
        {
            StudentFish sFish = studentFishRepository.findByStudentId(inClazzStudent.getId());
            if(sFish == null)
                continue;

            StudentFishDTO inClazzSFDTO = createStudentFishDTO(clazz, inClazzStudent,sFish.getFish());

            studentFishDTOs.add(inClazzSFDTO);

        }
        int subCount = 20;
        if(studentFishDTOs.size() > subCount){
            studentFishDTOs = randomSubset(studentFishDTOs,subCount - 1);
        }


        return studentFishDTOs;
    }

    private Student getStudent(long studentId) {
        Student student = studentRepository.findOne(studentId);
        if (student == null)
            throw new NotFoundException("student with id ="+studentId+" not found...");
        return student;
    }
    @Transactional
    @Override
    public void saveOrUpdateStudentFish(long studentId, long fishId) {
        Fish fish = this.getFish(fishId);
        Student student = getStudent(studentId);
        StudentFish studentFish = studentFishRepository.findByStudentId(studentId);
        if (studentFish == null)
            studentFish = new StudentFish();
        studentFish.setStudentId(student.getId());
        studentFish.setFish(fish);
        studentFishRepository.save(studentFish);
    }

    private StudentFishDTO createStudentFishDTO(Clazz clazz, Student inClazzStudent,Fish fish) {
        StudentFishDTO studentFishDTO = new StudentFishDTO();
        studentFishDTO.setStudentId(inClazzStudent.getId());
        studentFishDTO.setStudentName(inClazzStudent.getUsername());
        studentFishDTO.setStudentIdentity(inClazzStudent.getIdentity());
        studentFishDTO.setRole("ROLE_STUDENT");
        studentFishDTO.setClazzName(clazz.getName());
        studentFishDTO.setClazzId(clazz.getId());
        studentFishDTO.setCampusId(clazz.getCampus().getId());
        studentFishDTO.setCampusName(clazz.getCampus().getName());
        studentFishDTO.setCoin(inClazzStudent.getStatistic().getCoin());
        studentFishDTO.setReadNum(inClazzStudent.getStatistic().getReadNum());
        studentFishDTO.setFishId(fish.getId());
        return studentFishDTO;
    }

    public static List randomSubset(final List list, final int subCount) {
        if (list == null || list.size() == 0 || list.size() < subCount) {
            throw new RuntimeException("获取随机子集的参数不合逻辑！");
        }
        List rs = new ArrayList(subCount);
        for (int i = 0; i < subCount; i++) {
            rs.add(null);
        }
        Random random = new Random();
        for (int i = 0, odd = list.size() - 1; i < subCount; i++, odd--) {
            int ranindex = random.nextInt(odd);
            rs.set(i, list.get(ranindex));
            list.set(ranindex, list.get(odd));
        }
        return rs;
    }

}
