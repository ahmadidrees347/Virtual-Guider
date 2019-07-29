package io.mapwize.mapwizecomponents.ui.API;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TeaherNamesServices {
    //@FormUrlEncoded
    @POST(EndPoint.SHOW_TEACHERNAME)
    Call<ShowTeacherNames<TeacherArray>> showTeacherName();
}
