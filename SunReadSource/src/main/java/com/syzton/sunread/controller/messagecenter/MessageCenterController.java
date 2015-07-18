package com.syzton.sunread.controller.messagecenter;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.message.MessageDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.message.Action;
import com.syzton.sunread.model.message.Message;
import com.syzton.sunread.repository.message.ActionRepository;
import com.syzton.sunread.service.message.MessageCenterService;

/**
 * 消息中心
 */
@Controller
@RequestMapping("/api")
public class MessageCenterController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageCenterController.class);
	
	private MessageCenterService messageService;

	private ActionRepository actionRepository;
	
	@Autowired
	public MessageCenterController(MessageCenterService service,ActionRepository actionRepository) {
		this.messageService = service;
		this.actionRepository = actionRepository;
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
	public PageResource<MessageDTO> findMessagesBySendUser(@PathVariable("userId") Long userId,
												   @RequestParam("page") int page,
												   @RequestParam("size") int size,
												   @RequestParam(value = "sortBy",required = false) String sortBy) throws NotFoundException {
		LOGGER.debug("Finding messages entry with sendUserId:{}",userId);
		
		sortBy = sortBy ==null ? "creationTime" : sortBy;
		Pageable pageable = this.getPageable(page,size,sortBy,"desc") ;
		
		Page<MessageDTO> messagePage = messageService.findMessagesBySendUser(pageable,userId);
		
		LOGGER.debug("Found messages entry with information:{}",messagePage);
		
		return new PageResource<>(messagePage,"page","size");
	}
	
	@RequestMapping(value = "/to/{userId}/messages",method = RequestMethod.GET)
	@ResponseBody
	public PageResource<MessageDTO> findMessagesByReceiveUser(@PathVariable("userId")Long userId,
												     @RequestParam("page") int page,
												     @RequestParam("size") int size,
												     @RequestParam(value = "sortBy" ,required = false) String sortBy) throws NotFoundException {
		sortBy = sortBy == null ? "creationTime" : sortBy;

		Pageable pageable = this.getPageable(page,size,sortBy,"desc") ;
		
		Page<MessageDTO> messagePage = messageService.findMessagesByReceiveUser(pageable,userId);
		
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

	@RequestMapping(value = "/messages/{id}/reads",method = RequestMethod.PUT)
	@ResponseBody
	public void readMsgById(@PathVariable("id") Long id) throws NotFoundException {

		messageService.readMessage(id);

	}
	@RequestMapping(value = "/actions",method = RequestMethod.GET)
	@ResponseBody
	public PageResource<Action> findActions(@RequestParam("page") int page,@RequestParam("size") int size){

		Pageable pageable = this.getPageable(page,size,"creationTime","desc");

		Page<Action> actionPage = actionRepository.findAll(pageable);

		return  new PageResource<>(actionPage,"page","size");
	}

	@RequestMapping(value = "/actions",method = RequestMethod.POST)
	@ResponseBody
	public void addAction(@Valid @RequestBody Action action) {
		actionRepository.save(action);
	}

	@RequestMapping(value = "/actions/{actionId}",method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteAction(@PathVariable("actionId") long actionId) {
		actionRepository.delete(actionId);
	}

	@RequestMapping(value = "/actions/{actionId}",method = RequestMethod.PUT)
	@ResponseBody
	public void updateAction(@Valid @RequestBody Action action,@PathVariable("actionId") long actionId) {
		Action exits = actionRepository.findOne(actionId);
		if(exits==null)
			throw new NotFoundException("action id = "+actionId+" not found..");
		exits.setTitle(action.getTitle());
		exits.setContent(action.getContent());
		actionRepository.save(exits);
	}



}
