package io.mapwize.mapwizecomponents.ui.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherArray {

    @SerializedName("f_id")
    @Expose
    private Integer fId;
    @SerializedName("f_name")
    @Expose
    private String fName;

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

}