package com.syzton.sunread.service.note;


import java.util.ArrayList;
import java.util.List;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.syzton.sunread.controller.util.SecurityContextUtil;
import com.syzton.sunread.dto.note.NoteDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.QBook;
import com.syzton.sunread.model.pointhistory.PointHistory;
import com.syzton.sunread.model.pointhistory.PointHistory.PointFrom;
import com.syzton.sunread.model.pointhistory.PointHistory.PointType;
import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.model.note.QNote;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.note.NoteRepository;
import com.syzton.sunread.repository.user.UserRepository;
import com.syzton.sunread.service.pointhistory.PointHistoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.syzton.sunread.repository.book.predicates.BookPredicates.quickSearchContains;


/**
 * @author chenty
 *
 */
@Service
public class NoteRepositoryService implements NoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteRepositoryService.class);

    /* Auto wire of note */
    private NoteRepository repository;

    @Autowired
    public NoteRepositoryService(NoteRepository repository) {
        this.repository = repository;
    }
    
    /* Auto wire of book */
    private BookRepository bookRepository;

    @Autowired
    public void BookRepositoryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /* Auto wire of user */
    private UserRepository userRepository;

    @Autowired
    public void UserRepositoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    private PointHistoryService pointHistoryService;
    
    @Autowired
    public void PointHistoryService(PointHistoryService pointHistoryService) {
        this.pointHistoryService = pointHistoryService;
    }
    
    private SecurityContextUtil securityContextUtil;
    
    @Autowired
    public void setSecurityContextUtil(SecurityContextUtil securityContextUtil) {
        this.securityContextUtil = securityContextUtil;
    }
    
    @Override
    public Note add(NoteDTO added, Long bookId) {
        LOGGER.debug("Adding a new note entry with information: {}", added);
        
        Book book = bookRepository.findOne(bookId);
        book.getStatistic().increaseNotes();
        bookRepository.save(book);
        User user = securityContextUtil.getUser();
        Note noteModel = Note.getBuilder(added.getTitle(), added.getContent(), book, user)
        		.image(added.getImage())
                .build();
        
        // Add pointhistories entity
        PointHistory pointHistory = new PointHistory();
        pointHistory.setPointFrom(PointFrom.FROM_NOTE);
        pointHistory.setPointType(PointType.IN);
        pointHistory.setNum(5);
        pointHistory.setStudent((Student)user);
        pointHistoryService.add(pointHistory);
        return repository.save(noteModel);
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Note deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a note entry with id: {}", id);

        Note deleted = findById(id);
        LOGGER.debug("Deleting note entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Note> findAll(Pageable pageable) {
        LOGGER.debug("Finding all note entries");
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Note findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a note entry with id: {}", id);

        Note found = repository.findOne(id);
        LOGGER.debug("Found note entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No note entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Note update(NoteDTO updated) throws NotFoundException {
        LOGGER.debug("Updating contact with information: {}", updated);

        Note model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getTitle(), updated.getContent(), updated.getImage());

        return model;
    }
    
    @Transactional
    @Override 
    public Page<Note> findByBookId(Pageable pageable, long bookId) {

        Book book = bookRepository.findOne(bookId);
        Page<Note> notePage = repository.findByBook(book, pageable);
        return notePage;
    }

    @Transactional
    @Override 
    public Page<Note> findByUserId(Pageable pageable, long userId) {

        User user = userRepository.findOne(userId);
        Page<Note> notePage = repository.findByUser(user, pageable);
        return notePage;
    }
    
    @Transactional
    @Override 
    public Page<Note> findBySearchTerm(Pageable pageable, String searchTerm) {
    	
    	// Get a list of book
    	List<Long> bookIdList = bookRepository.findAllIdBySearchTerm(searchTerm);
    	
    	// Convert bookId list to book list
    	List<Book> bookList = new ArrayList<Book>();
     	for (Long bookId : bookIdList){
    		Book book = new Book();
    		book.setId((Long)bookId);
    		bookList.add(book);
    	}

        Page<Note> notePage = repository.findAllByBookList(bookList, pageable);
        return notePage;
    }

}
