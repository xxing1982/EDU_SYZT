package com.syzton.sunread.controller.bookshelf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.syzton.sunread.dto.bookshelf.BookshelfStatisticsDTO.VerifiedBook;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.exception.bookshelf.BookInShelfDuplicateVerifiedException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.book.DictionaryType;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.service.book.BookService;
import com.syzton.sunread.service.book.DictionaryService;
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
    private BookService bookService;
    private DictionaryService dictionaryService;
	@Autowired
    public BookInShelfController(BookInShelfService service){
    	this.service = service;    	
    }
    
    @Autowired
    public void SemesterServiceController(SemesterService semesterService){
    	this.semesterService = semesterService;    	
    }
    
    @Autowired
    public void BookController(BookService bookService){
    	this.bookService = bookService;    	
    }
    
    @Autowired
    public void DictionaryController(DictionaryService dictionaryService){
    	this.dictionaryService = dictionaryService;    	
    }
    
    //Add a Book to bookshelf    
    @RequestMapping(value = "/bookshelf/{id}/books/{bookId}/bookinshelf", method = RequestMethod.POST)
    @ResponseBody
    public BookInShelfDTO add(@Valid @RequestBody BookInShelfDTO dto
    		,@PathVariable("id")Long id, @PathVariable("bookId")Long bookId	) {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        BookInShelf added = service.add(dto,id, bookId);
        
       return added.createDTO();
    }
   
    
//Delete a book in shelf
    @RequestMapping(value = "/bookinshelf/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BookInShelfDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a book in shelf entry with id: {}", id);

        BookInShelf deleted = service.deleteById(id);
        LOGGER.debug("Deleted book in shelf entry with information: {}", deleted);

        return deleted.createDTO();
    }
    
//Delete a book in shelf
    @RequestMapping(value = "/bookshelf/{bookshelfId}/books/{bookId}/bookinshelf", method = RequestMethod.DELETE)
    @ResponseBody
    public BookInShelfDTO deleteByStudentIdAndBookId(@PathVariable("bookshelfId")Long bookshelfId, @PathVariable("bookId")Long bookId) throws NotFoundException, BookInShelfDuplicateVerifiedException {
        LOGGER.debug("Deleting a book in shelf entry with id: {}", bookshelfId);
        
        //Boolean update = service.updateReadState(bookshelfId, bookId);
              
        BookInShelf deleted = service.deleteByBookshelfIdAndBookId(bookshelfId, bookId);
        
        LOGGER.debug("Deleted book in shelf entry with information: {}", deleted);

        return deleted.createDTO();
    }    
    
//Update a book in shelf    
    @RequestMapping(value = "/bookinshelf/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public BookInShelfDTO update(@Valid @RequestBody BookInShelfDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        BookInShelf updated = service.update(dto,id);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO();
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

        return found.createDTO();
    }
    
 //Get a Book in bookshelf    
    @RequestMapping(value = "/bookshelf/{id}/books/{bookId}/bookinshelf", method = RequestMethod.GET)
    @ResponseBody
    public BookInShelfDTO findByStudentIdAndBookId(@PathVariable("id") Long id,@PathVariable("bookId") Long bookId) throws NotFoundException {
        LOGGER.debug("Finding a book in shelf entry with id: {}", id);

        BookInShelf found = service.findByStudentIdAndBookId(id, bookId);
        if(found.getDeleted())
        	throw new com.syzton.sunread.exception.common.NotFoundException("The book with name "+found.getBookName()+"had been deleted.");
        return found.createDTO();
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
       
        LOGGER.debug("Found to-do entry with information: {}", founds);
        return createBookshelfStatisticsDTO(founds, startTime, endTime);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public BookshelfStatisticsDTO createBookshelfStatisticsDTO(ArrayList<BookInShelf> booksInShelves, DateTime startTime, DateTime endTime) {
    	
    	// Initlizate the dto entity
    	BookshelfStatisticsDTO dto = new BookshelfStatisticsDTO();
    	int startMonth = startTime.getMonthOfYear();
    	int endMonth = endTime.getMonthOfYear();
    	int monthCount = endMonth - startMonth + ( startMonth < endMonth ? 0 : 12 ) + 1;
    	dto.monthlyVerifiedNums = new int[monthCount];
    	dto.monthlyPoints = new int[monthCount];
    	dto.semesterVerified = new ArrayList<VerifiedBook>();
    	dto.semesterReadNum = 0;
    	
    	// Initlizate monthlyVerifiedNums and monthlyPoints
    	for ( int i = 0; i < monthCount; i ++ ){
    		dto.monthlyVerifiedNums[i] = 0;
    		dto.monthlyPoints[i] = 0;
    	}
		
    	// Initlizate the categoriesMap
    	List<Dictionary> categories =  dictionaryService.findByType(DictionaryType.CATEGORY);
		Map categoriesMap = new HashMap<Integer, String>();
		for ( Dictionary category : categories ){
			categoriesMap.put( category.getValue(), category.getName() );					
		}
		
    	// Calculate the dto
		for ( BookInShelf bookInShelf : booksInShelves ) {
			
			// Update semesterReadNum
			dto.semesterReadNum ++;
			
			// Check if bookInShelf is verified
			if ( bookInShelf.getReadState() == true ) {
				
				// The index in both arrays
				int index = bookInShelf.getVerifiedTime().getMonthOfYear() - startMonth;
				if ( index < 0 ) { index += 12; }
				
				// Update monthlyVerifiedNums
				dto.monthlyVerifiedNums[index] ++;
				
				// Update monthlyPoints
				dto.monthlyPoints[index] += bookInShelf.getPoint();		
				
				// Update the semesterVerified
				VerifiedBook verifiedBook = dto.new VerifiedBook();
				Book book = bookService.findById(bookInShelf.getBookId());
				verifiedBook.bookName = bookInShelf.getBookName();
				verifiedBook.author = book.getAuthor();
				verifiedBook.wordCount = book.getWordCount();
				verifiedBook.point = bookInShelf.getPoint();
				verifiedBook.verifiedTime = bookInShelf.getVerifiedTime();
				int categoryId = book.getExtra().getCategory();
				verifiedBook.category = (String) categoriesMap.get(categoryId);
				dto.semesterVerified.add(verifiedBook);
			}
		}
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