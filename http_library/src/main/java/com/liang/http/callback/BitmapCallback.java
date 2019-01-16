package com.liang.http.callback;

import android.graphics.Bitmap;
import com.liang.http.base.ICallBack;
import com.liang.http.base.ReqResult;
import com.liang.http.rxhttp.RxFactory;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * Created by Jianbo on 2018/4/18.
 */

public abstract class BitmapCallback extends ICallBack<Bitmap> {

    @Override
    public ReqResult<Bitmap> convertSuccess(ResponseBody responseBody) throws IOException {
        ReqResult<Bitmap> result = new ReqResult<>();
        result.setCode(0);
        result.setData(RxFactory.transformBitmap(responseBody));
        return result;
    }
}
