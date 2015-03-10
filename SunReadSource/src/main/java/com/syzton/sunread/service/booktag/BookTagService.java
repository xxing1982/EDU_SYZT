package com.syzton.sunread.service.booktag;

import java.util.List;

import com.syzton.sunread.dto.booktag.BookTagDTO;
import com.syzton.sunread.exception.booktag.BookTagNotFoundException;
import com.syzton.sunread.model.booktag.BookTag;

/**
 * @author chenty
 *
 */
public interface BookTagService {

    /**
     * Adds a new booktag entry.
     * @param added The information of the added booktag entry.
     * @return  The added booktag entry.
     */
    public BookTag add(BookTagDTO added);

    /**
     * Deletes a booktag entry.
     * @param id    The id of the deleted booktag entry.
     * @return  The deleted booktag entry.
     * @throws com.syzton.sunread.todo.exception.BookTagNotFoundException    if no booktag entry is found with the given id.
     */
    public BookTag deleteById(Long id) throws BookTagNotFoundException;

    /**
     * Returns a list of booktag entries.
     * @return
     */
    public List<BookTag> findAll();

    /**
     * Finds a booktag entry.
     * @param id    The id of the wanted tag entry.
     * @return  The found booktag entry.
     * @throws BookTagNotFoundException    if no tag entry is found with the given id.
     */
    public BookTag findById(Long id) throws BookTagNotFoundException;

    /**
     * Updates the information of a booktag entry.
     * @param updated   The information of the updated booktag entry.
     * @return  The updated booktag entry.
     * @throws BookTagNotFoundException    If no booktag entry is found with the given id.
     */
    public BookTag update(BookTagDTO updated) throws BookTagNotFoundException;


}
