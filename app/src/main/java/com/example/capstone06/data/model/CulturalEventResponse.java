package com.example.capstone06.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CulturalEventResponse {

    @SerializedName("culturalEventInfo")
    @Expose
    private CulturalEventInfo culturalEventInfo;

    public CulturalEventInfo getCulturalEventInfo() {
        return culturalEventInfo;
    }

    public void setCulturalEventInfo(CulturalEventInfo culturalEventInfo) {
        this.culturalEventInfo = culturalEventInfo;
    }

}
