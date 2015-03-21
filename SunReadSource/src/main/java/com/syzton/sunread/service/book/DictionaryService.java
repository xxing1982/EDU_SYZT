package com.syzton.sunread.service.book;

import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.book.DictionaryType;

import java.util.List;

/**
 * Created by jerry on 3/21/15.
 */
public interface DictionaryService {

    List<Dictionary> findByType(DictionaryType type);

    Dictionary add(Dictionary dictionary);
}
