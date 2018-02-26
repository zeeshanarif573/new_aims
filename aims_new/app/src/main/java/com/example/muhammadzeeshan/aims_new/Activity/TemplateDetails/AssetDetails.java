package com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.MainActivity;
import com.example.muhammadzeeshan.aims_new.Adapter.ViewAssetAdapter;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.R;

import static com.example.muhammadzeeshan.aims_new.Activity.MainActivity.form_list;
import static com.example.muhammadzeeshan.aims_new.Activity.MainActivity.listView;

public class AssetDetails extends AppCompatActivity {

    String getReportId;
    DatabaseHelper databaseHelper;
    LinearLayout mainLayout;
    Button change_operation, back_btn_add_Assets2;
    ViewAssetAdapter assetsAdapter;
    String operation_value;
    String getAssetId;
    Typeface ubuntu_light_font, ubuntu_medium_font, source_sans_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        getAssetId = intent.getStringExtra("AssetId");
        Toast.makeText(this, getAssetId, Toast.LENGTH_SHORT).show();

        getData();

    }

    void font(){
        ubuntu_light_font = Typeface.createFromAsset(AssetDetails.this.getAssets(), "font/ubuntu_light.ttf");
        ubuntu_medium_font = Typeface.createFromAsset(AssetDetails.this.getAssets(), "font/Ubuntu-Medium.ttf");
        source_sans_pro = Typeface.createFromAsset(AssetDetails.this.getAssets(), "font/SourceSansPro-Light.ttf");
    }

    void getData(){

        String val = "SELECT asset_template_data.Data_Id, asset.Asset_Name, asset_template.Widget_Label, asset_template_data.Widget_Data " +
                " FROM asset_template_data " +
                " LEFT JOIN asset ON asset_template_data.Asset_Id = asset.Asset_Id " +
                " LEFT JOIN asset_template ON asset_template_data.Widget_Id = asset_template.Widget_Id" +
                " LEFT JOIN template ON asset_template_data.Template_Id = template.Template_Id " +
                " where asset.Asset_Id = " + getAssetId ;

        Cursor cursor = databaseHelper.RetrieveData(val);

        while (cursor.moveToNext()){

            String Data_Id = cursor.getString(0);
            String AssetName = cursor.getString(1);
            String Widget_Label = cursor.getString(2);
            String Widget_Data = cursor.getString(3);

            Log.e("Values", "Data_Id: " + Data_Id + " ,AssetName: "+ AssetName + " ,Widget_Label: " + Widget_Label + " ,Widget_Data: " + Widget_Data);
        }
    }
}
