package com.example.muhammadzeeshan.aims_new.Models;

/**
 * Created by Muhammad Zeeshan on 2/6/2018.
 */

public class Asset_Widgets_Data {

    String Asset_widget_id, Asset_widget_type, Asset_widget_label, Asset_widget_data , Asset_form_id;

    public Asset_Widgets_Data(String asset_widget_type, String asset_widget_label) {
        Asset_widget_type = asset_widget_type;
        Asset_widget_label = asset_widget_label;

    }

    public Asset_Widgets_Data(String asset_form_id,String asset_widget_type, String asset_widget_label, String asset_widget_data) {
        Asset_form_id = asset_form_id;
        Asset_widget_type = asset_widget_type;
        Asset_widget_label = asset_widget_label;
        Asset_widget_data = asset_widget_data;

    }

    public Asset_Widgets_Data(String asset_widget_type, String asset_widget_label, String asset_widget_data) {
        Asset_widget_type = asset_widget_type;
        Asset_widget_label = asset_widget_label;
        Asset_widget_data = asset_widget_data;
    }

    public Asset_Widgets_Data() {
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

    public String getAsset_form_id() {
        return Asset_form_id;
    }

    public void setAsset_form_id(String asset_form_id) {
        Asset_form_id = asset_form_id;
    }
}
