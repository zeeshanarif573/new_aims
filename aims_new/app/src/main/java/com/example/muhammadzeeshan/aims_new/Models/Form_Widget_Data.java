package com.example.muhammadzeeshan.aims_new.Models;

/**
 * Created by Muhammad Zeeshan on 1/6/2018.
 */

public class Form_Widget_Data {

    String form_id, form_name, form_description, operation_type, widget_id, widget_type, widget_label, widget_data ;

    public Form_Widget_Data(String form_id, String form_name, String form_description,String operation_type, String widget_id, String widget_type, String widget_label, String widget_data) {
        this.form_id = form_id;
        this.form_name = form_name;
        this.form_description = form_description;
        this.operation_type = operation_type;
        this.widget_id = widget_id;
        this.widget_type = widget_type;
        this.widget_label = widget_label;
        this.widget_data = widget_data;
    }

    public Form_Widget_Data(String form_id, String form_name, String form_description, String operation_type) {
        this.form_id = form_id;
        this.form_name = form_name;
        this.form_description = form_description;
        this.operation_type = operation_type;
    }

    public Form_Widget_Data() {
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getForm_name() {
        return form_name;
    }

    public void setForm_name(String form_name) {
        this.form_name = form_name;
    }

    public String getForm_description() {
        return form_description;
    }

    public void setForm_description(String form_description) {
        this.form_description = form_description;
    }

    public String getWidget_id() {
        return widget_id;
    }

    public void setWidget_id(String widget_id) {
        this.widget_id = widget_id;
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

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getWidget_data() {
        return widget_data;
    }

    public void setWidget_data(String widget_data) {
        this.widget_data = widget_data;
    }
}
