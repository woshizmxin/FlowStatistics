package org.android10.viewgroupperformance;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by zhoumao on 2018/12/6.
 * Description:
 */

public class HttpClientUtil {
    private static final String TAG = "jamal.jo";

    public static void get() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                // get method
                HttpGet httpGet = new HttpGet("https://www.baidu.com/");
                //response
                HttpResponse response = null;
                try {
                    response = httpClient.execute(httpGet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //get response into String
                String temp = "";
                try {
                    HttpEntity entity = response.getEntity();
                    temp = EntityUtils.toString(entity, "UTF-8");
                    Log.d(TAG, "run: " + temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
