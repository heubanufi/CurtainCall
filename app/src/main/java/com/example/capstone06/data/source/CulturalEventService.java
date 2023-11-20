package com.example.capstone06.data.source;

import com.example.capstone06.data.model.CulturalEventResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CulturalEventService {
    @GET("/{key}/json/culturalEventInfo/1/1000/{code_name}")
    public Observable<CulturalEventResponse> getEvents(
            @Path("key") String key,
            @Path("code_name") String codeName
    );
}
