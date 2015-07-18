package com.syzton.sunread.dto.book;

import javax.validation.constraints.NotNull;

import com.syzton.sunread.dto.common.AbstractDTO;
import com.syzton.sunread.model.book.Binding;

/**
 * Created by jerry on 3/8/15.
 */
public class BookDTO  extends AbstractDTO{


    private String isbn;

    private String name;

    private String pictureUrl;

    private String description;

    private Long publicationDate;

    private String author;

    private String publisher;

    private int pageCount;

    private int wordCount;

    private int point;

    private int coin;

    private int price;

    private int highPrice;

    private Binding binding;

    private String authorIntroduction;

    private int evaluationNum;

    @NotNull
    private BookExtraDTO extra;

    public BookExtraDTO getExtra() {
        return extra;
    }

    public void setExtra(BookExtraDTO extra) {
        this.extra = extra;
    }

    public Long getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Long publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(int highPrice) {
        this.highPrice = highPrice;
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }


    public String getAuthorIntroduction() {
        return authorIntroduction;
    }

    public void setAuthorIntroduction(String authorIntroduction) {
        this.authorIntroduction = authorIntroduction;
    }

    public int getEvaluationNum() {
        return evaluationNum;
    }

    public void setEvaluationNum(int evaluationNum) {
        this.evaluationNum = evaluationNum;
    }
}
