/**
 * 
 */
package com.syzton.sunread.comparator;

import java.util.Comparator;

import com.syzton.sunread.dto.bookshelf.BookshelfDTO;

/**
 * @author Morgan-Leon
 * @Date 2015年7月1日
 * 
 */
public class BookshelfDTOComparator implements Comparator<BookshelfDTO>{

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(BookshelfDTO s1, BookshelfDTO s2) {
		// TODO Auto-generated method stub
		int booksInS1 = s1.getBookNumberInShelf();
		int booksInS2 = s2.getBookNumberInShelf();
		
		int readBooksInS1 = s1.getReadMust()+s1.getReadSelect() ;
		int readBooksInS2 = s2.getReadMust()+s2.getReadSelect();
		
		/*
		 * @书架排序标准：
		 * 1.书架中书目多的排在前面
		 * 2.如果书目一样多，按已读书的数量排序
		 * 3.如果已读书的数量一样多，按已读书中必读书的数量排序
		 */
		if(booksInS1>booksInS2)
			return 1;
		else if(booksInS1 == booksInS2){
			if (readBooksInS1 > readBooksInS2)
				return 1;
			else if(readBooksInS1 == readBooksInS2){
				if (s1.getReadMust()>s2.getReadMust()) 
					return 1;
				else 
					return -1;
			}
			else	
				return -1;
		}
		else
			return 0;
	}

}
