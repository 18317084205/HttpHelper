package com.liang.http.okhttp;

import com.liang.http.utils.Logger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jianbo on 2018/4/10.
 */

public class HeadersInterceptor implements Interceptor {
    private Map<String, String> headers;

    public HeadersInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();
        Logger.e(builder.toString());
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey) == null? "": headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());

    }
}
