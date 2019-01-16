package com.liang.httptest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.liang.http.HttpRequest
import com.liang.http.callback.HttpCallback
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var parameters: MutableMap<String, String> = HashMap()
        parameters["ip"] = "21.22.11.33"
        button.setOnClickListener {
            HttpRequest.getInstance().get(
                "https://www.taobao.com/",
                object : HttpCallback<String>() {
                    override fun onSuccess(code: Int, result: String?, msg: String?) {
                        sample_text.text = result
                    }

                    override fun onFailure(code: Int, msg: String?) {
                        sample_text.text = "请求失败：code-$code;msg-$msg"
                    }
                })
        }

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
