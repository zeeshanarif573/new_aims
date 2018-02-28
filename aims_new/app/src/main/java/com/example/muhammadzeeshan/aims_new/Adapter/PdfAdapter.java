package com.example.muhammadzeeshan.aims_new.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.PDF_Activity;
import com.example.muhammadzeeshan.aims_new.Models.PDF_Doc;
import com.example.muhammadzeeshan.aims_new.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Muhammad Zeeshan on 1/29/2018.
 */

public class PdfAdapter extends BaseAdapter {

    Context c;
    String email, subject;
    ArrayList<PDF_Doc> pdfDocs;

    public PdfAdapter(@NonNull Context context, ArrayList<PDF_Doc> list) {

        this.c = context;
        this.pdfDocs = list;
    }

    @Override
    public int getCount() {
        return pdfDocs.size();
    }

    @Override
    public Object getItem(int i) {
        return pdfDocs.get(getCount() - i - 1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            //INFLATE CUSTOM LAYOUT
            view = LayoutInflater.from(c).inflate(R.layout.custom_pdf_layout, viewGroup, false);
        }

        final PDF_Doc pdfDoc = (PDF_Doc) this.getItem(i);

        TextView nameTxt = (TextView) view.findViewById(R.id.nameTxt);
        ImageView img = (ImageView) view.findViewById(R.id.pdfImage);
        Button export = (Button) view.findViewById(R.id.export_pdf);

        //BIND DATA
        nameTxt.setText(pdfDoc.getName());
        img.setImageResource(R.drawable.ic_action_pdf_2);

        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openPDFView(pdfDoc.getPath());
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                String pathToMyAttachedFile = pdfDoc.getPath();

                File file = new File(pathToMyAttachedFile);
                if (!file.exists() || !file.canRead()) {
                    Toast.makeText(c, "File not Exist", Toast.LENGTH_SHORT).show();
                }
                Uri uri = Uri.fromFile(file);

                Log.e("URI", uri.toString());

                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                c.startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
            }
        });

        return view;
    }

    private void openPDFView(String path) {
        Intent i = new Intent(c, PDF_Activity.class);
        i.putExtra("PATH", path);
        c.startActivity(i);
    }

}
