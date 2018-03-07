package com.example.muhammadzeeshan.aims_new.Activity.NavigationDrawer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.muhammadzeeshan.aims_new.Activity.CreateTemplate;
import com.example.muhammadzeeshan.aims_new.Activity.MainActivity;
import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.AssetTemplateDetails;
import com.example.muhammadzeeshan.aims_new.Activity.Templates.AssetTemplate;
import com.example.muhammadzeeshan.aims_new.Adapter.TemplateAdapter;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.Models.TemplateDescription;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TemplateManagement extends AppCompatActivity {

    FloatingActionButton createTemplate;
    DatabaseHelper databaseHelper;
    String Template_Id, Template_Name, Template_Description;
    ListView listView;
    ArrayList<TemplateDescription> templateList;
    TemplateAdapter templateAdapter;
    Button backBtn_templateMgt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_management);

        initialization();
        getTemplateData();

        templateAdapter = new TemplateAdapter(this , templateList);
        listView.setAdapter(templateAdapter);

        backBtn_templateMgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(new Intent(TemplateManagement.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startActivity(new Intent(getApplicationContext(), AssetTemplateDetails.class));
            }
        });

    }

    void initialization() {

        backBtn_templateMgt = findViewById(R.id.back_btn_templateMgt);
        templateList = new ArrayList<>();

        listView = findViewById(R.id.expandable_ListView);

        listView.setEmptyView(findViewById(R.id.welcome_template_text));
        databaseHelper = new DatabaseHelper(this);
        createTemplate = findViewById(R.id.createTemplate);

    }

    void getTemplateData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from template");
        while (cursor.moveToNext()) {

            Template_Id = cursor.getString(0);
            Template_Name = cursor.getString(1);
            Template_Description = cursor.getString(2);

            templateList.add(new TemplateDescription(Template_Id, Template_Name, Template_Description));

            Log.e("TemplateManagement_Data", "Template_Id: " + Template_Id + " ,Template_Name: " + Template_Name);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        finish();
        startActivity(new Intent(TemplateManagement.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}