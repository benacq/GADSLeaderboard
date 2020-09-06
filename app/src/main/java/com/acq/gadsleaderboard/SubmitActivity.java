package com.acq.gadsleaderboard;

import android.os.Bundle;

import com.acq.gadsleaderboard.Models.SubmissionModel;
import com.acq.gadsleaderboard.Models.SubmissionModel;
import com.acq.gadsleaderboard.Services.LearningLeadersService;
import com.acq.gadsleaderboard.Services.NoConnectivityException;
import com.acq.gadsleaderboard.Services.RetrofitService;
import com.acq.gadsleaderboard.Services.SubmissionService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Window;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_submit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        String baseUrl = "https://docs.google.com/forms/d/e/";

        SubmissionService submissionService =  RetrofitService.retrofitInit(baseUrl, this).create(SubmissionService.class);
        Call<SubmissionModel> call = submissionService.submitProject("Test First Name","Test Last Name","test@gmail.com","https://developer.android.com/");
        getLeadersData(call);


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    void getLeadersData(Call<SubmissionModel> call){
        call.enqueue( new Callback<SubmissionModel>() {
            @Override
            public void onResponse(@NonNull Call<SubmissionModel> call, @NonNull Response<SubmissionModel> response) {
                if (!response.isSuccessful()){
                    Snackbar.make(findViewById(R.id.submit_view), "Error sending data", Snackbar.LENGTH_LONG).show();
                    Log.d("ERROR LOG", "**************** ERROR SENDING DATA: "+ response.code() +" ********************");
                }else {
                    Snackbar.make(findViewById(R.id.submit_view), "Data sent", Snackbar.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<SubmissionModel> call, @NonNull Throwable t) {
                if (t instanceof NoConnectivityException || t instanceof UnknownHostException){
                    Snackbar.make(findViewById(R.id.submit_view), "No internet", Snackbar.LENGTH_LONG).show();
                    return;
                }
                Snackbar.make(findViewById(R.id.submit_view), "Error contacting server", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}