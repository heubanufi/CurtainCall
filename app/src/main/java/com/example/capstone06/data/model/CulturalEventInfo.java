package com.example.capstone06.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CulturalEventInfo {

    @SerializedName("list_total_count")
    @Expose
    private int listTotalCount;
    @SerializedName("RESULT")
    @Expose
    private Result result;
    @SerializedName("row")
    @Expose
    private List<Row> row;

    public int getListTotalCount() {
        return listTotalCount;
    }

    public void setListTotalCount(int listTotalCount) {
        this.listTotalCount = listTotalCount;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }

}
