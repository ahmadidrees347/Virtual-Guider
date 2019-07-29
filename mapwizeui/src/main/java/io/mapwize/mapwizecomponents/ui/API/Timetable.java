package io.mapwize.mapwizecomponents.ui.API;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timetable<T> {

    @SerializedName("TimetableArray")
    @Expose
    private List<TimetableArray> timetableArray = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<TimetableArray> getTimetableArray() {
        return timetableArray;
    }

    public void setTimetableArray(List<TimetableArray> timetableArray) {
        this.timetableArray = timetableArray;
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