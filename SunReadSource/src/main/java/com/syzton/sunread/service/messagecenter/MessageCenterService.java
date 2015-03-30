package com.syzton.sunread.service.messagecenter;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.model.messagecenter.Message;

public interface MessageCenterService {

	public Message sendMessage(Message message);

	public Page<Message> findMessagesBySendUser(Pageable pageable, Long userId);

	public Page<Message> findMessagesByReceiveUser(Pageable pageable, Long userId);

	public Message deleteById(Long id) throws NotFoundException;
	
}
