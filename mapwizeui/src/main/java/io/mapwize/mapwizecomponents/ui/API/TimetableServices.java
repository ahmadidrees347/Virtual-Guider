package io.mapwize.mapwizecomponents.ui.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TimetableServices {
    @FormUrlEncoded
    @POST(EndPoint.SHOW_TIMETABLE)
    Call<Timetable<TimetableArray>> showTimetable(@Field("t_shift") String t_shift,
                                                  @Field("t_section") String t_section,
                                                  @Field("t_day") String t_day,
                                                  @Field("t_degree") String t_degree,
                                                  @Field("t_department") String t_department,
                                                  @Field("t_semester") String t_semester);
}
