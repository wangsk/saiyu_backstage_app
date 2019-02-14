package com.saiyu.transactions.https;

import com.saiyu.transactions.consts.ConstValue;
import com.saiyu.transactions.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestHeaderInterceptor implements Interceptor {
    public RequestHeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        final long time = System.currentTimeMillis();
        Request updateRequest = originalRequest.newBuilder()
                .header("appId", ConstValue.APPID)
                .header("accessToken", SPUtils.getString("accessToken", ""))
                .build();

        return chain.proceed(updateRequest);
    }
}
