package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.muhammadzeeshan.aims_new.Activity.Templates.AssetTemplate;
import com.example.muhammadzeeshan.aims_new.R;

public class TemplateManagement extends AppCompatActivity {

    FloatingActionButton createTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_management);

        initialization();

        createTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TemplateManagement.this, CreateTemplate.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    void initialization(){
        createTemplate = findViewById(R.id.createTemplate);
    }
}
