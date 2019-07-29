package io.mapwize.mapwizecomponents.ui.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ScheduleServices {
    @FormUrlEncoded
    @POST(EndPoint.SHOW_SCHEDULE)
    Call<ScheduleData<ScheduleArray>> showSchedule(@Field("f_name") String f_name,
                                                   @Field("t_day") String t_day);
}
