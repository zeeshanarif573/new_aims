package com.example.muhammadzeeshan.aims_new.Activity.NavigationDrawer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
    Button backBtn_templateMgt;
    List<String> itemsList;
    String listDataHeader_Id;
    List<TemplateDescription> listDataHeader;
    HashMap<TemplateDescription, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_management);

        initialization();
        getTemplateData();

        itemsList.add("Asset");
        itemsList.add("CheckOut");
        itemsList.add("CheckIn");
        itemsList.add("Inspect");

        for (int i = 0; i < itemsList.size(); i++) {
            listDataChild.put(listDataHeader.get(i), itemsList);
        }

        templateAdapter = new TemplateAdapter(this, listDataHeader, listDataChild);
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

        // Listview on child click listener
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return false;
            }
        });

        // Listview Group expanded listener
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                TemplateDescription templateDescription = listDataHeader.get(groupPosition);

                listDataHeader_Id = templateDescription.getTemplate_id();
                Log.e("listDataHeader_Id", listDataHeader_Id);

             //  getAssetTemplateData();
            }
        });

    }

    void initialization() {

        itemsList = new ArrayList<>();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        backBtn_templateMgt = findViewById(R.id.back_btn_templateMgt);

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

            listDataHeader.add(new TemplateDescription(Template_Id, Template_Name, Template_Description));

            Log.e("TemplateManagement_Data", "Template_Id: " + Template_Id + " ,Template_Name: " + Template_Name);
        }
    }

    void getAssetTemplateData() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String assetTemplateCount = "SELECT count(*) FROM asset_template where Template_Id = " + listDataHeader_Id;
        Cursor assetTemplateCursor = db.rawQuery(assetTemplateCount, null);
        assetTemplateCursor.moveToFirst();
        int assetTemplateiCount = assetTemplateCursor.getInt(0);

        Log.e("assetTemplateiCount", String.valueOf(assetTemplateiCount));

        if (assetTemplateiCount > 0) {

            itemsList.add("Asset");
        }

        String CheckoutTemplateCount = "SELECT count(*) FROM checkout where Template_Id = " + listDataHeader_Id;
        Cursor CheckoutTemplateCursor = db.rawQuery(CheckoutTemplateCount, null);
        CheckoutTemplateCursor.moveToFirst();
        int CheckoutTemplateiCount = CheckoutTemplateCursor.getInt(0);

        Log.e("CheckoutTemplateiCount", String.valueOf(CheckoutTemplateiCount));

        if (CheckoutTemplateiCount > 0) {

            itemsList.add("Checkout");
        }

        for (int i = 0; i < itemsList.size(); i++) {
            listDataChild.put(listDataHeader.get(i), itemsList);
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
