package com.syzton.sunread.repository.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.message.Message;

public interface MessageCenterRepository extends JpaRepository<Message,Long> {

	Page<Message> findBySendUserId(Pageable pageable, long sendUserId);

	Page<Message> findByReceiveUserId(Pageable pageable, long receiveUserId);

}
