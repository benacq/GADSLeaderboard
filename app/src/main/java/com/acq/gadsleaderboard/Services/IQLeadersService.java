package com.acq.gadsleaderboard.Services;
import com.acq.gadsleaderboard.Models.IQLeadersModel;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IQLeadersService {
    @GET("api/skilliq")
    Call<List<IQLeadersModel>> getIQLeaders();
}
