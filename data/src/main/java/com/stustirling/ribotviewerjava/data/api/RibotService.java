package com.stustirling.ribotviewerjava.data.api;

import com.stustirling.ribotviewerjava.data.api.model.ApiRibot;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Stu Stirling on 27/09/2017.
 */

public interface RibotService {

    @GET("/ribots")
    Single<List<ApiRibot>> getRibots();

}
