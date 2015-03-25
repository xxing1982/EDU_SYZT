package com.syzton.sunread.service.bookshelf;

import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
import com.syzton.sunread.exception.bookshelf.BookshelfNotFoundException;
import com.syzton.sunread.model.bookshelf.Bookshelf;

/**
 * @author Morgan-Leon
 */
public interface BookshelfService {

    /**
     * Adds a new bookshelf entry.
     * @param added The information of the added bookshelf entry.
     * @return  The added bookshelf entry.
     */
    public Bookshelf add(BookshelfDTO added);

    /**
     * Deletes a bookshelf entry.
     * @param id    The id of the deleted bookshelf entry.
     * @return  The deleted bookshelf entry.
     * @throws com.syzton.sunread.Bookshelf.exception.BookshelfNotFoundException if no bookshelf entry is found with the given id.
     */
    //public Bookshelf deleteById(Long id) throws BookshelfNotFoundException;

    /**
     * Returns a list of bookshelf entries.
     * @return
     */
    //public List<Bookshelf> findAll();

    /**
     * Finds a bookshelf entry.
     * @param id    The id of the wanted bookshelf entry.
     * @return  The found to-entry.
     * @throws BookshelfNotFoundException    if no bookshelf entry is found with the given id.
     */
    public Bookshelf findById(Long id);

    /**
     * Updates the information of a bookshelf entry.
     * @param updated   The information of the updated bookshelf entry.
     * @return  The updated bookshelf entry.
     * @throws BookshelfNotFoundException    If no bookshelf entry is found with the given id.
     */
    public Bookshelf update(BookshelfDTO updated);

}
