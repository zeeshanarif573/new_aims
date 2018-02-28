package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.muhammadzeeshan.aims_new.Activity.Templates.AssetTemplate;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.Models.TemplateDescription;
import com.example.muhammadzeeshan.aims_new.R;

import static com.example.muhammadzeeshan.aims_new.GeneralMethods.CreatingTemplateLoader;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.creatingTemplate;

public class CreateTemplate extends AppCompatActivity {

    Button submitCreateTemplate;
    DatabaseHelper databaseHelper;
    AlertDialog.Builder alertDialog;
    String Template_Id, Template_Name;
    AlertDialog dialog;
    EditText create_template_name, create_template_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_template);

        initialization();

        submitCreateTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                CreatingTemplateLoader(view, CreateTemplate.this);

                //Handler for Saving Data.........................
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        creatingTemplate.dismiss();
                        databaseHelper.insertDataIntoTemplate(new TemplateDescription(create_template_name.getText().toString(), create_template_desc.getText().toString()));

                        dialog = alertDialog.create();
                        dialog.show();

                        getData();
                        finish();
                        startActivity(new Intent(CreateTemplate.this, AssetTemplate.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                        dialog.dismiss();
                    }
                }, 2000);

            }
        });
    }

    void initialization() {

        alertDialog = new AlertDialog.Builder(this);
        databaseHelper = new DatabaseHelper(this);
        create_template_name = findViewById(R.id.create_template_name);
        create_template_desc = findViewById(R.id.create_template_desc);

        submitCreateTemplate = findViewById(R.id.submitCreateTemplate);
    }

    void getData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from Template");
        while (cursor.moveToNext()) {

            Template_Id = cursor.getString(0);
            Template_Name = cursor.getString(1);
            String Template_Description = cursor.getString(2);

            Log.e("Retrieve", Template_Id + " Name: " + Template_Name + ", Description: " + Template_Description);
            Log.e("Template_ID", Template_Id);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
