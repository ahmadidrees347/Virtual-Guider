package io.mapwize.mapwizecomponents.ui.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowTeacherNames<T> {
    @SerializedName("TeacherArray")
    @Expose
    private List<TeacherArray> teacherArray = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<TeacherArray> getTeacherArray() {
        return teacherArray;
    }

    public void setTeacherArray(List<TeacherArray> teacherArray) {
        this.teacherArray = teacherArray;
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
