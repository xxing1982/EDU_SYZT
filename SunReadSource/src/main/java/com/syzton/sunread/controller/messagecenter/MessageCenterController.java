package com.syzton.sunread.controller.messagecenter;

import javassist.NotFoundException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.messagecenter.Message;
import com.syzton.sunread.service.messagecenter.MessageCenterService;

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
	
	@RequestMapping(value = "/message/sendMessage",method = RequestMethod.POST)
	@ResponseBody
	public Message sendMessage(@Valid @RequestBody Message message) {
		LOGGER.debug("Sending a new message entry with information:{}",message);
		
		Message sended = messageService.sendMessage(message);
		
		LOGGER.debug("Sended a message entry with information:{}",sended);
		
		return sended;
	}
	
	@RequestMapping(value = "/user/{userId}/sendedMessages",method = RequestMethod.GET)
	@ResponseBody
	public PageResource<Message> findMessagesBySendUser(@RequestParam("userId") Long userId,
												   @RequestParam("page") int page,
												   @RequestParam("size") int size,
												   @RequestParam("sortBy") String sortBy) throws NotFoundException {
		LOGGER.debug("Finding messages entry with sendUserId:{}",userId);
		
		sortBy = sortBy ==null ? "id" : sortBy;
		Pageable pageable = new PageRequest(
				page,size,new Sort(sortBy)
				);
		
		Page<Message> messagePage = messageService.findMessagesBySendUser(pageable,userId);
		
		LOGGER.debug("Found messages entry with information:{}",messagePage);
		
		return new PageResource<>(messagePage,"page","size");
	}
	
	@RequestMapping(value = "/user/{userId}/receivedMessages",method = RequestMethod.GET)
	@ResponseBody
	public PageResource<Message> findMessagesByReceiveUser(@RequestParam("userId")Long userId,
												     @RequestParam("page") int page,
												     @RequestParam("size") int size,
												     @RequestParam("sortBy") String sortBy) throws NotFoundException {
		sortBy = sortBy == null ? "id" : sortBy;
		Pageable pageable = new PageRequest(
				page,size,new Sort(sortBy)
				);
		
		Page<Message> messagePage = messageService.findMessagesByReceiveUser(pageable,userId);
		
		return new PageResource<>(messagePage,"page","size");
	}
	
	@RequestMapping(value = "/message/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public Message deleteById(@PathVariable("id") Long id) throws NotFoundException {
		
		LOGGER.debug("Deleting a message entry with id:{}",id);
		
		Message deleted = messageService.deleteById(id);
		
		LOGGER.debug("Deleted a message entry with information:{}",deleted);
		
		return deleted;
	}
	
}
