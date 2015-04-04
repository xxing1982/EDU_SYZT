package com.syzton.sunread.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by jerry on 3/29/15.
 */
public class BaseController {

    protected Pageable getPageable(int page, int size, String sortBy, String direction) {
        sortBy = sortBy == null ? "id" : sortBy;
        Sort.Direction directionType = direction ==null || !direction.equalsIgnoreCase("desc")? Sort.Direction.ASC: Sort.Direction.DESC;
        return new PageRequest(
                page, size, new Sort(directionType,sortBy)
        );
    }

    protected Pageable getPageable(int page, int size, String sortBy) {
        return this.getPageable(page,size,sortBy,null);
    }
    protected Pageable getPageable(int page, int size) {
        return this.getPageable(page,size,null,null);
    }
}
