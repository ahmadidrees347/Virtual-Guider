package io.mapwize.mapwizecomponents.ui.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UpdateStatusServices {
    @FormUrlEncoded
    @POST(EndPoint.UPDATE_STATUS)
    Call<UpdateStatus> UpdateFacultyStatus(@Field("f_name") String f_name,
                                           @Field("f_status") int f_status);
}
