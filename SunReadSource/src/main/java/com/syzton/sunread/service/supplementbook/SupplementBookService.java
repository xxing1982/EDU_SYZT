package com.syzton.sunread.service.supplementbook;

import com.syzton.sunread.dto.supplementbook.SupplementBookDTO;
import com.syzton.sunread.model.supplementbook.SupplementBook;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Morgan-Leon on 3/13/2015.
 */
public interface SupplementBookService {

    public SupplementBook add(SupplementBookDTO dto);

    public SupplementBook findById(Long id) throws NotFoundException;

    public SupplementBook deleteById(Long id) throws NotFoundException;

    Page<SupplementBook> findAll(Pageable pageable) throws NotFoundException;
}
