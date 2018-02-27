package com.example.muhammadzeeshan.aims_new.Models;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.gcacace.signaturepad.views.SignaturePad;

/**
 * Created by Muhammad Zeeshan on 1/24/2018.
 */

public class Widgets_Model {

    String Id, Label;
    EditText editText;
    Button button;
    CheckBox checkBox;
    SignaturePad signature;
    DatePicker datePicker;
    TimePicker timePicker;
    TextView textView ,section;
    LinearLayout imageView;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public SignaturePad getSignature() {
        return signature;
    }

    public void setSignature(SignaturePad signature) {
        this.signature = signature;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public void setTimePicker(TimePicker timePicker) {
        this.timePicker = timePicker;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public LinearLayout getImageView() {
        return imageView;
    }

    public void setImageView(LinearLayout imageView) {
        this.imageView = imageView;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public TextView getSection() {
        return section;
    }

    public void setSection(TextView section) {
        this.section = section;
    }
}
