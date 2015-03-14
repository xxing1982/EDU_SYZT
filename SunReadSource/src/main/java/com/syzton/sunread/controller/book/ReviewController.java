package com.syzton.sunread.controller.book;

import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.book.Review;
import com.syzton.sunread.service.book.ReviewService;
import javassist.NotFoundException;
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
 * Created by jerry on 3/9/15.
 */
@Controller
public class ReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping(value = "/api/reviews", method = RequestMethod.POST)
    @ResponseBody
    public ReviewDTO add(@Valid @RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.add(reviewDTO);
        return review.createDTO(review);
    }

    @RequestMapping(value = "/api/reviews/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ReviewDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Review deleted = reviewService.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }

    @RequestMapping(value = "/api/books/{bookId}/reviews", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Review> findReviewsByBookId(@PathVariable("bookId") long bookId,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size,
                                                    @RequestParam("sortBy") String sortBy) throws NotFoundException {
        sortBy = sortBy == null ? "id" : sortBy;

        Pageable pageable = new PageRequest(
                page, size, new Sort(sortBy)
        );

        Page<Review> reviewPage = reviewService.findByBookId(pageable, bookId);

        return new PageResource<>(reviewPage, "page", "size");
    }
}
