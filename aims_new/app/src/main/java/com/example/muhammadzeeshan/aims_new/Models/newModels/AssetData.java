package com.example.muhammadzeeshan.aims_new.Models.newModels;

/**
 * Created by Muhammad Zeeshan on 12/30/2017.
 */

public class AssetData {

    String template_id, asset_id , asset_name , asset_description , asset_type, status;

    public AssetData(String template_id, String asset_id, String asset_name, String asset_description, String asset_type, String status) {
        this.template_id = template_id;
        this.asset_id = asset_id;
        this.asset_name = asset_name;
        this.asset_description = asset_description;
        this.asset_type = asset_type;
        this.status = status;
    }

    public AssetData(String template_id, String asset_name, String asset_description, String asset_type, String status) {
        this.template_id = template_id;
        this.asset_name = asset_name;
        this.asset_description = asset_description;
        this.asset_type = asset_type;
        this.status = status;
    }

    public AssetData(String asset_id, String asset_name, String asset_description, String status) {
        this.asset_id = asset_id;
        this.asset_name = asset_name;
        this.asset_description = asset_description;
        this.status = status;
    }

    public AssetData() {
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getAsset_description() {
        return asset_description;
    }

    public void setAsset_description(String asset_description) {
        this.asset_description = asset_description;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
}
