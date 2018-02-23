package com.example.muhammadzeeshan.aims_new.Models.newModels;

/**
 * Created by Muhammad Zeeshan on 2/22/2018.
 */

public class CheckOutWidgets {

    String template_id, checkout_id, widget_type, widget_label, widget_data;

    public CheckOutWidgets(String template_id, String widget_type, String widget_label, String widget_data) {
        this.template_id = template_id;
        this.widget_type = widget_type;
        this.widget_label = widget_label;
        this.widget_data = widget_data;
    }

    public CheckOutWidgets(String widget_type, String widget_label) {
        this.widget_type = widget_type;
        this.widget_label = widget_label;
    }

    public CheckOutWidgets(String widget_type, String widget_label, String widget_data) {
        this.widget_type = widget_type;
        this.widget_label = widget_label;
        this.widget_data = widget_data;
    }

    public CheckOutWidgets() {
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(String checkout_id) {
        this.checkout_id = checkout_id;
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
