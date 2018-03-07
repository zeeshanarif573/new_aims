package com.example.muhammadzeeshan.aims_new.Activity.NavigationDrawer;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.MainActivity;
import com.example.muhammadzeeshan.aims_new.R;

import java.io.File;

import static com.example.muhammadzeeshan.aims_new.GeneralMethods.CreatingHeader;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.CreatingPdf;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.creatingheader;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.creatingpdf;

public class Settings extends AppCompatActivity {

    CardView managePdfSetting;
    Dialog pdfDialog;
    Button generate_settings, selectLogo, back_btn_settings;
    EditText header, footer;
    Uri uri;
    File uriToFile;
    Bitmap bitmap;
    ImageView displayLogo;
    String header_name, footer_name, logo, getHeader, getFooter;
    SharedPreferences.Editor editor;
    private int PICK_IMAGE_REQUEST = 20;
    public static final String DEFAULT = "";
    SharedPreferences pref;
    Intent intent;
    String ImageDecode;
    String from = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {

            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                uri = data.getData();

                String[] FILE = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(uri, FILE, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();

                displayLogo.setVisibility(View.VISIBLE);
                displayLogo.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialization();

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra("from")) {
                from = getIntent().getStringExtra("from");
            }
        }


        pref = getApplicationContext().getSharedPreferences("PdfData", 0);
        editor = pref.edit();

        //Click Listener for choose Image for Logo in Pdf Viewer.................
        selectLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, 1);
            }
        });


        managePdfSetting.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                pdfDialog.show();

                //Get Data From Shared Preference...............................
                header_name = pref.getString("headerName", DEFAULT);
                footer_name = pref.getString("footerName", DEFAULT);
                logo = pref.getString("Logo", DEFAULT);

                if (header_name.equals(DEFAULT) || footer_name.equals(DEFAULT) || logo.equals(DEFAULT)) {

                    Toast.makeText(Settings.this, "No Data Found...", Toast.LENGTH_SHORT).show();

                    header.setText("");
                    footer.setText("");
                    displayLogo.setVisibility(View.GONE);

                } else {

                    Log.e("SharedPreference", "HeaderName: " + header_name + " ,FooterName: " + footer_name + " ,Logo: " + logo);
                    header.setText(header_name);
                    footer.setText(footer_name);

                    Uri myUri = Uri.parse(logo);
                    String[] FILE = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(myUri, FILE, null, null, null);

                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(FILE[0]);
                    ImageDecode = cursor.getString(columnIndex);
                    cursor.close();

                    displayLogo.setVisibility(View.VISIBLE);
                    displayLogo.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));

                }


                generate_settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pdfDialog.dismiss();

                        if (from.equalsIgnoreCase("AssetTemplateDetails")) {

                            CreatingHeader(view, Settings.this);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    creatingheader.dismiss();

                                    //GetData from EditText...............................
                                    getHeader = header.getText().toString();
                                    getFooter = footer.getText().toString();
                                    logo = String.valueOf(uri);

                                    //Save Data Into Shared Preference...............................
                                    editor.putString("headerName", getHeader);
                                    editor.putString("footerName", getFooter);
                                    editor.putString("Logo", logo);

                                    Log.e("putSharedPreference", "putHeaderName: " + getHeader + " ,putFooterName: " + getFooter + " ,putLogo: " + logo);


                                    editor.commit();

                                    finish();
//                                    Intent intent = new Intent(Settings.this, MainActivity.class);
//                                    startActivity(intent);
                                }
                            }, 2000);

                        } else {

                            CreatingHeader(view, Settings.this);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    creatingheader.dismiss();

                                    //GetData from EditText...............................
                                    getHeader = header.getText().toString();
                                    getFooter = footer.getText().toString();
                                    logo = String.valueOf(uri);

                                    //Save Data Into Shared Preference...............................
                                    editor.putString("headerName", getHeader);
                                    editor.putString("footerName", getFooter);
                                    editor.putString("Logo", logo);

                                    Log.e("putSharedPreference", "putHeaderName: " + getHeader + " ,putFooterName: " + getFooter + " ,putLogo: " + logo);


                                    editor.commit();

                                    Intent intent = new Intent(Settings.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }, 2000);

                        }

                    }
                });
            }
        });

        back_btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(new Intent(Settings.this , MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    void initialization() {

        managePdfSetting = findViewById(R.id.managePdfSetting);
        pdfDialog = new Dialog(this);
        pdfDialog.setContentView(R.layout.pdf_setting_layout);
        pdfDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pdfDialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        generate_settings = pdfDialog.findViewById(R.id.generate_settings);
        selectLogo = pdfDialog.findViewById(R.id.selectLogo);
        header = pdfDialog.findViewById(R.id.header_name);
        footer = pdfDialog.findViewById(R.id.footer_name);
        displayLogo = pdfDialog.findViewById(R.id.displayLogo);

        back_btn_settings = findViewById(R.id.back_btn_settings);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        startActivity(new Intent(Settings.this , MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}