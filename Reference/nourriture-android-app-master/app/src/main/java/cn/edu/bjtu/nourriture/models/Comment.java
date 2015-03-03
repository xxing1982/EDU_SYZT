package cn.edu.bjtu.nourriture.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import cn.edu.bjtu.nourriture.utilities.Utilities;

/**
 * Created by Pavel Procházka on 03/01/15.
 */

@Generated("org.jsonschema2pojo")
public class Comment implements Serializable{

    // Constants definition
    private static final long serialVersionUID = 1L;

    @SerializedName("_id")
    @Expose
    private String Id;
    @Expose
    private String text;
    @Expose
    private Date created;
    @Expose
    private List<Object> likes = new ArrayList<Object>();

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
     * The likes
     */
    public List<Object> getLikes() {
        return likes;
    }

    /**
     *
     * @param likes
     * The likes
     */
    public void setLikes(List<Object> likes) {
        this.likes = likes;
    }

}