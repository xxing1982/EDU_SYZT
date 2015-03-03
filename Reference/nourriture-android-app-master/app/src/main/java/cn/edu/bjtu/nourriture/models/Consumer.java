package cn.edu.bjtu.nourriture.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pavel Procházka on 26/12/14.
 */

@Generated("org.jsonschema2pojo")
public class Consumer {

    // Constants definition
    public static final String CONSUMER_ID = "id";
    public static final String CONSUMER_USERNAME = "username";
    public static final String CONSUMER_NAME = "name";
    public static final String CONSUMER_PICTURE = "picture";
    public static final String CONSUMER_OCCUPATION = "occupation";
    public static final String CONSUMER_BIRTHDATE = "birthdate";
    public static final String CONSUMER_WEBSITE = "website";
    public static final String CONSUMER_BIO = "bio";
    public static final String CONSUMER_EMAIL = "email";
    public static final String CONSUMER_GENDER = "gender";

    @SerializedName("_id")
    @Expose
    private String Id;
    @Expose
    private String cId; //used when refering to a consumer in FollowingRelation
    @Expose
    private String created;
    @Expose
    private String username;
    @Expose
    private String email;
    @Expose
    private String name;
    @Expose
    private String picture;
    @Expose
    private String bio;
    @Expose
    private String occupation;
    @Expose
    private Integer gender;
    @Expose
    private String birthday;
    @Expose
    private String website;
    @Expose
    private List<Object> following = new ArrayList<Object>();

    private ArrayList<HashMap> consumerInfoToDisplay = null;   //consumer may provide different amount of information, therefore we return only the provided one

    /**
     * @return The Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return The cId
     */
    public String getcId() {
        return cId;
    }

    /**
     * @param Id The _cId
     */
    public void setcId(String cId) {
        this.cId = cId;
    }

    /**
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture The picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * @return The bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * @param bio The bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * @return The occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation The occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return The gender
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return The following
     */
    public List<Object> getFollowing() {
        return following;
    }

    /**
     * @param following The following
     */
    public void setFollowing(List<Object> following) {
        this.following = following;
    }



    // CUSTOM GETTER
    public ArrayList<HashMap> getConsumerInfoToDisplay() {

        if (consumerInfoToDisplay == null) {
            consumerInfoToDisplay = findAvailableInfo();
        }

        return consumerInfoToDisplay;
    }

    private ArrayList findAvailableInfo() {
        ArrayList<HashMap> result = new ArrayList<>();

        if (picture != null && !picture.isEmpty()) {
            HashMap picDic = new HashMap();
            picDic.put(Consumer.CONSUMER_PICTURE, picture);
            result.add(picDic);
        }

        if (username != null && !username.isEmpty()) {
            HashMap usernameDic = new HashMap();
            usernameDic.put(Consumer.CONSUMER_USERNAME, username);
            result.add(usernameDic);
        }

        if (name != null && !name.isEmpty()) {
            HashMap nameDic = new HashMap();
            nameDic.put(Consumer.CONSUMER_NAME, name);
            result.add(nameDic);
        }

        if (occupation != null && !occupation.isEmpty()) {
            HashMap occupationDic = new HashMap();
            occupationDic.put(Consumer.CONSUMER_OCCUPATION, occupation);
            result.add(occupationDic);
        }

        /*if (birthday != null && birthday != null) {
            HashMap birthdateDic = new HashMap();
            birthdateDic.put(Consumer.CONSUMER_BIRTHDATE, birthday.toString());
            result.add(birthdateDic);
        }*/

        if (website != null && !website.isEmpty()) {
            HashMap websiteDic = new HashMap();
            websiteDic.put(Consumer.CONSUMER_WEBSITE, website);
            result.add(websiteDic);
        }

        if (bio != null && !bio.isEmpty()) {
            HashMap bioDic = new HashMap();
            bioDic.put(Consumer.CONSUMER_BIO, bio);
            result.add(bioDic);
        }

        /* //I DONT WANNA PRINT OUT EMAIL and GENDER
        if (this.consumerEmail != null && !this.consumerEmail.isEmpty()){
            HashMap emailDic = new HashMap();
            emailDic.put(Consumer.CONSUMER_EMAIL, this.consumerEmail);
            result.add(emailDic);
        }

        if (this.consumerGender != 0){
            HashMap genderDic = new HashMap();
            genderDic.put(Consumer.CONSUMER_GENDER, "" + this.consumerGender);
            result.add(genderDic);
        }*/

        return result;
    }
}
