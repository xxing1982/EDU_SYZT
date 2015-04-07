package com.syzton.sunread.service.message;


import com.syzton.sunread.exception.common.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.model.message.Message;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.message.MessageCenterRepository;
import com.syzton.sunread.repository.user.UserRepository;

@Service
public class MessageCenterRepositoryService implements MessageCenterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageCenterRepositoryService.class);
	
	private MessageCenterRepository messageRepository;
	
	private UserRepository userRepository;
	
	@Autowired
	public MessageCenterRepositoryService(MessageCenterRepository repository,UserRepository userRepository) {
		this.messageRepository = repository;
		this.userRepository = userRepository;
	}

	@Override
	public void sendMessage(long sendUserId,long receiveUseId,String message) {

		this.findUserById(sendUserId);

		this.findUserById(receiveUseId);

		Message send = new Message();
		send.setMessage(message);
		send.setSendUserId(sendUserId);
		send.setReceiveUserId(receiveUseId);

		messageRepository.save(send);
		
	}

	private void findUserById(long userId) {
		User receiveUser = userRepository.findOne(userId);
		if(receiveUser == null){
			throw new NotFoundException("user id = "+userId+ " not found...");
		}
	}

	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Page<Message> findMessagesBySendUser(Pageable pageable,Long sendUserId) {

		this.findUserById(sendUserId);
		
		Page<Message> messagePage = messageRepository.findBySendUserId(pageable, sendUserId);
		return messagePage;
	}

	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Page<Message> findMessagesByReceiveUser(Pageable pageable, Long receiveUserId) {

		this.findUserById(receiveUserId);
		
		Page<Message> messagePage = messageRepository.findByReceiveUserId(pageable, receiveUserId);
		
		return messagePage;
	}

	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Message deleteById(Long id) throws NotFoundException {
		
		Message deleted = findById(id);
		
		messageRepository.delete(deleted);
		return deleted;
	}

	@Transactional(readOnly = true,rollbackFor = {NotFoundException.class})
	private Message findById(Long id) throws NotFoundException {
		
		Message found = messageRepository.findOne(id);
		
		if(found == null) {
			throw new NotFoundException("No message found with id:"+id);
		}
		
		return found;
	}
}
