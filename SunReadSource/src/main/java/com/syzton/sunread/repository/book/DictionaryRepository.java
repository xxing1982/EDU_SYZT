package com.syzton.sunread.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.book.DictionaryType;

/**
 * Created by jerry on 3/8/15.
 */
public interface DictionaryRepository extends JpaRepository<Dictionary,Long> {

    public List<Dictionary> findByType(DictionaryType type);

    public Dictionary findByTypeAndValue(DictionaryType type,int value);

}
