package io.mapwize.mapwizecomponents.ui.API;

 import java.util.List;
 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

public class ScheduleData<S> {

    @SerializedName("ScheduleArray")
    @Expose
    private List<ScheduleArray> scheduleArray = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ScheduleArray> getScheduleArray() {
        return scheduleArray;
    }

    public void setScheduleArray(List<ScheduleArray> scheduleArray) {
        this.scheduleArray = scheduleArray;
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