package com.syzton.sunread.service.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.exception.store.InSufficientCoinsException;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.store.ExchangeHistory;
import com.syzton.sunread.model.store.Gift;
import com.syzton.sunread.model.store.GiftStatus;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.coinhistory.CoinHistoryRepository;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.store.ExchangeHistoryRepository;
import com.syzton.sunread.repository.store.GiftRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import com.syzton.sunread.repository.user.UserRepository;

/**
 * Created by jerry on 4/20/15.
 */
@Service
public class StoreRepositoryService implements StoreService {

    private GiftRepository giftRepository;

    private ExchangeHistoryRepository exchangeHistoryRepository;

    private StudentRepository studentRepository;

    private UserRepository userRepository;
    
    private CampusRepository campusRepository;

    private CoinHistoryRepository coinHistoryRepository;

    @Autowired
    public StoreRepositoryService(GiftRepository giftRepository, ExchangeHistoryRepository exchangeHistoryRepository,
                                  StudentRepository studentRepository, UserRepository userRepository,
                                  CampusRepository campusRepository,CoinHistoryRepository coinHistoryRepository) {
        this.giftRepository = giftRepository;
        this.exchangeHistoryRepository = exchangeHistoryRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.campusRepository = campusRepository;
        this.coinHistoryRepository = coinHistoryRepository;
    }

    @Override
    public void addGift(Gift gift) {

        Campus exists = campusRepository.findOne(gift.getCampusId());
        if(exists == null){
            throw new NotFoundException("school Id = "+ gift.getCampusId()+" not found...");
        }

        giftRepository.save(gift);
    }

    @Override
    public void deleteGift(long giftId) {

        getGift(giftId);

        giftRepository.delete(giftId);
    }

    @Transactional
    @Override
    public void exchangeGift(long studentId, long giftId,int count) {

        Gift gift = getGift(giftId);

        Student student = getStudent(studentId);

        int currentCoins = student.getStatistic().getCoin();

        int needCoins = count * gift.getCoin();

        if(currentCoins >= needCoins){

           // ExchangeHistory exchangeHistory = exchangeHistoryRepository.findByStudentIdAndGift(studentId,gift);

           // if(exchangeHistory == null){
//                exchangeHistory =  new ExchangeHistory();
//            }
            ExchangeHistory exchangeHistory = new ExchangeHistory();
            exchangeHistory.setGift(gift);
            exchangeHistory.setStudentId(studentId);
            exchangeHistory.setStatus(GiftStatus.SUCCESSED);
            exchangeHistory.setCount(count + exchangeHistory.getCount());
            exchangeHistoryRepository.save(exchangeHistory);
            student.getStatistic().setCoin(currentCoins - needCoins);

            CoinHistory coinHistory = new CoinHistory();
            coinHistory.setCoinType(CoinHistory.CoinType.OUT);
            coinHistory.setNum(needCoins);
            coinHistory.setReason("exchange gift =" + gift.getName());
            coinHistory.setStudent(student);
            coinHistory.setCoinFrom(CoinHistory.CoinFrom.FROM_GIFT);
            coinHistory.setCoinType(CoinHistory.CoinType.OUT);
            coinHistoryRepository.save(coinHistory);
            studentRepository.save(student);
        }else {
            throw new InSufficientCoinsException("student current coin = "+ student.getStatistic().getCoin() +" insufficient. need coin ="+ needCoins);
        }


    }

    @Override
    public void changeExchangeStatus(long exchangeId,long userId,GiftStatus status) {

        ExchangeHistory exchangeHistory = exchangeHistoryRepository.findOne(exchangeId);
        if(exchangeHistory == null){
            throw new NotFoundException("exchangeHistory with id =" + exchangeId +" not found...");
        }

        User user = userRepository.findOne(userId);
        if (user == null){
            throw new NotFoundException("user with id =" +userId+" not found...");
        }

        exchangeHistory.setStatus(status);

        exchangeHistoryRepository.save(exchangeHistory);



    }

    @Override
    public Page<Gift> getGifts(Pageable pageable,long campusId) {

        Page<Gift> giftPage = giftRepository.findByCampusId(pageable, campusId);

        return giftPage;
    }

    @Override
    public Page<ExchangeHistory> getExchangeHistory(Pageable pageable,long studentId) {

        getStudent(studentId);

        Page<ExchangeHistory> historyPage = exchangeHistoryRepository.findByStudentId(pageable,studentId);
        return historyPage;
    }

    private Gift getGift(long giftId) {
        Gift exist = giftRepository.findOne(giftId);

        if (exist == null){
            throw new NotFoundException("gift with id = "+giftId+" not found...");
        }
        User addUser = userRepository.findOne(exist.getUserId());

        exist.setUserName(addUser.getUsername());


        return exist;
    }

    private Student getStudent(long studentId){
        Student student = studentRepository.findOne(studentId);

        if(student == null){
            throw new NotFoundException("student with id = "+studentId+" not found...");
        }
        return student;
    }

}
