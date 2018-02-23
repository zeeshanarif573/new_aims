package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.AssetDetails;
import com.example.muhammadzeeshan.aims_new.Activity.Templates.AssetTemplate;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.Models.newModels.TemplateIdAndName;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;

public class CreateAsset extends AppCompatActivity {

    EditText create_asset_title, create_asset_desc;
    Button submitCreateAsset, back_btn_createAsset;
    Snackbar snackbar;
    Spinner select_type;
    String Template_Id, Template_Name, assetTitle, assetDescription, selectedItem, selectedItemId, selectedItemName;
    ArrayAdapter adapter;
    TextView show;
    ArrayList<TemplateIdAndName> TemplateNameList;
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


        if (TemplateNameList.size() != 0) {
            spinnerItems.add("Select");
            spinnerItems.add("Create new Template");
            for (int i = 0; i < TemplateNameList.size(); i++) {

                spinnerItems.add(TemplateNameList.get(i).getTemplateName());
            }

        } else {
            spinnerItems.add("Select");
            spinnerItems.add("Create new Template");
        }

        adapter = new ArrayAdapter<String>(CreateAsset.this, R.layout.custom_spinner_item, spinnerItems);
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

                if (select_type.getSelectedItem().equals("Create new Template") || select_type.getSelectedItem().equals("Select")) {

                    if (!select_type.getSelectedItem().equals("Select")) {
                        startActivity(new Intent(CreateAsset.this, TemplateManagement.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }

                } else {

                    if (TextUtils.isEmpty(create_asset_title.getText().toString())) {

                        select_type.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        create_asset_title.setError("Please give title of form");

                    } else if (TextUtils.isEmpty(create_asset_desc.getText().toString())) {

                        select_type.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        create_asset_desc.setError("Please give desciption of form");

                    } else {

                        assetTitle = create_asset_title.getText().toString();
                        assetDescription = create_asset_desc.getText().toString();
                        selectedItem = select_type.getSelectedItem().toString();

                        for (TemplateIdAndName templateIdAndName : TemplateNameList) {
                            if (templateIdAndName.getTemplateName().equals(selectedItem)) {
                                selectedItemId = templateIdAndName.getTemplateId();
                                selectedItemName = templateIdAndName.getTemplateName();

                            }
                        }

                        if(getAssetTemplateData()){

                            Intent intent = new Intent(CreateAsset.this, AssetDetails.class);

                            intent.putExtra("AssetTitle", assetTitle);
                            intent.putExtra("AssetDescription", assetDescription);
                            intent.putExtra("SelectedItem", selectedItem);
                            intent.putExtra("SelectedItemId", selectedItemId);
                            intent.putExtra("SelectedItemName", selectedItemName);

                            startActivity(intent);
                        }

                        else if(!getAssetTemplateData()) {
                            startActivity(new Intent(CreateAsset.this, AssetTemplate.class));
                        }

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


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

        Layout_CreateAsset_Template = findViewById(R.id.linearLayout_CreateAsset);
    }

    public void hideKeyboard() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    void getTemplateData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from Template");
        while (cursor.moveToNext()) {

            Template_Id = cursor.getString(0);
            Template_Name = cursor.getString(1);

            TemplateNameList.add(new TemplateIdAndName(Template_Id, Template_Name));

        }
    }

    boolean getAssetTemplateData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from asset_template where Template_Id = " + selectedItemId);

        if(cursor != null){

            while (cursor.moveToNext()) {

                String AssetTemplate_Id = cursor.getString(0);
                String Widget_Type = cursor.getString(1);
                String Widget_Label = cursor.getString(2);
                String Widget_Data = cursor.getString(3);
                String Template_Id = cursor.getString(4);

                Log.e("AssetTemplate_Data", "AssetTemplate_Id: " + AssetTemplate_Id + " ,Template_Id: " + Template_Id + " ,Widget_Type: " + Widget_Type + " ,Widget_Label: " + Widget_Label + " ,Widget_Data: " + Widget_Data);
                Toast.makeText(this, "Contains", Toast.LENGTH_SHORT).show();
            }
            return true;

        }
        Toast.makeText(this, "Not Contains", Toast.LENGTH_SHORT).show();
        return false;

    }


}