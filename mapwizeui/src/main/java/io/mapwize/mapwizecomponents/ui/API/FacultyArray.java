package io.mapwize.mapwizecomponents.ui.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacultyArray {

    @SerializedName("f_id")
    @Expose
    private Integer fId;
    @SerializedName("f_name")
    @Expose
    private String fName;
    @SerializedName("f_email")
    @Expose
    private String fEmail;
    @SerializedName("f_pass")
    @Expose
    private String fPass;
    @SerializedName("f_status")
    @Expose
    private String fStatus;

    public Integer getFId() {
        return fId;
    }

    public void setFId(Integer fId) {
        this.fId = fId;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFEmail() {
        return fEmail;
    }

    public void setFEmail(String fEmail) {
        this.fEmail = fEmail;
    }

    public String getFPass() {
        return fPass;
    }

    public void setFPass(String fPass) {
        this.fPass = fPass;
    }

    public String getFStatus() {
        return fStatus;
    }

    public void setFStatus(String fStatus) {
        this.fStatus = fStatus;
    }

}