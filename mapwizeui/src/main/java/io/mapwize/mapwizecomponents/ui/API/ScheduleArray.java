package io.mapwize.mapwizecomponents.ui.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleArray {

    @SerializedName("r_name")
    @Expose
    private String rName;
    @SerializedName("t_time")
    @Expose
    private String tTime;
    @SerializedName("t_shift")
    @Expose
    private String tShift;
    @SerializedName("t_section")
    @Expose
    private String tSection;
    @SerializedName("t_day")
    @Expose
    private String tDay;
    @SerializedName("t_course")
    @Expose
    private String tCourse;
    @SerializedName("t_degree")
    @Expose
    private String tDegree;
    @SerializedName("t_department")
    @Expose
    private String tDepartment;
    @SerializedName("t_semester")
    @Expose
    private Integer tSemester;

    public String getRName() {
        return rName;
    }

    public void setRName(String rName) {
        this.rName = rName;
    }

    public String getTTime() {
        return tTime;
    }

    public void setTTime(String tTime) {
        this.tTime = tTime;
    }

    public String getTShift() {
        return tShift;
    }

    public void setTShift(String tShift) {
        this.tShift = tShift;
    }

    public String getTSection() {
        return tSection;
    }

    public void setTSection(String tSection) {
        this.tSection = tSection;
    }

    public String getTDay() {
        return tDay;
    }

    public void setTDay(String tDay) {
        this.tDay = tDay;
    }

    public String getTCourse() {
        return tCourse;
    }

    public void setTCourse(String tCourse) {
        this.tCourse = tCourse;
    }

    public String getTDegree() {
        return tDegree;
    }

    public void setTDegree(String tDegree) {
        this.tDegree = tDegree;
    }

    public String getTDepartment() {
        return tDepartment;
    }

    public void setTDepartment(String tDepartment) {
        this.tDepartment = tDepartment;
    }

    public Integer getTSemester() {
        return tSemester;
    }

    public void setTSemester(Integer tSemester) {
        this.tSemester = tSemester;
    }

}