package com.acq.gadsleaderboard.Services;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.acq.gadsleaderboard.Models.IQLeadersModel;
import com.acq.gadsleaderboard.Models.LearningLeadersModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
//    private String baseUrl;
    private View mView;
    private static Retrofit mRetrofit = null;



    public static Retrofit retrofitInit(String baseUrl, Context context){
        if (mRetrofit == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new NetworkConnectionInterceptor(context))
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    return mRetrofit;
    }






}
