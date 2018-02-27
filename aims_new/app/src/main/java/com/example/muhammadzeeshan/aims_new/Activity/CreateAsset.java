package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.AssetTemplateDetails;
import com.example.muhammadzeeshan.aims_new.Activity.Templates.AssetTemplate;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.Models.TemplateIdAndName;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;

public class CreateAsset extends AppCompatActivity {

    EditText create_asset_title, create_asset_desc;
    Button back_btn_createAsset;
    Snackbar snackbar;
    Spinner select_type;
    String Template_Id, Template_Name, assetTitle, assetDescription, selectedItem, selectedItemId, selectedItemName;
    ArrayAdapter adapter;
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
                        startActivity(new Intent(CreateAsset.this, CreateTemplate.class));
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

                        if (getAssetTemplateData()) {

                            Intent intent = new Intent(CreateAsset.this, AssetTemplateDetails.class);

                            intent.putExtra("AssetTitle", assetTitle);
                            intent.putExtra("AssetDescription", assetDescription);
                            intent.putExtra("SelectedItem", selectedItem);
                            intent.putExtra("SelectedItemId", selectedItemId);
                            intent.putExtra("SelectedItemName", selectedItemName);

                            startActivity(intent);

                        } else {

                            Snackbar.make(Layout_CreateAsset_Template, "You first have to create template..", Snackbar.LENGTH_LONG).show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    startActivity(new Intent(CreateAsset.this, AssetTemplate.class));
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                }
                            }, 2000);

                        }

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    @Override
    public void onBackPressed() {
        finish();
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

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String count = "SELECT count(*) FROM asset_template where Template_Id = " + selectedItemId;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {

            return true;

        }
        return false;
    }

}