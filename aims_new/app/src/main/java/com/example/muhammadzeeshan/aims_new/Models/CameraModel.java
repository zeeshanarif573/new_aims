package com.example.muhammadzeeshan.aims_new.Models;

import java.io.File;

/**
 * Created by Muhammad Zeeshan on 2/7/2018.
 */

public class CameraModel {

    String id;
    File Image_file;

    public CameraModel(String id, File image_file) {
        this.id = id;
        Image_file = image_file;
    }

    public CameraModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getImage_file() {
        return Image_file;
    }

    public void setImage_file(File image_file) {
        Image_file = image_file;
    }
}
