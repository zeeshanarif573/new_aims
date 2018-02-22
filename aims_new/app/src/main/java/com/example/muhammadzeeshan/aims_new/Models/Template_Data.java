package com.example.muhammadzeeshan.aims_new.Models;

/**
 * Created by Muhammad Zeeshan on 1/4/2018.
 */

public class Template_Data {

    String template_id, template_name, template_description;

    public Template_Data( String template_id, String template_name, String template_description) {

        this.template_id = template_id;
        this.template_name = template_name;
        this.template_description = template_description;
    }

    public Template_Data(String template_name, String template_description) {
        this.template_name = template_name;
        this.template_description = template_description;
    }

    public Template_Data() {
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTemplate_description() {
        return template_description;
    }

    public void setTemplate_description(String template_description) {
        this.template_description = template_description;
    }
}
