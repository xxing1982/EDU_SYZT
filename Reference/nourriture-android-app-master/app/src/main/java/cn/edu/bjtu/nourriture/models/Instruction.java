package cn.edu.bjtu.nourriture.models;

/**
 * Created by remoteusrr on 05/01/15.
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Instruction {

    @Expose
    private String step;
    @SerializedName("_id")
    @Expose
    private String Id;

    /**
     *
     * @return
     * The step
     */
    public String getStep() {
        return step;
    }

    /**
     *
     * @param step
     * The step
     */
    public void setStep(String step) {
        this.step = step;
    }

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

}