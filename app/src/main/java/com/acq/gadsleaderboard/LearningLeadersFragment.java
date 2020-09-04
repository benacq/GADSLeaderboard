package com.acq.gadsleaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acq.gadsleaderboard.Models.LearningLeadersModel;
import com.acq.gadsleaderboard.Services.LearningLeadersService;
import com.acq.gadsleaderboard.Services.NetworkConnectionInterceptor;
import com.acq.gadsleaderboard.Services.NoConnectivityException;
import com.google.android.material.snackbar.Snackbar;

import java.net.UnknownHostException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LearningLeadersFragment extends Fragment {
    LearningLeadersAdapter mAdapter;
    RecyclerView mRecyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View learningLeadersView = inflater.inflate(R.layout.learning_leaders_fragment, container, false);
        String baseUrl = "https://gadsapi.herokuapp.com/";
        mRecyclerView = learningLeadersView.findViewById(R.id.learning_leaders_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new NetworkConnectionInterceptor(getContext()))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LearningLeadersService learningLeadersService =  retrofit.create(LearningLeadersService.class);
        Call<List<LearningLeadersModel>> call = learningLeadersService.getLearningLeaders();

        call.enqueue( new Callback<List<LearningLeadersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<LearningLeadersModel>> call, @NonNull Response<List<LearningLeadersModel>> response) {
                if (!response.isSuccessful()){
                    Snackbar.make(learningLeadersView, "Error fetching data", Snackbar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(learningLeadersView, "Data successfully fetched", Snackbar.LENGTH_LONG).show();
                    mAdapter = new LearningLeadersAdapter(getContext() ,response.body());
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LearningLeadersModel>> call, @NonNull Throwable t) {
                if (t instanceof NoConnectivityException || t instanceof UnknownHostException){
                    Snackbar.make(learningLeadersView, "No internet", Snackbar.LENGTH_LONG).show();
                    return;
                }
                Snackbar.make(learningLeadersView, "Error contacting server", Snackbar.LENGTH_LONG).show();
            }
        });

        return learningLeadersView;
    }
}
