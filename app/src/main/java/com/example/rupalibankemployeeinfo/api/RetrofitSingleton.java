package com.example.rupalibankemployeeinfo.api;

import android.content.Context;

import com.example.rupalibankemployeeinfo.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static Context sContext;
    private static Retrofit sRetrofit;
    public static final String BASE_URL = "http://localhost/rbl_hrapi";


    private RetrofitSingleton() {

    }

    public synchronized static Retrofit getInstance(Context context) {
        sContext = context;

        if (sRetrofit == null) {
            createRetrofit();
        }
        return sRetrofit;
    }


    private static void createRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
//                .cache(new Cache(sContext.getCacheDir(), 10 * 1024 * 1024))
                .cache(new Cache(sContext.getCacheDir(),10*1024*1024))
                .build();
        sRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
//                .baseUrl(BuildConfig.HOST)
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//                .baseUrl("http://137.59.48.34:8972/rbl_hrapi/")
                .baseUrl("http://192.168.0.101:8080/rbl_hrapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
