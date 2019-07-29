package io.mapwize.mapwizecomponents.ui.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FacultyLogin {

    @SerializedName("FacultyArray")
    @Expose
    private List<FacultyArray> facultyArray = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FacultyArray> getFacultyArray() {
        return facultyArray;
    }

    public void setFacultyArray(List<FacultyArray> facultyArray) {
        this.facultyArray = facultyArray;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
