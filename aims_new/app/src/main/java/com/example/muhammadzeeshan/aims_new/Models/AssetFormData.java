package com.example.muhammadzeeshan.aims_new.Models;

/**
 * Created by Muhammad Zeeshan on 2/6/2018.
 */

public class AssetFormData {

    String Asset_form_id , Asset_form_name ,Asset_form_description, Asset_Operations;

    public AssetFormData(String asset_form_id, String asset_form_name, String asset_form_description, String asset_Operations) {
        Asset_form_id = asset_form_id;
        Asset_form_name = asset_form_name;
        Asset_form_description = asset_form_description;
        Asset_Operations = asset_Operations;

    }

    public AssetFormData(String asset_form_name, String asset_form_description, String asset_Operations) {
        Asset_form_name = asset_form_name;
        Asset_form_description = asset_form_description;
        Asset_Operations = asset_Operations;

    }

    public AssetFormData() {
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
}
