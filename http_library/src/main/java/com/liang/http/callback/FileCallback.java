package com.liang.http.callback;


import com.liang.http.base.ICallBack;
import com.liang.http.base.ReqResult;
import com.liang.http.rxhttp.RxFactory;
import com.liang.http.utils.Logger;
import okhttp3.ResponseBody;

/**
 * Created by Jianbo on 2018/4/18.
 */

public abstract class FileCallback extends ICallBack<String> {

    public String fileDir() {
        return "";
    }

    public String fileName() {
        return "";
    }

    @Override
    public ReqResult<String> convertSuccess(ResponseBody responseBody) throws Exception {
        Logger.e("FileCallback", responseBody.contentType() + "");
        String path = RxFactory.transformFile(responseBody, fileDir(), fileName(), this);
        ReqResult<String> result = new ReqResult();
        result.setCode(0);
        result.setData(path);
        return result;
    }
}
