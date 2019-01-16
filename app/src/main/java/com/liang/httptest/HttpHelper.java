package com.liang.httptest;

import com.liang.http.rxhttp.RxRequest;

public class HttpHelper extends RxRequest {
    @Override
    public String baseUrl() {
        return "http://www.taobao.com/";
    }
}
