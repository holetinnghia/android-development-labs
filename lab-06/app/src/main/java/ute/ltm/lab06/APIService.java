package ute.ltm.lab06;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("newimagesmanager.php")
    Call<MessageModel> getImageSlider(@Field("position") int position);
}