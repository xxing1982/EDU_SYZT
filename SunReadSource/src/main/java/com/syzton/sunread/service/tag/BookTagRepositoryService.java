package com.syzton.sunread.service.tag;

import java.util.List;

import com.syzton.sunread.dto.tag.TagStatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.controller.util.SecurityContextUtil;
import com.syzton.sunread.dto.tag.BookTagDTO;
import com.syzton.sunread.exception.tag.BookTagNotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.tag.BookTag;
import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.tag.BookTagRepository;
import com.syzton.sunread.repository.tag.TagRepository;


/**
 * @author chenty
 *
 */
@Service
public class BookTagRepositoryService implements BookTagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookTagRepositoryService.class);
    private BookTagRepository bookTagRepository;

    
    /*
     *  Autowiring of BookTag
     */
    @Autowired
    public BookTagRepositoryService(BookTagRepository bookTagRepository) {
        this.bookTagRepository = bookTagRepository;
    }
    
    private TagRepository tagRepository;

    /*
     *  Autowiring of Tag
     */
    @Autowired
    public void RepositoryTagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    private BookRepository bookRepository;

    /*
     *  Autowiring of Book
     */
    @Autowired
    public void RepositoryBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    private SecurityContextUtil securityContextUtil;
    
    @Autowired
    public void setSecurityContextUtil(SecurityContextUtil securityContextUtil) {
        this.securityContextUtil = securityContextUtil;
    }
    
    @Transactional
    @Override
    public BookTag add(BookTagDTO added) {
        LOGGER.debug("Adding a new booktag entry with information: {}", added);
        
        Tag tag = tagRepository.findOne(added.getTag_id());
        Book book = bookRepository.findOne(added.getBook_id());
        User user = securityContextUtil.getUser();
        List<BookTag> booktags = findByTagAndBookAndUser( tag, book, user ); 
        if ( booktags.size() == 0){
	        BookTag bookTagModel = BookTag.getBuilder(tag, book, user)
	                .build();
	        return bookTagRepository.save(bookTagModel);
        } else {
        	return booktags.get(0);
        }
    }
    
    @Transactional(rollbackFor = {BookTagNotFoundException.class})
    @Override
    public BookTag deleteById(Long id) throws BookTagNotFoundException {
        LOGGER.debug("Deleting a booktag entry with id: {}", id);

        BookTag deleted = findById(id);
        LOGGER.debug("Deleting booktag entry: {}", deleted);

        bookTagRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookTag> findAll() {
        LOGGER.debug("Finding all booktag entries");
        return bookTagRepository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {BookTagNotFoundException.class})
    @Override
    public BookTag findById(Long id) throws BookTagNotFoundException {
        LOGGER.debug("Finding a booktag entry with id: {}", id);

        BookTag found = bookTagRepository.findOne(id);
        LOGGER.debug("Found booktag entry: {}", found);

        if (found == null) {
            throw new BookTagNotFoundException("No booktag entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {BookTagNotFoundException.class})
    @Override
    public BookTag update(BookTagDTO updated) throws BookTagNotFoundException {
        LOGGER.debug("Updating contact with information: {}", updated);

        BookTag model = findById(updated.getId());
        LOGGER.debug("Found a booktag entry: {}", model);

        model.update();

        return model;
    }
    
    @Override
    public Page<BookTag> findByTagId(Pageable pageable, long tagId) {

        Tag tag = tagRepository.findOne(tagId);
        Page<BookTag> bookTagPage = bookTagRepository.findByTag(tag,pageable);
        return bookTagPage;
    }

	@Override
	public List<BookTag> findByTagAndBookAndUser(Tag tag, Book book, User user) {
		return bookTagRepository.findByTagAndBookAndUser(tag, book, user);
	}

    @Override
    public List<TagStatisticsDTO> statistics() {
        return bookTagRepository.tagStatistics();
    }
}
