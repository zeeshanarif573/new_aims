package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.NavigationDrawer.Reports;
import com.example.muhammadzeeshan.aims_new.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;

public class PDF_Activity extends AppCompatActivity {

    PDFView pdfView;
    File file;
    Button back_btn_pdf_viewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_);

        back_btn_pdf_viewer = findViewById(R.id.back_btn_pdf_viewer);

        //PDFVIEW SHALL DISPLAY OUR PDFS
        pdfView = (PDFView) findViewById(R.id.pdfView);
        //SCROLLBAR TO ENABLE SCROLLING
        ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
        pdfView.setScrollBar(scrollBar);
        //VERTICAL SCROLLING
        scrollBar.setHorizontal(false);
        //SACRIFICE MEMORY FOR QUALITY
        //pdfView.useBestQuality(true)

        //UNPACK OUR DATA FROM INTENT
        Intent i = this.getIntent();
        String path = i.getExtras().getString("PATH");

        Log.e("path", path);

        //GET THE PDF FILE
        file = new File(path);

        back_btn_pdf_viewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                startActivity(new Intent(PDF_Activity.this , Reports.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        if (file.canRead()) {
            //LOAD IT
            pdfView.fromFile(file)
                    .defaultPage(1)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .swipeVertical(true)
                    .showMinimap(false)
                    .onLoad(new OnLoadCompleteListener() {
                public void loadComplete(int nbPages) {
                    Toast.makeText(PDF_Activity.this, "Pdf Load Successfully...", Toast.LENGTH_SHORT).show();
                }
            }).load();
        }
    }

}
