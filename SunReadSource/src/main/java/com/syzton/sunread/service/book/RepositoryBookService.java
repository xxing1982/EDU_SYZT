package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.todo.dto.TodoDTO;
import com.syzton.sunread.todo.exception.TodoNotFoundException;
import com.syzton.sunread.todo.model.Todo;
import com.syzton.sunread.todo.repository.TodoRepository;
import com.syzton.sunread.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Petri Kainulainen
 */
@Service
public class RepositoryBookService implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryBookService.class);

    private BookRepository repository;

    @Autowired
    public RepositoryBookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book add(BookDTO added) {
        LOGGER.debug("Adding a new Book entry with information: {}", added);

        Book bookModel = Book.getBuilder(added.getIsbn(),added.getName())
                .description(added.getDescription())
                .build();
      // Book bookModel = new Book();
        return repository.save(bookModel);
      //  return  new Book();
    }


}
