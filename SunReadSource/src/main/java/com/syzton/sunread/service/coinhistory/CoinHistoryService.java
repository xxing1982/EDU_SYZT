package com.syzton.sunread.service.coinhistory;

import java.util.List;
import com.syzton.sunread.exception.coinhistory.CoinHistoryNotFoundException;
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
    public CoinHistory deleteById(Long id) throws CoinHistoryNotFoundException;

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
    public CoinHistory findById(Long id) throws CoinHistoryNotFoundException;

    /**
     * Updates the information of a coinHistory entry.
     * @param updated   The information of the updated coinHistory entry.
     * @return  The updated coinHistory entry.
     * @throws CoinHistoryNotFoundException    If no coinHistory entry is found with the given id.
     */
    public CoinHistory update(CoinHistory updated) throws CoinHistoryNotFoundException;


}
