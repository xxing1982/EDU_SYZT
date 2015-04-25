package com.syzton.sunread.service.book;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.syzton.sunread.assembler.book.BookAssembler;
import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.BookExtra;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User.GenderType;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.CategoryRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.syzton.sunread.repository.book.predicates.BookPredicates.findByCondition;
import static com.syzton.sunread.repository.book.predicates.BookPredicates.quickSearchContains;

/**
 */
@Service
public class BookRepositoryService implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryService.class);

    private BookRepository bookRepo;

    private CategoryRepository categoryRepo;



    @Autowired
    public BookRepositoryService(BookRepository bookRepo,CategoryRepository categoryRepo)
    {
        this.bookRepo = bookRepo;
        this.categoryRepo = categoryRepo;
    }


    @Transactional(rollbackFor = {DuplicateException.class})
    @Override
    public BookDTO add(BookDTO bookDTO) {

        Book exits = bookRepo.findByIsbn(bookDTO.getIsbn());
        if(exits != null){
            throw new DuplicateException("book with isbn: "+bookDTO.getIsbn()+" is already exits..");
        }

        BookAssembler assembler = new BookAssembler();
        Book book =  assembler.fromDTOtoBookWithExtra(bookDTO);

        LOGGER.debug(book.toString());

        book = bookRepo.save(book);
        bookDTO.setId(book.getId());
        return bookDTO;
    }

    @Transactional
    @Override
    public BookDTO update(BookDTO bookDTO,Long bookId) {

        Book exits = bookRepo.findOne(bookId);
        if(exits == null){
            throw new NotFoundException("No book found with id: " + bookId);
        }

        BookAssembler assembler = new BookAssembler();

        assembler.fillBook(bookDTO, exits);

        assembler.fillBookExta(bookDTO.getExtra(),exits.getExtra());

        exits = bookRepo.save(exits);
        bookDTO.setId(exits.getId());
        return bookDTO;
    }


    @Override
    public Book findById(Long id) {
        LOGGER.debug("Finding a book entry with id: {}", id);

        Book found = bookRepo.findOne(id);
        LOGGER.debug("Found book entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No book found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Book deleteById(Long id){
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Book deleted = findById(id);
        LOGGER.debug("Deleting to-do entry: {}", deleted);
        if(deleted == null)
            throw new NotFoundException("No book found with id: " + id);

        bookRepo.delete(deleted);
        return deleted;
    }
    
    @Override
    public Page<Book> findAll(Pageable pageable){

        Page<Book> bookPages = bookRepo.findAll(pageable);

        return bookPages;

    }

//    @Transactional(rollbackFor = {NotFoundException.class})
//    @Override
//    public Page<Book> findByCategories(Set<Long> categoryIds,Pageable pageable){
//
//        Set<Category> categories = new HashSet<>();
//
//        for(Long id : categoryIds)
//        {
//            Category category = categoryRepo.findOne(id);
//            if(category == null){
//                throw new NotFoundException("no category be found wiht id: "+id+"");
//            }
//            categories.add(category);
//        }
//        Page<Book> bookPages = bookRepo.findByCategories(categories,pageable);
//
//        return bookPages;
//    }

    @Transactional(readOnly = true)
    @Override
    public Page<Book> quickSearch(String searchTerm, Pageable pageable) {

        Page<Book> bookPage = bookRepo.findAll(quickSearchContains(searchTerm), pageable);
        if(bookPage.getContent().size()==0)
            throw new NotFoundException("no satisfy book result with this search term : "+searchTerm);
        return bookPage;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Book> searchByCondition(BookExtraDTO condition,Pageable pageable){

        Page<Book> bookPage = bookRepo.findAll(findByCondition(condition),pageable);

        if(bookPage.getContent().size()==0)
            throw new NotFoundException("no satisfy book result with this condition ="+ condition.toString());
        return bookPage;

    }


	@Override
	public List<Book> batchAddBookFromExcel(Sheet sheet) {
		List<Book> list = new ArrayList<Book>();
		for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {  
		    Book book = new Book();
			Row row = sheet.getRow(i);  
			//column 0 id is useful? should update book from id or ignore the column
			book.setIsbn(row.getCell(1).toString());
		    book.setName(row.getCell(2).toString());
		    book.setAuthor(row.getCell(3).toString());
		    book.setPrice(Float.parseFloat(row.getCell(4).toString()));
		    book.setPublisher(row.getCell(5).toString());
		    book.setPublicationDate(new DateTime(row.getCell(6).toString()));
		    //picture contains domain name
		    book.setPictureUrl(row.getCell(7).toString());
		    book.setPageCount(Integer.parseInt(row.getCell(8).toString()));
		    String wordNum = row.getCell(8).toString();
		    book.setWordCount(wordNum==null?0:Integer.parseInt(wordNum));
		    //Can't find packaging field in book model
		    book.setDescription(row.getCell(10).toString());
		    book.setCatalogue(row.getCell(11).toString());
		    BookExtra extra = new BookExtra();
		    //AgeRange is int can't convert rang value to int
		    //type is category or not, May be we need method mapping string type to int category.
		    //Can't find posters	video	evaluationNum	highPraise cloumn
		    book.setAuthorIntroduction(row.getCell(18).toString());
		    book.setExtra(extra);
		    book = bookRepo.save(book);
		    list.add(book);
		}  
		return list;
	}
}
