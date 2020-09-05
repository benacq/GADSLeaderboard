package com.acq.gadsleaderboard.Services;

import com.acq.gadsleaderboard.Models.Submission;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SubmissionService {
    @POST
    Call<Submission> submitProject(
            @Body String firstName,
            @Body String lastName,
            @Body String email,
            @Body String githubLink);
}
