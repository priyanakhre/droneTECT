package com.example.a591669.c_uasnotficiation;

/**
 * Created by 591669 on 7/14/2017.
 */

public class report {
    public long latitude;
    public long longitude;
    public String time;
    public long sound;
    public String picture;
    public String category;
    public double angle;
    public String comment;
    public long reportNum;

    public report(){

    }

    public report(long lattitude, long longitude, String time, long sound, String picture,
                  String category, double angle, String comment, long reportNum){

        this.latitude = lattitude;
        this.longitude = longitude;
        this.time = time;
        this.sound = sound;
        this.picture = picture;
        this.category = category;
        this.angle = angle;
        this.comment = comment;
        this.reportNum = reportNum;



    }


}
