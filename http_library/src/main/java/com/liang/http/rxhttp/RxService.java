package com.liang.http.rxhttp;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by Jianbo on 2018/4/10.
 */

public interface RxService {
    @Streaming
    @GET
    Observable<ResponseBody> get(@Url String url);

    @GET
    Observable<ResponseBody> getResponse(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postResponse(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> parameter);

}
