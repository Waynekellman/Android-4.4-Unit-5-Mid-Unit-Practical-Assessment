package com.nyc.android_44_unit_5_mid_unit_practical_assessment;

import com.nyc.android_44_unit_5_mid_unit_practical_assessment.model.Users;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Wayne Kellman on 1/24/18.
 */

public interface UserNetwork {
    @GET("?nat=us&inc=name,location,cell,email,dob,picture&results=20")
    Call<Users> getUserList();
}
