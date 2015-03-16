package com.syzton.sunread.service.user;

import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jerry on 3/16/15.
 */
@Service
public class UserRepositoryService implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserRepositoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) throws NotFoundException {


        User user = userRepository.findOne(id);
        if(user == null){
            throw new NotFoundException("user id =" + id +" does not exits...");
        }
        return user;

    }
    @Transactional
    @Override
    public User add(User user) {
       return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException
    {
        User user = userRepository.findOne(id);
        if(user == null){
            throw new NotFoundException("user id =" + id +" does not exits...");
        }
         userRepository.delete(user);
    }
}
