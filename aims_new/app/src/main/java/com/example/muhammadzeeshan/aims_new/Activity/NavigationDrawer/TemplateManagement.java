package com.example.muhammadzeeshan.aims_new.Activity.NavigationDrawer;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.muhammadzeeshan.aims_new.Activity.CreateTemplate;
import com.example.muhammadzeeshan.aims_new.Activity.MainActivity;
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
    ExpandableListView listView;
    TemplateAdapter templateAdapter;
    List parentlist;
    Button backBtn_templateMgt;
    HashMap<String, List<TemplateDescription>> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_management);

        initialization();
        getInfo();

        templateAdapter = new TemplateAdapter(this, map, parentlist);
        listView.setAdapter(templateAdapter);

        createTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TemplateManagement.this, CreateTemplate.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        backBtn_templateMgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(TemplateManagement.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    void initialization(){

        parentlist = new ArrayList<>();
        map = new HashMap<>();

        backBtn_templateMgt = findViewById(R.id.back_btn_templateMgt);

        listView = findViewById(R.id.expandable_ListView);

        listView.setEmptyView(findViewById(R.id.welcome_template_text));
        databaseHelper = new DatabaseHelper(this);
        createTemplate = findViewById(R.id.createTemplate);

    }

    public HashMap<String, List<TemplateDescription>> getInfo() {

        getTemplateData();

        map.put("ParentList", parentlist);

        return map ;
    }

    void getTemplateData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from template");
        while (cursor.moveToNext()) {

            Template_Id = cursor.getString(0);
            Template_Name = cursor.getString(1);
            Template_Description = cursor.getString(2);

            parentlist.add(new TemplateDescription(Template_Id, Template_Name, Template_Description));

            Log.e("TemplateManagement_Data", "Template_Id: " + Template_Id + " ,Template_Name: " + Template_Name);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        finish();
        startActivity(new Intent(TemplateManagement.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
