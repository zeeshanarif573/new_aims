package com.example.muhammadzeeshan.aims_new.Models.newModels;

/**
 * Created by Muhammad Zeeshan on 2/22/2018.
 */

public class AssetTemplatesWidgets {

    String template_id, asset_template_id, widget_type, widget_label;

    public AssetTemplatesWidgets(String template_id, String widget_type, String widget_label) {
        this.template_id = template_id;
        this.widget_type = widget_type;
        this.widget_label = widget_label;
    }

    public AssetTemplatesWidgets(String widget_type, String widget_label) {
        this.widget_type = widget_type;
        this.widget_label = widget_label;
    }

    public AssetTemplatesWidgets(String template_id, String asset_template_id, String widget_type, String widget_label) {
        this.template_id = template_id;
        this.asset_template_id = asset_template_id;
        this.widget_type = widget_type;
        this.widget_label = widget_label;

    }

    public AssetTemplatesWidgets() {

    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getAsset_template_id() {
        return asset_template_id;
    }

    public void setAsset_template_id(String asset_template_id) {
        this.asset_template_id = asset_template_id;
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

}