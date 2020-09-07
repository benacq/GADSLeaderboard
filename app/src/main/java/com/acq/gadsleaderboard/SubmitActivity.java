package com.acq.gadsleaderboard;

import android.app.AlertDialog;

import android.os.Bundle;

import com.acq.gadsleaderboard.Services.SubmissionService;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmitActivity extends AppCompatActivity {

    private View mDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_submit);

        Button submitProject = findViewById(R.id.submit_project);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);


        TextView firstNameTextView = findViewById(R.id.first_name_submit);
        TextView lastNameTextView = findViewById(R.id.last_name_submit);
        TextView emailTextView = findViewById(R.id.email_submit);
        TextView githubTextView = findViewById(R.id.github_link_submit);


        String baseUrl = "https://docs.google.com/forms/d/e/";






        submitProject.setOnClickListener(v -> {
            String firstName = firstNameTextView.getText().toString();
            String lastName = lastNameTextView.getText().toString();
            String email = emailTextView.getText().toString();
            String link = githubTextView.getText().toString();

            SubmissionService submissionService = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(SubmissionService.class);

            Call<ResponseBody> call = submissionService.submitProject(firstName, lastName, email, link);

            confirmDialog().show();
//            getLeadersData(call);

            Button cancelSubmission = mDialogView.findViewById(R.id.cancel_submission);
            cancelSubmission.setOnClickListener(v1 -> {
                if (confirmDialog().isShowing()){
                    Log.d("CANCEL", "**************** CANCELLING SUBMISSION ********************");
                    confirmDialog().dismiss();
                }
            });

            Button proceedSubmission = mDialogView.findViewById(R.id.submit_project_confirm_yes);
            proceedSubmission.setOnClickListener(v2 -> getLeadersData(call));
        });



    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    void getLeadersData(Call<ResponseBody> call){
        call.enqueue( new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (!response.isSuccessful()){
                    showCustomDialog(R.layout.popup_error);
                    Log.d("ERROR LOG", "**************** ERROR SENDING DATA: "+ call.request().url() +" ********************");
                }else {
                    showCustomDialog(R.layout.popup_success);
                    Log.d("SUCCESS LOG", "**************** ERROR SENDING DATA: "+ response.body() +" ********************");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d("ERROR LOG", "**************** ERROR SENDING DATA: "+ t.getMessage() +" ********************");
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



    private AlertDialog confirmDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        mDialogView = LayoutInflater.from(this).inflate(R.layout.confirm_send, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(mDialogView);
        return builder.create();
    }
}