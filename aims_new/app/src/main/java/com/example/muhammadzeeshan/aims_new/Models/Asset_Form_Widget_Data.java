package com.example.muhammadzeeshan.aims_new.Models;

/**
 * Created by Muhammad Zeeshan on 2/6/2018.
 */

public class Asset_Form_Widget_Data {

    String Asset_form_id, Asset_form_name, Asset_form_description, Asset_Operations ,Asset_widget_id, Asset_widget_type, Asset_widget_label, Asset_widget_data ;

    public Asset_Form_Widget_Data(String asset_form_id, String asset_form_name, String asset_form_description, String asset_operations, String asset_widget_id, String asset_widget_type, String asset_widget_label, String asset_widget_data) {
        Asset_form_id = asset_form_id;
        Asset_form_name = asset_form_name;
        Asset_form_description = asset_form_description;
        Asset_Operations = asset_operations;
        Asset_widget_id = asset_widget_id;
        Asset_widget_type = asset_widget_type;
        Asset_widget_label = asset_widget_label;
        Asset_widget_data = asset_widget_data;
    }

    public Asset_Form_Widget_Data(String asset_form_id, String asset_form_name, String asset_form_description, String asset_Operations) {
        Asset_form_id = asset_form_id;
        Asset_form_name = asset_form_name;
        Asset_form_description = asset_form_description;
        Asset_Operations = asset_Operations;
    }

    public Asset_Form_Widget_Data() {
    }

    public String getAsset_form_id() {
        return Asset_form_id;
    }

    public void setAsset_form_id(String asset_form_id) {
        Asset_form_id = asset_form_id;
    }

    public String getAsset_form_name() {
        return Asset_form_name;
    }

    public void setAsset_form_name(String asset_form_name) {
        Asset_form_name = asset_form_name;
    }

    public String getAsset_form_description() {
        return Asset_form_description;
    }

    public void setAsset_form_description(String asset_form_description) {
        Asset_form_description = asset_form_description;
    }

    public String getAsset_Operations() {
        return Asset_Operations;
    }

    public void setAsset_Operations(String asset_Operations) {
        Asset_Operations = asset_Operations;
    }

    public String getAsset_widget_id() {
        return Asset_widget_id;
    }

    public void setAsset_widget_id(String asset_widget_id) {
        Asset_widget_id = asset_widget_id;
    }

    public String getAsset_widget_type() {
        return Asset_widget_type;
    }

    public void setAsset_widget_type(String asset_widget_type) {
        Asset_widget_type = asset_widget_type;
    }

    public String getAsset_widget_label() {
        return Asset_widget_label;
    }

    public void setAsset_widget_label(String asset_widget_label) {
        Asset_widget_label = asset_widget_label;
    }

    public String getAsset_widget_data() {
        return Asset_widget_data;
    }

    public void setAsset_widget_data(String asset_widget_data) {
        Asset_widget_data = asset_widget_data;
    }
}
