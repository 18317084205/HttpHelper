package com.liang.http;


import com.liang.http.base.Request;
import com.liang.http.okhttp.OkRequest;

/**
 * Created by Jianbo on 2018/4/3.
 */

public class HttpRequest {
    private static Request request;

    public static void init(Request httpRequest) {
        request = httpRequest;
    }

    public static Request getInstance() {
        if (request == null) {
            request = new OkRequest() {
                @Override
                public String baseUrl() {
                    return "";
                }
            };
        }
        return request;
    }
}
