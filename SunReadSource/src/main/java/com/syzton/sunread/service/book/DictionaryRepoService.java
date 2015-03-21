package com.syzton.sunread.service.book;

import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.book.DictionaryType;
import com.syzton.sunread.repository.book.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jerry on 3/21/15.
 */
@Service
public class DictionaryRepoService implements DictionaryService{

    private DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryRepoService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public List<Dictionary> findByType(DictionaryType type) {

        return dictionaryRepository.findByType(type);
    }

    @Override
    public Dictionary add(Dictionary dictionary) {

        return dictionaryRepository.save(dictionary);
    }
}
