package io.mapwize.mapwizecomponents.ui.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FacultyLoginServices {
    @FormUrlEncoded
    @POST(EndPoint.FACULTY_LOGIN)
    Call<FacultyLogin> FacultyLogin(@Field("email") String email,
                                    @Field("password") String password);
}

