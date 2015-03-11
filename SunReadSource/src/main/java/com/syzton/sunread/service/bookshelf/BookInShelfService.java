package com.syzton.sunread.service.bookshelf;

import java.util.List;

import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.exception.bookshelf.BookInShelfNotFoundException;
import com.syzton.sunread.model.bookshelf.BookInShelf;

/**
 * @author Morgan-Leon
 */
public interface BookInShelfService {
	
    /**
     * Adds a new bookshelf entry.
     * @param added The information of the added bookshelf entry.
     * @return  The added bookshelf entry.
     */
    public BookInShelf add(BookInShelfDTO added);

    /**
     * Deletes a bookshelf entry.
     * @param id    The id of the deleted bookshelf entry.
     * @return  The deleted bookshelf entry.
     * @throws com.syzton.sunread.BookInShelf.exception.BookInShelfNotFoundException if no bookshelf entry is found with the given id.
     */
    public BookInShelf deleteById(Long id) throws BookInShelfNotFoundException;

    /**
     * Returns a list of bookshelf entries.
     * @return
     */
    public List<BookInShelf> findAll();

    /**
     * Finds a bookshelf entry.
     * @param id    The id of the wanted bookshelf entry.
     * @return  The found to-entry.
     * @throws BookInShelfNotFoundException    if no bookshelf entry is found with the given id.
     */
    public BookInShelf findById(Long id) throws BookInShelfNotFoundException;

    /**
     * Updates the information of a bookshelf entry.
     * @param updated   The information of the updated bookshelf entry.
     * @return  The updated bookshelf entry.
     * @throws BookInShelfNotFoundException    If no bookshelf entry is found with the given id.
     */
    public BookInShelf update(BookInShelfDTO updated) throws BookInShelfNotFoundException;

}