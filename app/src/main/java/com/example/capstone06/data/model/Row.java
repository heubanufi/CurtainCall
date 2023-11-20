package com.example.capstone06.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row implements Parcelable {

    @SerializedName("CODENAME")
    @Expose
    private String codename;
    @SerializedName("GUNAME")
    @Expose
    private String guname;
    @SerializedName("TITLE")
    @Expose
    private String title;
    @SerializedName("DATE")
    @Expose
    private String date;
    @SerializedName("PLACE")
    @Expose
    private String place;
    @SerializedName("ORG_NAME")
    @Expose
    private String orgName;
    @SerializedName("USE_TRGT")
    @Expose
    private String useTrgt;
    @SerializedName("USE_FEE")
    @Expose
    private String useFee;
    @SerializedName("PLAYER")
    @Expose
    private String player;
    @SerializedName("PROGRAM")
    @Expose
    private String program;
    @SerializedName("ETC_DESC")
    @Expose
    private String etcDesc;
    @SerializedName("ORG_LINK")
    @Expose
    private String orgLink;
    @SerializedName("MAIN_IMG")
    @Expose
    private String mainImg;
    @SerializedName("RGSTDATE")
    @Expose
    private String rgstdate;
    @SerializedName("TICKET")
    @Expose
    private String ticket;
    @SerializedName("STRTDATE")
    @Expose
    private String strtdate;
    @SerializedName("END_DATE")
    @Expose
    private String endDate;
    @SerializedName("THEMECODE")
    @Expose
    private String themecode;
    @SerializedName("LOT")
    @Expose
    private String lot;
    @SerializedName("LAT")
    @Expose
    private String lat;
    @SerializedName("IS_FREE")
    @Expose
    private String isFree;
    @SerializedName("HMPG_ADDR")
    @Expose
    private String hmpgAddr;

    protected Row(Parcel in) {
        codename = in.readString();
        guname = in.readString();
        title = in.readString();
        date = in.readString();
        place = in.readString();
        orgName = in.readString();
        useTrgt = in.readString();
        useFee = in.readString();
        player = in.readString();
        program = in.readString();
        etcDesc = in.readString();
        orgLink = in.readString();
        mainImg = in.readString();
        rgstdate = in.readString();
        ticket = in.readString();
        strtdate = in.readString();
        endDate = in.readString();
        themecode = in.readString();
        lot = in.readString();
        lat = in.readString();
        isFree = in.readString();
        hmpgAddr = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codename);
        dest.writeString(guname);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(place);
        dest.writeString(orgName);
        dest.writeString(useTrgt);
        dest.writeString(useFee);
        dest.writeString(player);
        dest.writeString(program);
        dest.writeString(etcDesc);
        dest.writeString(orgLink);
        dest.writeString(mainImg);
        dest.writeString(rgstdate);
        dest.writeString(ticket);
        dest.writeString(strtdate);
        dest.writeString(endDate);
        dest.writeString(themecode);
        dest.writeString(lot);
        dest.writeString(lat);
        dest.writeString(isFree);
        dest.writeString(hmpgAddr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Row> CREATOR = new Creator<Row>() {
        @Override
        public Row createFromParcel(Parcel in) {
            return new Row(in);
        }

        @Override
        public Row[] newArray(int size) {
            return new Row[size];
        }
    };

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getGuname() {
        return guname;
    }

    public void setGuname(String guname) {
        this.guname = guname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUseTrgt() {
        return useTrgt;
    }

    public void setUseTrgt(String useTrgt) {
        this.useTrgt = useTrgt;
    }

    public String getUseFee() {
        return useFee;
    }

    public void setUseFee(String useFee) {
        this.useFee = useFee;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getEtcDesc() {
        return etcDesc;
    }

    public void setEtcDesc(String etcDesc) {
        this.etcDesc = etcDesc;
    }

    public String getOrgLink() {
        return orgLink;
    }

    public void setOrgLink(String orgLink) {
        this.orgLink = orgLink;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getRgstdate() {
        return rgstdate;
    }

    public void setRgstdate(String rgstdate) {
        this.rgstdate = rgstdate;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getStrtdate() {
        return strtdate;
    }

    public void setStrtdate(String strtdate) {
        this.strtdate = strtdate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getThemecode() {
        return themecode;
    }

    public void setThemecode(String themecode) {
        this.themecode = themecode;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getHmpgAddr() {
        return hmpgAddr;
    }

    public void setHmpgAddr(String hmpgAddr) {
        this.hmpgAddr = hmpgAddr;
    }

}
