package com.example.muhammadzeeshan.aims_new.Models;

import android.widget.TextView;

/**
 * Created by Muhammad Zeeshan on 2/9/2018.
 */

public class DateModel {

    String id , date , time;
    TextView labelTextView;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TextView getLabelTextView() {
        return labelTextView;
    }

    public void setLabelTextView(TextView labelTextView) {
        this.labelTextView = labelTextView;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
