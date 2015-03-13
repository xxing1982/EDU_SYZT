package com.syzton.sunread.service.bookshelf;

import java.util.List;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.repository.bookshelf.BookInShelfRepository;
import com.syzton.sunread.service.book.BookRepositoryService;

/*
 * @Date 2015/3/12
 * @author Morgan-Leon
 */
@Service
public class BookInShelfRepositoryService implements BookInShelfService{

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryService.class);
    private BookInShelfRepository repository;

    @Autowired
    public BookInShelfRepositoryService(BookInShelfRepository repository) {
        this.repository = repository;
    }
    
	@Override
	public BookInShelf add(BookInShelfDTO added) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookInShelf deleteById(Long id) throws NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookInShelf> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookInShelf findById(Long id) throws NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookInShelf update(BookInShelfDTO updated) throws NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
