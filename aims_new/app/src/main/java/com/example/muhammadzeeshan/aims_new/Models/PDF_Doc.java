package com.example.muhammadzeeshan.aims_new.Models;

/**
 * Created by Muhammad Zeeshan on 1/28/2018.
 */

public class PDF_Doc {

    String name,path;

    public PDF_Doc(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public PDF_Doc() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
