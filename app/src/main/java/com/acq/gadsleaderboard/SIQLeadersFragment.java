package com.acq.gadsleaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acq.gadsleaderboard.Models.IQLeadersModel;
import com.acq.gadsleaderboard.Models.LearningLeadersModel;
import com.acq.gadsleaderboard.Services.IQLeadersService;
import com.acq.gadsleaderboard.Services.LearningLeadersService;
import com.acq.gadsleaderboard.Services.NoConnectivityException;
import com.acq.gadsleaderboard.Services.RetrofitService;
import com.google.android.material.snackbar.Snackbar;

import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SIQLeadersFragment extends Fragment {

    RecyclerView mRecyclerView;
    private View mSIQView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSIQView = inflater.inflate(R.layout.skill_iq_leaders_fragment, container, false);


        String baseUrl = "https://gadsapi.herokuapp.com/";
        mRecyclerView = mSIQView.findViewById(R.id.skilliq_leaders_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        IQLeadersService learningLeadersService =  RetrofitService.retrofitInit(baseUrl, getContext()).create(IQLeadersService.class);
        Call<List<IQLeadersModel>> call = learningLeadersService.getIQLeaders();

        getLeadersData(call);
        return mSIQView;
    }



    void getLeadersData(Call<List<IQLeadersModel>> call){

        call.enqueue(new Callback<List<IQLeadersModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<IQLeadersModel>> call, @NonNull Response<List<IQLeadersModel>> response) {
                if (!response.isSuccessful()){
                    Snackbar.make(mSIQView, "Error fetching data", Snackbar.LENGTH_LONG).show();
                }else {
                    mRecyclerView.setAdapter(new SIQAdapter(getContext() ,response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<IQLeadersModel>> call, @NonNull Throwable t) {
                if (t instanceof NoConnectivityException || t instanceof UnknownHostException){
                    Snackbar.make(mSIQView, "No internet", Snackbar.LENGTH_LONG).show();
                    return;
                }
                Snackbar.make(mSIQView, "Error contacting server", Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
