package com.syzton.sunread.repository.supplementbook;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.supplementbook.SupplementBook;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Morgan-Leon on 3/12/15.
 */
public interface SupplementBookRepository extends JpaRepository<SupplementBook,Long> {
}
