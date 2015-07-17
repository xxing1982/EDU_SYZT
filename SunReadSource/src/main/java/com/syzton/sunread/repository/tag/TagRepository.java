package com.syzton.sunread.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.model.tag.Type;

/**
 * @author chenty
 *
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByNameAndType(String name,Type type);
}
