package com.syzton.sunread.scheduler;

import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.ClazzStatisticHistory;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.UserStatisticHistory;
import com.syzton.sunread.repository.SemesterRepository;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.organization.ClazzStatisticHistoryRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import com.syzton.sunread.repository.user.UserStatisticHistoryRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jerry on 4/16/15.
 */
@Component
public class StatisticTask {

    SemesterRepository semesterRepository;
    StudentRepository studentRepository;
    ClazzRepository clazzRepository;
    ClazzStatisticHistoryRepository clazzStatisticHistoryRepository;
    UserStatisticHistoryRepository statisticHistoryRepository;
    CampusRepository campusRepository;

    @Autowired
    public StatisticTask(SemesterRepository semesterRepository, StudentRepository studentRepository, ClazzRepository clazzRepository, ClazzStatisticHistoryRepository clazzStatisticHistoryRepository, UserStatisticHistoryRepository statisticHistoryRepository, CampusRepository campusRepository) {
        this.semesterRepository = semesterRepository;
        this.studentRepository = studentRepository;
        this.clazzRepository = clazzRepository;
        this.clazzStatisticHistoryRepository = clazzStatisticHistoryRepository;
        this.statisticHistoryRepository = statisticHistoryRepository;
        this.campusRepository = campusRepository;
    }



    @Scheduled(cron = "0 0 1 1 * ?")
    public void executeStatistic(){
        List<Campus> campuses = campusRepository.findAll();
        for (Campus campus : campuses) {
            Semester semester = semesterRepository.findByTimeAndCampusId(new DateTime(),campus.getId());
            backUpUserStatistic(semester.getId());
            backUpClazzStatistic(semester.getId());
        }
    }

    private void backUpClazzStatistic(Long semesterId) {
        List<Clazz> clazzList = clazzRepository.findAll();
        for (Clazz clazz : clazzList){
            ClazzStatisticHistory exits = clazzStatisticHistoryRepository.findByClazzIdAndSemesterId(clazz.getId(),semesterId);
            if(exits == null){
                ClazzStatisticHistory statisticHistory = new ClazzStatisticHistory();
                statisticHistory.setSemesterId(semesterId);
                statisticHistory.setClazzId(clazz.getId());
                statisticHistory.setTotalPoints(clazz.getClazzStatistic().getTotalPoints());
                statisticHistory.setTotalReads(clazz.getClazzStatistic().getTotalReads());
                statisticHistory.setAvgPoints(clazz.getClazzStatistic().getAvgPoints());
                statisticHistory.setAvgReads(clazz.getClazzStatistic().getAvgReads());
                clazzStatisticHistoryRepository.save(statisticHistory);
            }


        }
    }

    private void backUpUserStatistic(Long semesterId) {
        List<Student> studentList = studentRepository.findAll();

        for(Student student : studentList){

            UserStatisticHistory exits = statisticHistoryRepository.findByStudentIdAndSemesterId(student.getId(), semesterId);
            if(exits == null) {
                UserStatisticHistory userStatisticHistory = new UserStatisticHistory();
                userStatisticHistory.setCoin(student.getStatistic().getCoin());
                userStatisticHistory.setPoint(student.getStatistic().getPoint());
                userStatisticHistory.setTestPasses(student.getStatistic().getTestPasses());
                userStatisticHistory.setReadNum(student.getStatistic().getReadNum());
                userStatisticHistory.setNotes(student.getStatistic().getNotes());
                userStatisticHistory.setLevel(student.getStatistic().getLevel());
                userStatisticHistory.setStudentId(student.getId());
                userStatisticHistory.setSemesterId(semesterId);

                statisticHistoryRepository.save(userStatisticHistory);
            }
        }
    }
}
