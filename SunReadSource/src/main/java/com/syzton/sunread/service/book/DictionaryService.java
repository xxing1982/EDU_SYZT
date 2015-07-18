package com.syzton.sunread.service.book;

import java.util.List;

import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.book.DictionaryType;

/**
 * Created by jerry on 3/21/15.
 */
public interface DictionaryService {

    List<Dictionary> findByType(DictionaryType type);

    Dictionary add(Dictionary dictionary);
}
