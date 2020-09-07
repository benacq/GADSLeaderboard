package com.acq.gadsleaderboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.net.UnknownHostException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitActivity extends AppCompatActivity {
//    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_submit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

//        mDialog = new Dialog(this);

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
                    showCustomDialog(R.layout.popup_error);
                    Log.d("ERROR LOG", "**************** ERROR SENDING DATA: "+ response.code() +" ********************");
                }else {
                    showCustomDialog(R.layout.popup_success);
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



    private void showCustomDialog(int layout) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(layout, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}