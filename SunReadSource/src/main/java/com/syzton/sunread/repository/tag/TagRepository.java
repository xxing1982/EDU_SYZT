package com.syzton.sunread.repository.tag;

import com.syzton.sunread.model.tag.Tag;

import com.syzton.sunread.model.tag.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByNameAndType(String name,Type type);
}
