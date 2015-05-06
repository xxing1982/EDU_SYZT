package com.syzton.sunread.service.coinhistory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.coinhistory.CoinHistory;

/**
 * @author chenty
 *
 */
public interface CoinHistoryService {

    /**
     * Adds a new coinHistory entry.
     * @param added The information of the added coinHistory entry.
     * @return  The added coinHistory entry.
     */
    public CoinHistory add(CoinHistory added);

    /**
     * Deletes a coinHistory entry.
     * @param id    The id of the deleted coinHistory entry.
     * @return  The deleted coinHistory entry.
     * @throws com.syzton.sunread.exception.coinHistory.BookCoinHistoryNotFoundException    if no coinHistory entry is found with the given id.
     */
    public CoinHistory deleteById(Long id) throws NotFoundException;

    /**
     * Returns a list of coinHistory entries.
     * @return
     */
    public List<CoinHistory> findAll();

    /**
     * Finds a coinHistory entry.
     * @param id    The id of the wanted coinHistory entry.
     * @return  The found coinHistory entry.
     * @throws CoinHistoryNotFoundException    if no coinHistory entry is found with the given id.
     */
    public CoinHistory findById(Long id) throws NotFoundException;

    /**
     * Updates the information of a coinHistory entry.
     * @param updated   The information of the updated coinHistory entry.
     * @return  The updated coinHistory entry.
     * @throws CoinHistoryNotFoundException    If no coinHistory entry is found with the given id.
     */
    public CoinHistory update(CoinHistory updated) throws NotFoundException;

    /**
     * Finds a list of coinHistory entries.
     * @param teacherId    The teacherId of the wanted coinHistory entry.
     * @return  The found coinHistory entries.
     * @throws CoinHistoryNotFoundException    if no coinHistory entry is found with the given id.
     */

	public Page<CoinHistory> findByTeacherId(Pageable pageable, Long teacherId) throws NotFoundException;

}
