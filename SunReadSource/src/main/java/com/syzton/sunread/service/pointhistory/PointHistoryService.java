package com.syzton.sunread.service.pointhistory;

import java.util.List;

import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.pointhistory.PointHistory;

/**
 * @author chenty
 *
 */
public interface PointHistoryService {

    /**
     * Adds a new pointHistory entry.
     * @param added The information of the added pointHistory entry.
     * @return  The added pointHistory entry.
     */
    public PointHistory add(PointHistory added);

    /**
     * Deletes a pointHistory entry.
     * @param id    The id of the deleted pointHistory entry.
     * @return  The deleted pointHistory entry.
     * @throws com.syzton.sunread.exception.pointHistory.BookPointHistoryNotFoundException    if no pointHistory entry is found with the given id.
     */
    public PointHistory deleteById(Long id) throws NotFoundException;

    /**
     * Returns a list of pointHistory entries.
     * @return
     */
    public List<PointHistory> findAll();

    /**
     * Finds a pointHistory entry.
     * @param id    The id of the wanted pointHistory entry.
     * @return  The found pointHistory entry.
     * @throws PointHistoryNotFoundException    if no pointHistory entry is found with the given id.
     */
    public PointHistory findById(Long id) throws NotFoundException;

    /**
     * Updates the information of a pointHistory entry.
     * @param updated   The information of the updated pointHistory entry.
     * @return  The updated pointHistory entry.
     * @throws PointHistoryNotFoundException    If no pointHistory entry is found with the given id.
     */
    public PointHistory update(PointHistory updated) throws NotFoundException;

    public List<PointHistory> findByStudentId(Long StudentId);

	public List<PointHistory> findBySemesterId(long teacherId);
}
