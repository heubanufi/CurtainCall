package com.example.capstone06.data.repository;

import androidx.core.util.Pair;

import com.example.capstone06.data.model.CulturalEventResponse;
import com.example.capstone06.data.model.Row;
import com.example.capstone06.data.source.CulturalEventService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CulturalEventRepository {
    public static final CulturalEventRepository instance = new CulturalEventRepository();
    private final CulturalEventService service;

    private CulturalEventRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        service = retrofit.create(CulturalEventService.class);
    }

    public Observable<Pair<List<Row>, List<Row>>> fetch() {
        String key = "467671625863636a343577434e6b55";

        Observable<CulturalEventResponse> theatricals = service.getEvents(key, "연극");
        Observable<CulturalEventResponse> musical = service.getEvents(key, "뮤지컬");

        return Observable.zip(
                theatricals.subscribeOn(Schedulers.io()), musical.subscribeOn(Schedulers.io()),
                (culturalEventResponse, culturalEventResponse2) -> {
                    List<Row> events = new ArrayList<>();
                    events.addAll(culturalEventResponse.getCulturalEventInfo().getRow());
                    events.addAll(culturalEventResponse2.getCulturalEventInfo().getRow());
                    return events;
                }).map((Function<List<Row>, Pair<List<Row>, List<Row>>>) events -> {
            ArrayList<Row> ongoingEvents = new ArrayList<>();
            ArrayList<Row> upcomingEvents = new ArrayList<>();
            ArrayList<Row> endingEvents = new ArrayList<>();

            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.US);

            for (Row event : events) {
                try {
                    Date startDate = dateFormat.parse(event.getStrtdate());
                    Date endDate = dateFormat.parse(event.getEndDate());
                    if (endDate.before(now)) { // 종료 시간이 현재 시간 이전인 경우
                        endingEvents.add(event);
                    } else if (endDate.after(now)) { // 종료 시간이 현재 시간 이후인 경우
                        if (startDate.after(now)) { // 시작 시간이 현재 시간 이후인 경우
                            upcomingEvents.add(event);
                        } else {
                            ongoingEvents.add(event);
                        }
                    }
                } catch (Exception ignore) {
                    continue;
                }
            }

            return new Pair<>(ongoingEvents, upcomingEvents);
        });
    }
}
