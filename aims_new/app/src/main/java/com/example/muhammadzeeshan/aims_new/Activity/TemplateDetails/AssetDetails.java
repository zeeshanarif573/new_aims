package com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.GeneralMethods;
import com.example.muhammadzeeshan.aims_new.Models.CameraModel;
import com.example.muhammadzeeshan.aims_new.Models.DateModel;
import com.example.muhammadzeeshan.aims_new.Models.SignaturePadModel;
import com.example.muhammadzeeshan.aims_new.Models.Widgets_Model;
import com.example.muhammadzeeshan.aims_new.Models.newModels.Asset_Data;
import com.example.muhammadzeeshan.aims_new.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.util.ArrayList;

public class AssetDetails extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    String generate_widget_id, generate_type, generate_label;
    LinearLayout layout;
    ArrayList<Widgets_Model> widgetList;
    Dialog date_dialog, time_dialog;
    DatePicker date_picker;
    Button getDate, getTime, back_btn_generate_widget;
    TimePicker time_picker;
    EditText report_name, header_name, footer_name;
    Button continue_dialog, logo, generate_report;
    ImageView dis_Image;
    LinearLayout displayImages;
    ArrayList<CameraModel> ImageList;
    AlertDialog.Builder alertDialog;
    Dialog dialog, get_header_footer_pdf;
    Button doneAssetDetails;
    byte[] byteArray;
    int day, year, month, hour, minute;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    String form_id;
    Uri uri;
    File uriToFile;
    ArrayList<SignaturePadModel> padList;

    ArrayList<File> signatureImage;
    String time, get_hour, get_min;
    /*--------------*/
    File photoFile;
    boolean insertOneTimeFlag = false;
    ProgressDialog progress1;
    ProgressDialog progress2;
    private int PICK_IMAGE_REQUEST = 20;
    String date, AssetTitle, AssetDescription, SelectedItem, SelectedItemId, SelectedItemName;

    GeneralMethods generalMethods;

    private ArrayList<DateModel> labelTextViewList = new ArrayList<>();
    private DateModel latestLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);

        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();

        AssetTitle = intent.getStringExtra("AssetTitle");
        AssetDescription = intent.getStringExtra("AssetDescription");
        SelectedItem = intent.getStringExtra("SelectedItem");
        SelectedItemId = intent.getStringExtra("SelectedItemId");
        SelectedItemName = intent.getStringExtra("SelectedItemName");


        Log.e("GetIntent", AssetTitle + AssetDescription + SelectedItem);
        Log.e("SelectedItemId", SelectedItemId);


        doneAssetDetails = findViewById(R.id.doneAssetDetails);

        doneAssetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseHelper.insertDataIntoAsset(new Asset_Data(SelectedItemId, AssetTitle, AssetDescription, SelectedItem, "InStock"));
                getAssetData();

            }
        });
    }

    void getAssetData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from asset");
        while (cursor.moveToNext()) {

            String Asset_Id = cursor.getString(0);
            String Asset_Name = cursor.getString(1);
            String Asset_Description = cursor.getString(2);
            String Asset_Type = cursor.getString(3);
            String Status = cursor.getString(4);
            String template_Id = cursor.getString(5);

            Log.e("Retrieve", "Asset_Id: " + Asset_Id + ", Template_Id: " + template_Id + " Name: " + Asset_Name + ", Description: " + Asset_Description + ", Type: " + Asset_Type + ", Status: " + Status);
        }
    }

}
