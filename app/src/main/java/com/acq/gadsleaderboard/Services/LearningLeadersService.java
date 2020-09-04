package com.acq.gadsleaderboard.Services;

import com.acq.gadsleaderboard.Models.LearningLeadersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LearningLeadersService {
    @GET("api/hours")
    Call<List<LearningLeadersModel>> getLearningLeaders();
}
