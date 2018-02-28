package com.example.muhammadzeeshan.aims_new.Activity.NavigationDrawer;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.muhammadzeeshan.aims_new.Activity.MainActivity;
import com.example.muhammadzeeshan.aims_new.Adapter.PdfAdapter;
import com.example.muhammadzeeshan.aims_new.Models.PDF_Doc;
import com.example.muhammadzeeshan.aims_new.R;

import java.io.File;
import java.util.ArrayList;

public class Reports extends AppCompatActivity {


    ListView listView;
    Button back_btn_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        init();

        listView = (ListView)findViewById(R.id.listView_pdf);
        listView.setAdapter(new PdfAdapter(Reports.this, getPDFs()));

        back_btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                startActivity(new Intent(Reports.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    private ArrayList<PDF_Doc> getPDFs() {

        ArrayList<PDF_Doc> pdfDocs=new ArrayList<PDF_Doc>();
        //TARGET FOLDER
        File downloadsFolder= Environment.getExternalStoragePublicDirectory("PDF");

        PDF_Doc pdfDoc;

        if(downloadsFolder.exists())
        {
            //GET ALL FILES IN DOWNLOAD FOLDER
            File[] files= downloadsFolder.listFiles();

            //LOOP THRU THOSE FILES GETTING NAME AND URI
            for (int i = 0; i < files.length; i++)
            {
                File file=files[i];

                if(file.getPath().endsWith("pdf"))
                {
                    PDF_Doc pdf_doc = new PDF_Doc();
                    pdf_doc.setName(file.getName());
                    pdf_doc.setPath(file.getAbsolutePath());

                    pdfDocs.add(pdf_doc);
                }

            }
        }

        return pdfDocs;
    }


    @Override
    public void onBackPressed() {

        finish();

        startActivity(new Intent(Reports.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();
    }

    void init(){

        back_btn_report = findViewById(R.id.back_btn_report);
    }
}
