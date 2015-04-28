package com.syzton.sunread.service.book;

import java.awt.Point;
import java.io.UnsupportedEncodingException;
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
import com.syzton.sunread.model.book.Binding;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.BookExtra;
import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.book.DictionaryType;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User.GenderType;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.CategoryRepository;
import com.syzton.sunread.repository.book.DictionaryRepository;

import org.apache.poi.ss.usermodel.Cell;
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
    
    private DictionaryRepository dictionaryRepo;



    @Autowired
    public BookRepositoryService(BookRepository bookRepo,CategoryRepository categoryRepo,DictionaryRepository dictionaryRepo)
    {
        this.bookRepo = bookRepo;
        this.categoryRepo = categoryRepo;
        this.dictionaryRepo = dictionaryRepo;
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

    @Transactional
	@Override
	public List<Integer> batchSaveOrUpdateBookFromExcel(Sheet sheet) {
		List<Dictionary> grades = dictionaryRepo.findByType(DictionaryType.GRADE);
		List<Dictionary> literatures = dictionaryRepo.findByType(DictionaryType.LITERATURE);
		List<Dictionary> languages = dictionaryRepo.findByType(DictionaryType.LANGUAGE);
		List<Dictionary> categorys = dictionaryRepo.findByType(DictionaryType.CATEGORY);
		
		List<Integer> list = new ArrayList<Integer>();
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
	 
			Row row = sheet.getRow(i);  
			//column 0 id is useful? should update book from id or ignore the column
			String isbn = row.getCell(1).toString();
			
			if("".equals(isbn)||(isbn==null)){
				return new ArrayList<Integer>();
			}
			Book book = bookRepo.findByIsbn(isbn);
			if(book == null){
				book = new Book();
			}
			BookExtra extra = book.getExtra();
			if(extra == null){
				extra = new BookExtra();
				book.setExtra(extra);
			}
			book.setIsbn(isbn);
		    book.setName(getStringFromExcelCell(row.getCell(2)));
		    LOGGER.debug("compare:婴儿画报精品故事书（蓝莓蓝）:"+book.getName().equals("婴儿画报精品故事书（蓝莓蓝）"));
		    LOGGER.debug(book.getName());
		    LOGGER.debug(row.getCell(2).toString());
		    book.setName("婴儿画报精品故事书（蓝莓蓝）");
		    book.setAuthor(getStringFromExcelCell(row.getCell(3)));
		    book.setPrice(getFloatFromExcelCell(row.getCell(4)));
		    book.setPublisher(row.getCell(5).toString());
		    book.setPublicationDate(new DateTime(getStringFromExcelCell(row.getCell(6))));
		    book.setPictureUrl(getStringFromExcelCell(row.getCell(7)));
		    book.setPageCount(getIntFromExcelCell(row.getCell(8)));
		    book.setWordCount(getIntFromExcelCell(row.getCell(9)));
		    //Can't find packaging field in book model
		    String binding = getStringFromExcelCell(row.getCell(10));
		    if(binding == "精装"){
		    	book.setBinding(Binding.softback);
		    }else{
		    	book.setBinding(Binding.hardback);
		    }
		    book.setDescription(getStringFromExcelCell(row.getCell(11)));
		    book.setCatalogue(getStringFromExcelCell(row.getCell(12)));
		    book.setAuthorIntroduction(getStringFromExcelCell(row.getCell(13)));
		    //unused field.
		    extra.setAgeRange(0);
		    String gradeKey = getStringFromExcelCell(row.getCell(15));
		    extra.setGrade(getValueFromDic(grades, gradeKey));
		    extra.setLevel(getIntFromExcelCell(row.getCell(16)));
		    
		    String literatureKey = getStringFromExcelCell(row.getCell(17));
		    extra.setLiterature(getValueFromDic(literatures, literatureKey));
		    
		    String languageKey = getStringFromExcelCell(row.getCell(18));
		    extra.setLanguage(getValueFromDic(languages, languageKey));
		    
		    String categoryKey = getStringFromExcelCell(row.getCell(19));
		    extra.setCategory(getValueFromDic(categorys,categoryKey));
		    int coin = getIntFromExcelCell(row.getCell(20));
		    int point = getIntFromExcelCell(row.getCell(21));
		    book.setPoint(point);
		    book.setCoin(coin);
		    int pointRange = 0;
		    if(point<=20&&point>0){
		    	pointRange = point/5;
		    }
		    
		    extra.setPointRange(pointRange);		
		    extra.setHasVideo(getBoolFromExcelCell(row.getCell(22)));
		    extra.setHasRadio(getBoolFromExcelCell(row.getCell(23)));
		    extra.setHasEbook(getBoolFromExcelCell(row.getCell(24)));
		   
		    book.setExtra(extra);
		   
		    book = bookRepo.save(book);
		 
		    
		}  
		return list;
	}
	
	private int getValueFromDic(List<Dictionary> list,String key){
		for(int i=0;i<list.size();i++){
			Dictionary dictionary = list.get(i);
			String name = dictionary.getName();
			if(name.equals(key)){
				return dictionary.getValue();
			}
		}
		
		return -1;
	}
	
	private String getStringFromExcelCell(Cell cell){
		String ret = "";
		try {
			ret =  cell.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private boolean getBoolFromExcelCell(Cell cell){
		boolean ret = false;
		try{
			ret = cell.getBooleanCellValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	private int getIntFromExcelCell(Cell cell){
		int ret = 0;
		try{
			ret = (int)Double.parseDouble(cell.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	private float getFloatFromExcelCell(Cell cell){
		float ret = 0;
		try{
			ret = (float)Double.parseDouble(cell.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
}
