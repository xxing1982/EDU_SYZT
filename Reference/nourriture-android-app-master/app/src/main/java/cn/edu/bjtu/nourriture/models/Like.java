package cn.edu.bjtu.nourriture.models;

/**
 * Created by Pavel Procházka on 04/01/15.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

@Generated("org.jsonschema2pojo")
public class Like implements Serializable{

    // Constants definition
    private static final long serialVersionUID = 1L;

    @Expose
    private String cId;
    @Expose
    private String name;

    /**
     *
     * @return
     * The cId
     */
    public String getCId() {
        return cId;
    }

    /**
     *
     * @param cId
     * The cId
     */
    public void setCId(String cId) {
        this.cId = cId;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

}
