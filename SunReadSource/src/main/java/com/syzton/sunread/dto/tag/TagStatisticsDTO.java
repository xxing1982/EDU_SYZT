package com.syzton.sunread.dto.tag;

/**
 * Created by jerry on 7/21/15.
 */
public class TagStatisticsDTO {

    private String subject;

    private Long tagId;

    private long count;

    public TagStatisticsDTO() {
    }
    public TagStatisticsDTO(String subject, Long tagId, long count) {
        this.subject = subject;
        this.tagId = tagId;
        this.count = count;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
