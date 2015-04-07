package com.syzton.sunread.controller.messagecenter;

import com.syzton.sunread.dto.message.MessageDTO;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.message.Message;
import com.syzton.sunread.service.message.MessageCenterService;

import javax.validation.Valid;

/**
 * 消息中心
 */
@Controller
@RequestMapping("/api")
public class MessageCenterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageCenterController.class);
	
	private MessageCenterService messageService;
	
	@Autowired
	public MessageCenterController(MessageCenterService service) {
		this.messageService = service;
	}
	
	@RequestMapping(value = "/from/{sendUserId}/to/{receiveUserId}/messages",method = RequestMethod.POST)
	@ResponseBody
	public void sendMessage(@PathVariable("sendUserId") long sendUserId,
							   @PathVariable("receiveUserId") long receiveUserId,
							   @Valid @RequestBody MessageDTO messageDTO) {
		LOGGER.debug("Sending a new message entry with information:{}",messageDTO);
		
		messageService.sendMessage(sendUserId,receiveUserId,messageDTO.getMessage());
		

	}

	@RequestMapping(value = "/from/{sendUserId}/to/class/{classId}/messages",method = RequestMethod.POST)
	@ResponseBody
	public void sendMessageToClass(@PathVariable("sendUserId") long sendUserId,
							@PathVariable("classId") long classId,
							@Valid @RequestBody MessageDTO messageDTO) {
		LOGGER.debug("Sending a new message entry with information:{}",messageDTO);

		messageService.sendMessageToClass(sendUserId,classId,messageDTO.getMessage());


	}
	
	@RequestMapping(value = "/from/{userId}/messages",method = RequestMethod.GET)
	@ResponseBody
	public PageResource<Message> findMessagesBySendUser(@PathVariable("userId") Long userId,
												   @RequestParam("page") int page,
												   @RequestParam("size") int size,
												   @RequestParam(value = "sortBy",required = false) String sortBy) throws NotFoundException {
		LOGGER.debug("Finding messages entry with sendUserId:{}",userId);
		
		sortBy = sortBy ==null ? "id" : sortBy;
		Pageable pageable = new PageRequest(
				page,size,new Sort(sortBy)
				);
		
		Page<Message> messagePage = messageService.findMessagesBySendUser(pageable,userId);
		
		LOGGER.debug("Found messages entry with information:{}",messagePage);
		
		return new PageResource<>(messagePage,"page","size");
	}
	
	@RequestMapping(value = "/to/{userId}/messages",method = RequestMethod.GET)
	@ResponseBody
	public PageResource<Message> findMessagesByReceiveUser(@PathVariable("userId")Long userId,
												     @RequestParam("page") int page,
												     @RequestParam("size") int size,
												     @RequestParam(value = "sortBy" ,required = false) String sortBy) throws NotFoundException {
		sortBy = sortBy == null ? "id" : sortBy;
		Pageable pageable = new PageRequest(
				page,size,new Sort(sortBy)
				);
		
		Page<Message> messagePage = messageService.findMessagesByReceiveUser(pageable,userId);
		
		return new PageResource<>(messagePage,"page","size");
	}
	
	@RequestMapping(value = "/messages/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public Message deleteById(@PathVariable("id") Long id) throws NotFoundException {
		
		LOGGER.debug("Deleting a message entry with id:{}",id);

		
		Message deleted = messageService.deleteById(id);
		
		LOGGER.debug("Deleted a message entry with information:{}",deleted);
		
		return deleted;
	}
	
}
