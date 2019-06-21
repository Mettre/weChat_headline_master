package com.chaychan.news.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mettre on 2018/5/23.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
//        String token = MyApp.getInstances().getToken();
//        if (TextUtils.isEmpty(token)) {
//            Request mRequest = chain.request().newBuilder()
//                    .build();
//            return chain.proceed(mRequest);
//        } else {
//            Request mRequest = chain.request().newBuilder()
//                    .header("Authorization", "Bearer " + MyApp.getInstances().getToken())
//                    .build();
//            return chain.proceed(mRequest);
//        }

        Request mRequest = chain.request().newBuilder()
                .build();
        return chain.proceed(mRequest);

    }
}
