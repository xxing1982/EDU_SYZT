package com.syzton.sunread.repository.book;

import com.syzton.sunread.model.book.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jerry on 3/8/15.
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
