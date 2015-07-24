package com.syzton.sunread.dto.common;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public class PageImpl<T> implements Page<T>{
	
	private Page<?> page;
	
	private List<T> list;
	
	public PageImpl(Page<?> page){
		this.page = page;
	}
	
	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return page.getNumber();
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return page.getSize();
	}

	@Override
	public int getTotalPages() {
		// TODO Auto-generated method stub
		return page.getTotalPages();
	}

	@Override
	public int getNumberOfElements() {
		// TODO Auto-generated method stub
		return page.getNumberOfElements();
	}

	@Override
	public long getTotalElements() {
		// TODO Auto-generated method stub
		return page.getTotalElements();
	}

	@Override
	public boolean hasPreviousPage() {
		// TODO Auto-generated method stub
		return page.hasPreviousPage();
	}

	@Override
	public boolean isFirstPage() {
		// TODO Auto-generated method stub
		return page.isFirstPage();
	}

	@Override
	public boolean hasNextPage() {
		// TODO Auto-generated method stub
		return page.hasNextPage();
	}

	@Override
	public boolean isLastPage() {
		// TODO Auto-generated method stub
		return page.isLastPage();
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return list.iterator();
	}

	@Override
	public List<T> getContent() {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public boolean hasContent() {
		// TODO Auto-generated method stub
		return page.hasContent();
	}

	@Override
	public Sort getSort() {
		// TODO Auto-generated method stub
		return page.getSort();
	}
	
	public void setContent(List<T> list){
		this.list = list;
	}
}
