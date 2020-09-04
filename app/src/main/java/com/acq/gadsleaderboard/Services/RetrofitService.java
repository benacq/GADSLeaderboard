package com.acq.gadsleaderboard.Services;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.acq.gadsleaderboard.Models.IQLeadersModel;
import com.acq.gadsleaderboard.Models.LearningLeadersModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private String baseUrl;
    private View mView;


    public RetrofitService(String baseUrl, View view){
        this.baseUrl = baseUrl;
        this.mView = view;
    }


    public void getLearningLeadersService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LearningLeadersService learningLeadersService =  retrofit.create(LearningLeadersService.class);
        Call<List<LearningLeadersModel>> call = learningLeadersService.getLearningLeaders();

        call.enqueue( new Callback<List<LearningLeadersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<LearningLeadersModel>> call, @NonNull Response<List<LearningLeadersModel>> response) {
                if (!response.isSuccessful()){
                    Log.d("ERROR", "*******************************" + response.code() +"**********************************");
                    Snackbar.make(mView, "Error fetching data", Snackbar.LENGTH_LONG).show();
                }else {
                    Log.d("SUCCESS", "******************************* DATA FETCHED SUCCESSFULLY **********************************");
                    Snackbar.make(mView, "Data successfully fetched", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LearningLeadersModel>> call, @NonNull Throwable t) {
                Log.d("ERROR", "******************************* ERROR CONTACTING SERVER **********************************");
                t.printStackTrace();
            }
        });
    }


    public void getIQLeadersService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IQLeadersService iqLeadersService =  retrofit.create(IQLeadersService.class);
        Call<List<IQLeadersModel>> call = iqLeadersService.getIQLeaders();

        call.enqueue(new Callback<List<IQLeadersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<IQLeadersModel>> call, @NonNull Response<List<IQLeadersModel>> response) {
                if (!response.isSuccessful()){
                    Snackbar.make(mView, "Error fetching data", Snackbar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(mView, "Data successfully fetched", Snackbar.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<IQLeadersModel>> call, Throwable t) {
                Snackbar.make(mView, "COULD NOT CONNECT TO SERVER", Snackbar.LENGTH_LONG).show();
            }
        });

    }


}
