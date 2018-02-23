package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.DialogInterface;
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
import com.example.muhammadzeeshan.aims_new.Models.newModels.TemplateData;
import com.example.muhammadzeeshan.aims_new.R;

import static com.example.muhammadzeeshan.aims_new.GeneralMethods.CreatingTemplateLoader;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.progress1;

public class CreateTemplate extends AppCompatActivity {

    Button submitCreateTemplate;
    DatabaseHelper databaseHelper;
    AlertDialog.Builder alertDialog;
    String Template_Id, Template_Name;
    EditText create_template_name, create_template_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_template);

        initialization();

        submitCreateTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Wanted to create CheckOut, CheckIn and Inspection Template? ");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        CreatingTemplateLoader(view, CreateTemplate.this);

                        //Handler for Saving Data.........................
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                progress1.dismiss();

                                databaseHelper.insertDataIntoTemplate(new TemplateData(create_template_name.getText().toString(), create_template_desc.getText().toString()));
                                startActivity(new Intent(CreateTemplate.this, AssetTemplate.class));

                                getData();

                            }
                        }, 2000);

                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        CreatingTemplateLoader(view, CreateTemplate.this);

                        //Handler for Saving Data.........................
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                progress1.dismiss();

                                databaseHelper.insertDataIntoTemplate(new TemplateData(create_template_name.getText().toString(), create_template_desc.getText().toString()));
                                startActivity(new Intent(CreateTemplate.this, CreateAsset.class));

                                getData();

                            }
                        }, 4000);
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

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

}
