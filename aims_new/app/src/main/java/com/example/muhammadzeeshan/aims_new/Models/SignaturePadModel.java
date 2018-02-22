package com.example.muhammadzeeshan.aims_new.Models;

import com.github.gcacace.signaturepad.views.SignaturePad;

/**
 * Created by Muhammad Zeeshan on 2/15/2018.
 */

public class SignaturePadModel {

    SignaturePad signaturePad;
    String id;

    public SignaturePadModel(){}
    public SignaturePadModel(String id, SignaturePad pad) {
        this.signaturePad = pad;
        this.id = id;
    }

    public SignaturePad getSignaturePad() {
        return signaturePad;
    }


    public String getId() {
        return id;
    }

}
