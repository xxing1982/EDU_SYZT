package com.syzton.sunread.repository.messagecenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.messagecenter.Message;
import com.syzton.sunread.model.user.User;

public interface MessageCenterRepository extends JpaRepository<Message,Long> {

	Page<Message> findMessagesBySendUser(Pageable pageable, User sendUser);

	Page<Message> findMessagesByReceiveUser(Pageable pageable, User receiveUser);

}
