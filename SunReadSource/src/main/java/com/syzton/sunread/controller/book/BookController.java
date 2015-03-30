package com.syzton.sunread.controller.book;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.BookExtra;
import com.syzton.sunread.service.book.BookService;
import com.syzton.sunread.service.book.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Jerry Zhang
 * @date 2015-03-05
 */
@Controller
@RequestMapping(value = "/api")
public class BookController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    private RecommendationService recommendationService;

    @Autowired
    public BookController(BookService bookService, RecommendationService qualityService) {
        this.bookService = bookService;
        this.recommendationService = qualityService;
    }


    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseBody
    public BookDTO add(@Valid @RequestBody BookDTO bookDTO) {
        LOGGER.debug("Adding a new book entry with information: {}", bookDTO);

        BookDTO added = bookService.add(bookDTO);

        LOGGER.debug("Added a book entry with information: {}", added);

        return added;
    }

//    @RequestMapping(value = "/books", method = RequestMethod.GET)
//    @ResponseBody
//    public PageResource<Book> findByCategories(@RequestParam(value = "categories", required = false) String categories,
//                                               @RequestParam("page") int page,
//                                               @RequestParam("size") int size,
//                                               @RequestParam(value = "sortBy", required = false) String sortBy) {
//        LOGGER.debug("Finding to-do entry with id: {}");
//
//        Pageable pageable = getPageable(page, size, sortBy);
//        Page<Book> pageResult;
//        if (categories != null) {
//            String[] cIds = categories.split(",");
//            Set<Long> categoryIds = new HashSet<>();
//            for (String id : cIds) {
//                categoryIds.add(Long.parseLong(id));
//            }
//            pageResult = bookService.findByCategories(categoryIds, pageable);
//        } else {
//            pageResult = bookService.findAll(pageable);
//
//        }
//
//        return new PageResource<>(pageResult, "page", "size");
//    }

    @RequestMapping(value = "/books/search", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> quickSearch(@RequestParam("searchTerm") String searchTerm,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size,
                                  @RequestParam(value = "sortBy", required = false) String sortBy) {

        Pageable pageable = getPageable(page, size, sortBy);

        Page<Book> bookPage = bookService.quickSearch(searchTerm, pageable);

        return bookPage;
    }

    @RequestMapping(value = "/books/conditions", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> searchByConditions(
                                         @RequestParam(value = "level", required = false) String level,
                                         @RequestParam(value = "testType", required = false) String testType,
                                         @RequestParam(value = "literature", required = false) String literature,
                                         @RequestParam(value = "language", required = false) String language,
                                         @RequestParam(value = "grade", required = false) String grade,
                                         @RequestParam(value = "category", required = false) String category,
                                         @RequestParam(value = "resource", required = false) String resource,
                                         @RequestParam(value = "ageRange", required = false) String ageRange,
                                         @RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam(value = "sortBy", required = false) String sortBy,
                                         @RequestParam(value = "direction", required = false) String direction) {
        int levelNum = level == null? 0: Integer.parseInt(level);
        int testTypeNum = testType == null? 0: Integer.parseInt(testType);
        int literatureNum = literature == null? 0: Integer.parseInt(literature);
        int languageNum = language == null? 0: Integer.parseInt(language);
        int gradeNum = grade == null? 0: Integer.parseInt(grade);
        int categoryNum = category == null? 0: Integer.parseInt(category);
        int resourceNum = resource == null? 0: Integer.parseInt(resource);
        int ageRangeNum = ageRange == null? 0: Integer.parseInt(ageRange);


        Pageable pageable = getPageable(page, size, sortBy,direction);
        BookExtraDTO condition = new BookExtraDTO();
        condition.setAgeRange(ageRangeNum);
        condition.setLevel(levelNum);
        condition.setLiterature(literatureNum);
        condition.setLanguage(languageNum);
        condition.setCategory(categoryNum);
        condition.setGrade(gradeNum);
        condition.setTestType(testTypeNum);
        condition.setResource(resourceNum);


        Page<Book> bookPage = bookService.searchByCondition(condition, pageable);

        return bookPage;
    }


    @RequestMapping(value = "/books/conditions/weeklyhot", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> orderByWeeklyHot(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "testType", required = false) String testType,
            @RequestParam(value = "literature", required = false) String literature,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "resource", required = false) String resource,
            @RequestParam(value = "ageRange", required = false) String ageRange,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return this.searchByConditions(level,testType,literature,language,grade,category,resource,ageRange,page,size,"hot.weeklyHot","desc");
    }
    @RequestMapping(value = "/books/conditions/monthlyhot", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> orderByMonthlyHot(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "testType", required = false) String testType,
            @RequestParam(value = "literature", required = false) String literature,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "resource", required = false) String resource,
            @RequestParam(value = "ageRange", required = false) String ageRange,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return this.searchByConditions(level,testType,literature,language,grade,category,resource,ageRange,page,size,"hot.monthlyHot","desc");
    }

    @RequestMapping(value = "/books/conditions/yearlyhot", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> orderByYearlyHot(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "testType", required = false) String testType,
            @RequestParam(value = "literature", required = false) String literature,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "resource", required = false) String resource,
            @RequestParam(value = "ageRange", required = false) String ageRange,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return this.searchByConditions(level,testType,literature,language,grade,category,resource,ageRange,page,size,"hot.yearlyHot","desc");
    }

    @RequestMapping(value = "/books/conditions/weeklyRecommend", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> orderByWeeklyRecommend(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "testType", required = false) String testType,
            @RequestParam(value = "literature", required = false) String literature,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "resource", required = false) String resource,
            @RequestParam(value = "ageRange", required = false) String ageRange,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return this.searchByConditions(level,testType,literature,language,grade,category,resource,ageRange,page,size,"hot.weeklyRecommend","desc");
    }
    @RequestMapping(value = "/books/conditions/monthlyRecommend", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> orderByMonthlyRecommend(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "testType", required = false) String testType,
            @RequestParam(value = "literature", required = false) String literature,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "resource", required = false) String resource,
            @RequestParam(value = "ageRange", required = false) String ageRange,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return this.searchByConditions(level,testType,literature,language,grade,category,resource,ageRange,page,size,"hot.monthlyRecommend","desc");
    }

    @RequestMapping(value = "/books/conditions/yearlyRecommend", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> orderByYearlyRecommend(
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "testType", required = false) String testType,
            @RequestParam(value = "literature", required = false) String literature,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "resource", required = false) String resource,
            @RequestParam(value = "ageRange", required = false) String ageRange,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return this.searchByConditions(level,testType,literature,language,grade,category,resource,ageRange,page,size,"hot.yearlyRecommend","desc");
    }
    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Book findById(@PathVariable("id") Long id) {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Book found = bookService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Book deleteById(@PathVariable("id") Long id) {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Book deleted = bookService.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted;
    }

    @RequestMapping(value = "/books/{id}/users/{userId}/recommends", method = RequestMethod.PUT)
    @ResponseBody
    public void recommend(@PathVariable("id") Long id,@PathVariable("userId") Long userId) {

        recommendationService.recommend(id,userId);

    }

}
