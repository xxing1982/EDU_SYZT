package com.syzton.sunread.service.tag;

import java.util.List;

import com.syzton.sunread.dto.tag.TagDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.tag.Tag;

/**
 * @author chenty
 *
 */
public interface TagService {

    /**
     * Adds a new tag entry.
     * @param added The information of the added tag entry.
     * @return  The added tag entry.
     */
    public Tag add(TagDTO added);

    /**
     * Deletes a tag entry.
     * @param id    The id of the deleted tag entry.
     * @return  The deleted tag entry.
     * @throws com.syzton.sunread.exception.tag.BookNotFoundException    if no tag entry is found with the given id.
     */
    public Tag deleteById(Long id) throws NotFoundException;

    /**
     * Returns a list of tag entries.
     * @return
     */
    public List<Tag> findAll();

    /**
     * Finds a tag entry.
     * @param id    The id of the wanted tag entry.
     * @return  The found tag entry.
     * @throws NotFoundException    if no tag entry is found with the given id.
     */
    public Tag findById(Long id) throws NotFoundException;

    /**
     * Updates the information of a tag entry.
     * @param updated   The information of the updated tag entry.
     * @return  The updated tag entry.
     * @throws NotFoundException    If no tag entry is found with the given id.
     */
    public Tag update(TagDTO updated) throws NotFoundException;


}
