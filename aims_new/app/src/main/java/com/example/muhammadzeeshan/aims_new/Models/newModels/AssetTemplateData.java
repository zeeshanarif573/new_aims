package com.example.muhammadzeeshan.aims_new.Models.newModels;

/**
 * Created by Muhammad Zeeshan on 2/26/2018.
 */

public class AssetTemplateData {

    String Template_Id , Asset_Id, Widget_Id, Widget_Data;

    public AssetTemplateData(String template_Id, String asset_Id, String widget_Id, String widget_Data) {
        Template_Id = template_Id;
        Asset_Id = asset_Id;
        Widget_Id = widget_Id;
        Widget_Data = widget_Data;
    }

    public AssetTemplateData(String template_Id, String asset_Id, String widget_Data) {
        Template_Id = template_Id;
        Asset_Id = asset_Id;
        Widget_Data = widget_Data;
    }

    public AssetTemplateData() {
    }

    public String getTemplate_Id() {
        return Template_Id;
    }

    public void setTemplate_Id(String template_Id) {
        Template_Id = template_Id;
    }

    public String getAsset_Id() {
        return Asset_Id;
    }

    public void setAsset_Id(String asset_Id) {
        Asset_Id = asset_Id;
    }

    public String getWidget_Id() {
        return Widget_Id;
    }

    public void setWidget_Id(String widget_Id) {
        Widget_Id = widget_Id;
    }

    public String getWidget_Data() {
        return Widget_Data;
    }

    public void setWidget_Data(String widget_Data) {
        Widget_Data = widget_Data;
    }
}
