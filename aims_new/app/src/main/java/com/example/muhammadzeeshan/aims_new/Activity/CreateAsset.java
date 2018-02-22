package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.AssetDetails;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.Models.Asset_Data;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;

import static com.example.muhammadzeeshan.aims_new.GeneralMethods.Loader1;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.progress1;

public class CreateAsset extends AppCompatActivity {

    EditText create_asset_title, create_asset_desc;
    Button submitCreateAsset, back_btn_createAsset;
    Snackbar snackbar;
    Spinner select_type;
    String Template_Id, Template_Name;
    ArrayAdapter adapter;
    TextView show;
    ArrayList<String> TemplateNameList;
    AlertDialog.Builder alertDialog;
    DatabaseHelper databaseHelper;
    ArrayList<String> spinnerItems;
    LinearLayout Layout_CreateAsset_Template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_asset);

        init();
        hideKeyboard();

        getTemplateData();
        getAssetData();

        if (Template_Name.equals("")) {
            spinnerItems.add("Select");
            spinnerItems.add("Create new Template");

        } else {

            spinnerItems.add("Select");
            spinnerItems.add("Create new Template");

            for (int i = 0; i < TemplateNameList.size(); i++) {

                spinnerItems.add(TemplateNameList.get(i));
            }

        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(R.layout.custom_spinner_item);
        select_type.setAdapter(adapter);

        back_btn_createAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                startActivity(new Intent(CreateAsset.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        select_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (select_type.getSelectedItem().equals("Create new Template")) {
                    startActivity(new Intent(CreateAsset.this, TemplateManagement.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        submitCreateAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Wanted to create CheckOut, CheckIn and Inspection Template? ");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Loader1(view, CreateAsset.this);

                        //Handler for Saving Data.........................
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                progress1.dismiss();

                                databaseHelper.insertDataIntoAsset(new Asset_Data(Template_Id, create_asset_title.getText().toString(), create_asset_desc.getText().toString(), select_type.getSelectedItem().toString(), "InStock"));


                                Log.e("Template_ID", Template_Id);
                                startActivity(new Intent(CreateAsset.this, AssetDetails.class));


                            }
                        }, 2000);

                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

//                if (TextUtils.isEmpty(create_asset_title.getText().toString())) {
//                    create_asset_title.setError("Please give title of form");
//                } else if (TextUtils.isEmpty(create_asset_desc.getText().toString())) {
//                    create_asset_desc.setError("Please give desciption of form");
//                } else {
//                    databaseHelper.insertDataIntoAssetForms(new AssetFormData(create_asset_title.getText().toString(), create_asset_desc.getText().toString(), "null"));
//                    getAssetFormData();
//
//                    snackbar = Snackbar.make(Layout_CreateAsset_Template, "Form Created Successfully", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//
//                    create_asset_title.setText("");
//                    create_asset_desc.setText("");
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(CreateAsset.this, AddAssetWidget.class);
//                            intent.putExtra("Asset_Form_Id", Asset_Str_Id);
//                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                        }
//                    }, 700);
//
//                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();

        startActivity(new Intent(CreateAsset.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();

    }

    void init() {

        TemplateNameList = new ArrayList<>();
        spinnerItems = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        select_type = findViewById(R.id.select_type);
        alertDialog = new AlertDialog.Builder(this);
        databaseHelper = new DatabaseHelper(this);

        create_asset_title = findViewById(R.id.create_asset_title);
        create_asset_desc = findViewById(R.id.create_asset_desc);

        back_btn_createAsset = findViewById(R.id.back_btn_createAsset);
        submitCreateAsset = findViewById(R.id.submitCreateAsset);

        Layout_CreateAsset_Template = findViewById(R.id.linearLayout_CreateAsset);
    }

    public void hideKeyboard() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    void getTemplateData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from Template");
        while (cursor.moveToNext()) {

            Template_Id = cursor.getString(0);
            Template_Name = cursor.getString(1);
            String Template_Description = cursor.getString(2);

            TemplateNameList.add(Template_Name);

        }
    }

}
