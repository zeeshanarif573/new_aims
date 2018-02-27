package com.example.muhammadzeeshan.aims_new.Models.CheckIn;

/**
 * Created by Muhammad Zeeshan on 2/22/2018.
 */

public class CheckInWidgets {

    String template_id, checkin_id, widget_type, widget_label, widget_data;

    public CheckInWidgets(String template_id, String widget_type, String widget_label, String widget_data) {
        this.template_id = template_id;
        this.widget_type = widget_type;
        this.widget_label = widget_label;
        this.widget_data = widget_data;
    }

    public CheckInWidgets(String widget_type, String widget_label, String widget_data) {
        this.widget_type = widget_type;
        this.widget_label = widget_label;
        this.widget_data = widget_data;
    }

    public CheckInWidgets(String widget_type, String widget_label) {
        this.widget_type = widget_type;
        this.widget_label = widget_label;
    }

    public CheckInWidgets() {
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getCheckin_id() {
        return checkin_id;
    }

    public void setCheckin_id(String checkin_id) {
        this.checkin_id = checkin_id;
    }

    public String getWidget_type() {
        return widget_type;
    }

    public void setWidget_type(String widget_type) {
        this.widget_type = widget_type;
    }

    public String getWidget_label() {
        return widget_label;
    }

    public void setWidget_label(String widget_label) {
        this.widget_label = widget_label;
    }

    public String getWidget_data() {
        return widget_data;
    }

    public void setWidget_data(String widget_data) {
        this.widget_data = widget_data;
    }
}
