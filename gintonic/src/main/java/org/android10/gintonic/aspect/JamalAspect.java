package org.android10.gintonic.aspect;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Tony Shen on 16/3/23.
 */
@Aspect
public class JamalAspect {

    private static final String TAG = "zhoumao";
    private static final String POINTCUT_HttpClient_Execute = "call(* *..*.HttpClient.execute(..))";
    private static final String POINTCUT_Okhttp_EXCUTION = "call(* okhttp3.Call.execute(..))";
    // 也可以  "call(* *.Call.execute(..))"


    private static final String POINTCUT_OKHTTP_RESPONSE = "execution(* *..*.onResponse*(..))";


    @Pointcut(POINTCUT_OKHTTP_RESPONSE)
    public void onResponse() {
    }

    @Pointcut(POINTCUT_HttpClient_Execute)
    public void httpClientExecute() {
    }

    @Pointcut(POINTCUT_Okhttp_EXCUTION)
    public void okHttpExecute() {
    }


    @Around("onResponse()")
    public Object onResponseDo(ProceedingJoinPoint joinPoint) throws Throwable {

        for (Object object : joinPoint.getArgs()) {
            handleOkHttpResponse(object, "onResponseDo");
        }
        return joinPoint.proceed();
    }

    @Around("okHttpExecute()")
    public Object okHttpExecuteDo(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.proceed();
        handleOkHttpResponse(object, "okHttpExecuteDo");
        return object;
    }


    @Around("httpClientExecute()")
    public Object httpClientExecuteDo(ProceedingJoinPoint joinPoint) throws Throwable {
        for (Object object : joinPoint.getArgs()) {
            if (object instanceof HttpGet) {
                Log.d(TAG, "httpClientExecuteDo: " + ((HttpGet) object).getURI());
            }
        }
        Object object = joinPoint.proceed();
        if (object instanceof HttpResponse) {
            Log.d(TAG, "httpClientExecute: " + ((HttpResponse) object).getEntity().toString());
        }
        return object;
    }


    private void handleOkHttpResponse(Object object, String tag) {
        if (object instanceof Response) {
            Response response = ((Response) object);
            Log.d(TAG, tag+"  :  " + object);
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                    Log.d(TAG, tag + "OkHttp: " + responseBody);
            }
        }
    }
}