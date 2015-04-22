package com.syzton.sunread.controller.bookshelf;
import java.util.ArrayList;

import javassist.NotFoundException;

import javax.validation.Valid;

import org.joda.time.DateTime;
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

import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.dto.bookshelf.BookshelfStatisticsDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.exception.bookshelf.BookInShelfDuplicateVerifiedException;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.service.bookshelf.BookInShelfService;
import com.syzton.sunread.service.semester.SemesterService;

/**
 * @author Morgan-Leon
 */
@Controller
@RequestMapping(value = "/api")
public class BookInShelfController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookshelfController.class);
    private BookInShelfService service;
    private SemesterService semesterService;
	private ArrayList<DateTime> month;
	private ArrayList<String> monthly;
	private ArrayList<Integer> monthlyVerified;
	private ArrayList<Integer> monthlyPoints;
	private int semesterPoints;
	
    
    @Autowired
    public BookInShelfController(BookInShelfService service){
    	this.service = service;    	
    }
    
    @Autowired
    public void SemesterServiceController(SemesterService semesterService){
    	this.semesterService = semesterService;    	
    }
    
//Add a Book to bookshelf    
    @RequestMapping(value = "/bookshelf/{id}/books/{bookId}/bookinshelf", method = RequestMethod.POST)
    @ResponseBody
    public BookInShelfDTO add(@Valid @RequestBody BookInShelfDTO dto
    		,@PathVariable("id")Long id, @PathVariable("bookId")Long bookId	) {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        BookInShelf added = service.add(dto,id, bookId);
        
       return added.createDTO(added);
    }
   
    
//Delete a book in shelf
    @RequestMapping(value = "/bookinshelf/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BookInShelfDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a book in shelf entry with id: {}", id);

        BookInShelf deleted = service.deleteById(id);
        LOGGER.debug("Deleted book in shelf entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Delete a book in shelf
    @RequestMapping(value = "/bookshelf/{bookshelfId}/books/{bookId}/bookinshelf", method = RequestMethod.DELETE)
    @ResponseBody
    public BookInShelfDTO deleteByStudentIdAndBookId(@PathVariable("bookshelfId")Long bookshelfId, @PathVariable("bookId")Long bookId) throws NotFoundException, BookInShelfDuplicateVerifiedException {
        LOGGER.debug("Deleting a book in shelf entry with id: {}", bookshelfId);
        
        //Boolean update = service.updateReadState(bookshelfId, bookId);
              
        BookInShelf deleted = service.deleteByBookshelfIdAndBookId(bookshelfId, bookId);
        
        LOGGER.debug("Deleted book in shelf entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }    
    
//Update a book in shelf    
    @RequestMapping(value = "/bookinshelf/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public BookInShelfDTO update(@Valid @RequestBody BookInShelfDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        BookInShelf updated = service.update(dto,id);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO(updated);
    }
   
//Get all books in shelf
    @RequestMapping(value = "/bookshelf/{id}/bookinshelf", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<BookInShelf> findAllBooks(@PathVariable("id") long id,
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  books in shelf entry with id: {}" );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<BookInShelf> pageResult = service.findByBookshelfId(pageable,id);
        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a Book in bookshelf    
    @RequestMapping(value = "/bookinshelf/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BookInShelfDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a book in shelf entry with id: {}", id);

        BookInShelf found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO(found);
    }
    
 //Get a Book in bookshelf    
    @RequestMapping(value = "/bookshelf/{id}/books/{bookId}/bookinshelf", method = RequestMethod.GET)
    @ResponseBody
    public BookInShelfDTO findByStudentIdAndBookId(@PathVariable("id") Long id,@PathVariable("bookId") Long bookId) throws NotFoundException {
        LOGGER.debug("Finding a book in shelf entry with id: {}", id);

        BookInShelf found = service.findByStudentIdAndBookId(id, bookId);

        return found.createDTO(found);
    }
 //Get a Book in bookshelf    
    @RequestMapping(value = "/student/{studentId}/semester/{semesterId}/bookshelfStatistics", method = RequestMethod.GET)
    @ResponseBody
    public BookshelfStatisticsDTO findBySemester(@PathVariable("studentId") Long studentId,@PathVariable("semesterId") Long semesterId) throws NotFoundException {
        LOGGER.debug("Finding a book in shelf entry with id: {}", semesterId);
        Semester semester = semesterService.findOne(semesterId);
    	DateTime startTime = semester.getStartTime();
    	DateTime endTime = semester.getEndTime(); 
    	
        ArrayList<BookInShelf> founds = service.findByStudentIdAndSemester(studentId, startTime,endTime);
        if ( founds.size() == 0 ) { throw new NotFoundException("Student with ID :"+studentId + " and semester with ID :"+semesterId); }
        
        LOGGER.debug("Found to-do entry with information: {}", founds);
        return createBookshelfStatisticsDTO(founds,startTime,endTime);
    }
    
    public BookshelfStatisticsDTO createBookshelfStatisticsDTO(ArrayList<BookInShelf> booksInShelf,DateTime startTime,DateTime endTime) {
		BookshelfStatisticsDTO dto = new BookshelfStatisticsDTO();
		String username = booksInShelf.get(0).getBookShelf().getUsername();
		semesterPoints = 0;
		int semesterVerified = booksInShelf.size();
		startTime = startTime.dayOfMonth().withMinimumValue().toDateTime();
		endTime = endTime.dayOfMonth().withMaximumValue().toDateTime();
		
		month = new ArrayList<DateTime>();
		monthly = new ArrayList<String>();
		monthlyVerified = new ArrayList<Integer>();
		monthlyPoints = new ArrayList<Integer>();
		
		for (int i = 0; i < booksInShelf.size(); i++) {
			month.add(startTime.plusMonths(i));
			monthly.add(month.get(i).toString());
			monthlyVerified.add(bookNumDuration(booksInShelf, month.get(i), month.get(i).dayOfMonth().withMaximumValue().toDateTime()));
			monthlyPoints.add(bookPointsDuration(booksInShelf,month.get(i), month.get(i).dayOfMonth().withMaximumValue().toDateTime()));
			semesterPoints+=monthlyPoints.get(i);
		}
		
		dto.setSemesterReadNum(booksInShelf.size());
		dto.setMonthly(monthly);
		dto.setMonthlyVerified(monthlyVerified);
		dto.setMonthlyPoints(monthlyPoints);
		dto.setSemesterVerified(semesterVerified);
		dto.setSemesterPoints(semesterPoints);
		dto.setUsername(username);
				
		return dto;
	}
    
    public int bookNumDuration(ArrayList<BookInShelf> booksInShelfs, DateTime fromDate, DateTime toDate){
		int count = 0;
    	for (BookInShelf bookInShelf : booksInShelfs) {
    		if( bookInShelf.getVerifiedTime() != null && isInDuration(bookInShelf.getVerifiedTime(), fromDate, toDate))
    			count++;
		}
    	return count;
     }
    
    public int bookPointsDuration(ArrayList<BookInShelf> bookInShelfs, DateTime fromDate, DateTime toDate) {
		int points = 0;
		for (BookInShelf bookInShelf : bookInShelfs) {
    		if(bookInShelf.getVerifiedTime() != null && isInDuration(bookInShelf.getVerifiedTime(), fromDate, toDate))
    			points+= bookInShelf.getPoint();
		}
		return points;
    }
    
    public boolean isInDuration(DateTime currentTime,DateTime fromDate, DateTime toDate){
    	if (currentTime.isAfter(fromDate.getMillis())&&currentTime.isBefore(toDate.getMillis())) {
			return true;
		}
    	else {
			return false;
		}
    }
    
}










