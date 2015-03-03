package cn.edu.bjtu.nourriture.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.edu.bjtu.nourriture.utilities.Utilities;

/**
 * Created by Pavel Procházka on 26/12/14.
 */

@Generated("org.jsonschema2pojo")
public class Moment implements Serializable{

    // Constants definition
    private static final long serialVersionUID = 1L;

    // Used in getMomentInfoToDisplay() as hashmap's KEY
    // Used in DetailMomentActivity
    public static final String MOMENT_TEXT      = "text";
    public static final String MOMENT_AUTHOR    = "author";
    public static final String MOMENT_CREATED   = "created";
    public static final String MOMENT_LIKES     = "likes";
    public static final String MOMENT_COMMENTS  = "comments";

    @SerializedName("_id")
    @Expose
    private String Id;
    @Expose
    private Author author;
    @Expose
    private Date created;
    @Expose
    private String text;
    @Expose
    private Integer commentCount;
    @Expose
    private Integer likeCount;
    @Expose
    private List<Comment> comments = new ArrayList<Comment>();
    @Expose
    private List<Like> likes = new ArrayList<Like>();
    @Expose
    private String subjectID;

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The created
     */
    public String getCreated() {
        return Utilities.convertDateToTimeElapsedString(created, new Date());
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The commentCount
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     *
     * @param commentCount
     * The commentCount
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     *
     * @return
     * The likeCount
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     *
     * @param likeCount
     * The likeCount
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     *
     * @return
     * The comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     *
     * @return
     * The likes
     */
    public List<Like> getLikes() {
        return likes;
    }

    /**
     *
     * @param likes
     * The likes
     */
    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    /**
     *
     * @return
     * The subjectID
     */
    public String getSubjectID() {
        return subjectID;
    }

    /**
     *
     * @param subjectID
     * The subjectID
     */
    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }



    public ArrayList<HashMap> getMomentInfoToDisplay(){
        ArrayList<HashMap> result = new ArrayList<>();

        if (text != null && !text.isEmpty()) {
            HashMap textDic = new HashMap();
            textDic.put(Moment.MOMENT_TEXT, text);
            result.add(textDic);
        }

        if (author != null) {
            HashMap authDic = new HashMap();
            authDic.put(Moment.MOMENT_AUTHOR, author.getName());
            result.add(authDic);
        }

        if (created != null) {
            HashMap createdDic = new HashMap();
            createdDic.put(Moment.MOMENT_CREATED, getCreated());
            result.add(createdDic);
        }

        HashMap likesDic = new HashMap();
        likesDic.put(Moment.MOMENT_LIKES, likeCount);
        result.add(likesDic);

        HashMap commentDic = new HashMap();
        commentDic.put(Moment.MOMENT_COMMENTS, commentCount);
        result.add(commentDic);

        return result;
    }
}