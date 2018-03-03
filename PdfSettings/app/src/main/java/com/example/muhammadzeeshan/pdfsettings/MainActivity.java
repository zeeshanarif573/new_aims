package com.example.muhammadzeeshan.pdfsettings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String FILE = Environment.getExternalStorageDirectory().toString() + "/PDF/qw.pdf";

        Log.e("File: ", FILE);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/PDF");
        myDir.mkdirs();

        // create document
        Document document = new Document(PageSize.A4, 36, 36, 90, 36);

        try {
            PdfWriter  writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));

            // add header and footer
            HeaderFooterPageEvent event = new HeaderFooterPageEvent(this);
            writer.setPageEvent(event);

            // write to document
            document.open();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {

            Paragraph paragraph1 = new Paragraph();
            Paragraph paragraph2 = new Paragraph();
            Paragraph paragraph3 = new Paragraph();
            Paragraph paragraph4 = new Paragraph();
            Paragraph paragraph5 = new Paragraph();
            Paragraph paragraph6 = new Paragraph();


            paragraph1.add("\n");
            paragraph1.add("\n");

            paragraph1.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
            paragraph1.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD));

            paragraph2.add("\n");

            paragraph2.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");

            paragraph3.add("\n");

            paragraph3.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");

            paragraph4.add("\n");

            paragraph4.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");

            paragraph5.add("\n");

            paragraph5.add("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");

            paragraph6.add("\n");

            try {
                // get input stream
                InputStream ims = getAssets().open("dp.jpg");
                Bitmap bmp = BitmapFactory.decodeStream(ims);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                paragraph6.add(image);
            }
            catch(IOException ex)
            {
                return;
            }


            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

//        document.newPage();

//        try {
//            document.add(new Paragraph("Adding a footer to PDF Document using iText."));
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }

        document.newPage();
        document.close();

    }

}
