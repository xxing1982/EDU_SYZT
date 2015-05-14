package com.syzton.sunread.service.message;


import com.syzton.sunread.dto.message.MessageDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.user.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.model.message.Message;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.message.MessageCenterRepository;
import com.syzton.sunread.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageCenterRepositoryService implements MessageCenterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageCenterRepositoryService.class);
	
	private MessageCenterRepository messageRepository;
	
	private UserRepository userRepository;

	private StudentRepository studentRepository;
	
	@Autowired
	public MessageCenterRepositoryService(MessageCenterRepository repository,UserRepository userRepository,StudentRepository studentRepository) {
		this.messageRepository = repository;
		this.userRepository = userRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public void sendMessage(long sendUserId,long receiveUseId,String message) {

		this.checkUser(sendUserId);

		this.checkUser(receiveUseId);

		Message send = new Message();
		send.setMessage(message);
		send.setSendUserId(sendUserId);
		send.setReceiveUserId(receiveUseId);

		messageRepository.save(send);
		
	}

	@Override
	@Transactional(rollbackFor = NotFoundException.class)
	public void sendMessageToClass(long sendUserId, long classId, String message) {

		this.checkUser(sendUserId);

		List<Student> students = studentRepository.findByClazzId(classId);
		if(students == null || students.size()==0){
			throw new NotFoundException("no student found in class id ="+ classId);
		}

		for(Student student : students){
			this.sendMessage(sendUserId,student.getId(),message);
		}

	}

	private User checkUser(long userId) {
		User user = userRepository.findOne(userId);
		if(user == null){
			throw new NotFoundException("user id = "+userId+ " not found...");
		}
		return user;
	}

	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Page<MessageDTO> findMessagesBySendUser(Pageable pageable,Long sendUserId) {
		User sendUser = this.checkUser(sendUserId);
		Page<Message> messagePage = messageRepository.findBySendUserId(pageable, sendUserId);
		List<MessageDTO> messageDTOList = new ArrayList<>();
		for (Message message : messagePage.getContent()){
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setIsRead(message.getIsRead());
			messageDTO.setMessage(message.getMessage());
			messageDTO.setSendUserId(message.getSendUserId());
			messageDTO.setSendUsername(sendUser.getUsername());
			messageDTO.setReceiveUserId(message.getReceiveUserId());
			User receiveUser = this.checkUser(message.getReceiveUserId());
			messageDTO.setReceiveUserName(receiveUser.getUsername());
			messageDTO.setCreationTime(message.getCreationTime().toDate());
			messageDTOList.add(messageDTO);

		}

		Page<MessageDTO> messageDTOPage = new PageImpl<>(messageDTOList,pageable,messagePage.getTotalElements());

		return messageDTOPage;
	}

	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Page<MessageDTO> findMessagesByReceiveUser(Pageable pageable, Long receiveUserId) {

		User receiveUser = this.checkUser(receiveUserId);
		
		Page<Message> messagePage = messageRepository.findByReceiveUserId(pageable, receiveUserId);
		List<MessageDTO> messageDTOList = new ArrayList<>();
		for (Message message : messagePage.getContent()){
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setIsRead(message.getIsRead());
			messageDTO.setMessage(message.getMessage());
			messageDTO.setSendUserId(message.getSendUserId());
			User sendUser = this.checkUser(message.getSendUserId());
			messageDTO.setSendUsername(sendUser.getUsername());
			messageDTO.setReceiveUserId(message.getReceiveUserId());
			messageDTO.setReceiveUserName(receiveUser.getUsername());
			messageDTO.setCreationTime(message.getCreationTime().toDate());
			messageDTOList.add(messageDTO);

		}

		Page<MessageDTO> messageDTOPage = new PageImpl<>(messageDTOList,pageable,messagePage.getTotalElements());

		return messageDTOPage;
	}

	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Message deleteById(Long id) throws NotFoundException {
		
		Message deleted = findById(id);
		
		messageRepository.delete(deleted);
		return deleted;
	}

	@Transactional(readOnly = true,rollbackFor = {NotFoundException.class})
	private Message findById(Long id){
		
		Message found = messageRepository.findOne(id);
		
		if(found == null) {
			throw new NotFoundException("No message found with id:"+id);
		}
		
		return found;
	}
}
