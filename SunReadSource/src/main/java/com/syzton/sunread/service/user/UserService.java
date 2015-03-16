package com.syzton.sunread.service.user;

import com.syzton.sunread.model.book.Category;
import com.syzton.sunread.model.user.User;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by jerry on 3/9/15.
 */
public interface UserService {

    public User findById(Long id) throws NotFoundException;

    public User add(User user);

    void deleteById(Long id) throws NotFoundException;;

}
