package com.syzton.sunread.service.message;

import com.syzton.sunread.dto.message.MessageDTO;
import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.model.message.Message;

public interface MessageCenterService {

	public void sendMessage(long sendUserId,long receiveUserId, String message);

	public void sendMessageToClass(long sendUserId,long classId, String message);

	public Page<MessageDTO> findMessagesBySendUser(Pageable pageable, Long userId);

	public Page<MessageDTO> findMessagesByReceiveUser(Pageable pageable, Long userId);

	public Message deleteById(Long id);

	public void readMessage(Long id);
	
}
