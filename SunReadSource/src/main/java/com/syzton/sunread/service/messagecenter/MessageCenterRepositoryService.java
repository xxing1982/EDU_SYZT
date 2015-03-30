package com.syzton.sunread.service.messagecenter;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.model.messagecenter.Message;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.messagecenter.MessageCenterRepository;
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
	public Message sendMessage(Message message) {
		
		Message sended = messageRepository.save(message);
		
		return sended;
	}

	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Page<Message> findMessagesBySendUser(Pageable pageable,Long userId) {
		
		LOGGER.debug("Finding messages entry with sendUser:{}",userId);
		User sendUser = userRepository.findOne(userId);
		Page<Message> messagePage = messageRepository.findMessagesBySendUser(pageable,sendUser);
		return messagePage;
	}

	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Page<Message> findMessagesByReceiveUser(Pageable pageable, Long userId) {
		
		User receiveUser = userRepository.findOne(userId);
		Page<Message> messagePage = messageRepository.findMessagesByReceiveUser(pageable,receiveUser);
		
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
