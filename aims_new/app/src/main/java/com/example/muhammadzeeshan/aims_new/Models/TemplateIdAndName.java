package com.example.muhammadzeeshan.aims_new.Models;

/**
 * Created by Muhammad Zeeshan on 2/22/2018.
 */

public class TemplateIdAndName {

    String TemplateId, TemplateName;

    public TemplateIdAndName(String templateId, String templateName) {
        TemplateId = templateId;
        TemplateName = templateName;
    }

    public TemplateIdAndName() {
    }

    public String getTemplateId() {
        return TemplateId;
    }

    public void setTemplateId(String templateId) {
        TemplateId = templateId;
    }

    public String getTemplateName() {
        return TemplateName;
    }

    public void setTemplateName(String templateName) {
        TemplateName = templateName;
    }
}
