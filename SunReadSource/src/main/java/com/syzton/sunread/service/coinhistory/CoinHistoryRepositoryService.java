package com.syzton.sunread.service.coinhistory;

import java.util.List;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.repository.coinhistory.CoinHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author chenty
 *
 */
@Service
public class CoinHistoryRepositoryService implements CoinHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinHistoryRepositoryService.class);
    private CoinHistoryRepository repository;

    @Autowired
    public CoinHistoryRepositoryService(CoinHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CoinHistory add(CoinHistory add) {
        LOGGER.debug("Adding a new coinHistory entry with information: {}", add);
        return repository.save(add);
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public CoinHistory deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a coinHistory entry with id: {}", id);

        CoinHistory deleted = findById(id);
        LOGGER.debug("Deleting coinHistory entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CoinHistory> findAll() {
        LOGGER.debug("Finding all coinHistory entries");
        return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public CoinHistory findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a coinHistory entry with id: {}", id);

        CoinHistory found = repository.findOne(id);
        LOGGER.debug("Found coinHistory entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No coinHistory entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public CoinHistory update(CoinHistory update) throws NotFoundException {
        LOGGER.debug("Updating contact with information: {}", update);

        CoinHistory model = findById(update.getId());
        LOGGER.debug("Found a coinHistory entry: {}", model);
        model.setCoinType(update.getCoinType());
        model.setCoinFrom(update.getCoinFrom());        
        return model;
    }
}
