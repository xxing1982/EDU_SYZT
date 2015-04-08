package com.syzton.sunread.service.bookshelf;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javassist.NotFoundException;

import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.exception.bookshelf.BookInShelfDuplicateVerifiedException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;

/**
 * @author Morgan-Leon
 */

public interface BookInShelfService {

    /**
     * Adds a new bookshelf entry.
     * @param added The information of the added bookshelf entry.
     * @return  The added bookshelf entry.
     */
    public BookInShelf add(BookInShelfDTO added,Long id, Long bookId );

    /**
     * Deletes a bookshelf entry.
     * @param id    The id of the deleted bookshelf entry.
     * @return  The deleted bookshelf entry.
     * @throws com.syzton.sunread.BookInShelf.exception.BookInShelfNotFoundException if no bookshelf entry is found with the given id.
     */
    public BookInShelf deleteById(long id) throws NotFoundException;

    /**
     * Returns a list of bookshelf entries.
     * @return
     */
    public Page<BookInShelf> findByBookshelfId(Pageable pageable,long id);
    
    
    /**
     * Returns a list of bookshelf entries.
     * @return
     */
    public Set<BookInShelf> findByBookshelfId(long id);

    /**
     * Finds a bookshelf entry.
     * @param id    The id of the wanted bookshelf entry.
     * @return  The found to-entry.
     * @throws BookInShelfNotFoundException    if no bookshelf entry is found with the given id.
     */
    public BookInShelf findById(Long id) throws NotFoundException;

    /**
     * Updates the information of a bookshelf entry.
     * @param updated   The information of the updated bookshelf entry.
     * @return  The updated bookshelf entry.
     * @throws BookInShelfNotFoundException    If no bookshelf entry is found with the given id.
     */
    public BookInShelf update(BookInShelfDTO updated,long id);

	/**
	 * @param studentId
	 * @param bookId
	 * @return
	 * @throws BookInShelfDuplicateVerifiedException 
	 */
	boolean updateReadState(Long studentId, Long bookId) throws BookInShelfDuplicateVerifiedException;

	/**
	 * @param book
	 * @return
	 */
	boolean updateByBook(Book book);

	/**
	 * @param book
	 * @return
	 */
	boolean deleteByBook(Book book);

	/**
	 * @param bookshelf
	 * @return
	 */
	boolean deleteByBookshelf(Bookshelf bookshelf);
    
    

}