package com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.MainActivity;
import com.example.muhammadzeeshan.aims_new.Activity.Templates.CheckInTemplate;
import com.example.muhammadzeeshan.aims_new.Activity.Templates.CheckOutTemplate;
import com.example.muhammadzeeshan.aims_new.Activity.Templates.InspectTemplate;
import com.example.muhammadzeeshan.aims_new.Adapter.ViewAssetAdapter;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.R;

import static com.example.muhammadzeeshan.aims_new.Activity.MainActivity.form_list;
import static com.example.muhammadzeeshan.aims_new.Activity.MainActivity.listView;

public class AssetDetails extends AppCompatActivity {

    String getReportId;
    DatabaseHelper databaseHelper;
    LinearLayout mainLayout;
    Button checkOut, checkIn, Inspect;
    ViewAssetAdapter assetsAdapter;
    String status;
    String getAssetId, getAssetName, getTemplateId;
    Typeface ubuntu_light_font, ubuntu_medium_font, source_sans_pro;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);

        databaseHelper = new DatabaseHelper(this);
        mainLayout = findViewById(R.id.detail_main_Layout);
        checkOut = findViewById(R.id.checkOut);
        checkIn = findViewById(R.id.checkIn);
        Inspect = findViewById(R.id.Inspect);

        Intent intent = getIntent();
        getAssetId = intent.getStringExtra("AssetId");
        getTemplateId = intent.getStringExtra("TemplateId");
        getAssetName= intent.getStringExtra("AssetName");

        Toast.makeText(this, getTemplateId, Toast.LENGTH_SHORT).show();

        getWidgets();
        checkOperation();

        if (status.equals("Instock")) {
            checkIn.setVisibility(View.GONE);
            Inspect.setVisibility(View.GONE);
            checkOut.setVisibility(View.VISIBLE);
            checkOut.setText("CheckOut");

        } else if (status.equals("CheckOut")) {
            checkOut.setVisibility(View.GONE);
            checkIn.setVisibility(View.VISIBLE);
            checkIn.setText("CheckIn");
            Inspect.setVisibility(View.VISIBLE);
            Inspect.setText("Inspect");
        }

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getCheckoutData()) {

                    Intent intent = new Intent(AssetDetails.this, CheckOutDetails.class);
                    intent.putExtra("AssetId", getAssetId);
                    intent.putExtra("TemplateId", getTemplateId);
                    intent.putExtra("AssetName", getAssetName);

                    startActivity(intent);

                } else {

                    Snackbar.make(mainLayout, "You first have to create CheckOut Template..", Snackbar.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(AssetDetails.this, CheckOutTemplate.class);
                            intent.putExtra("from", "AssetDetails");
                            intent.putExtra("AssetId", getAssetId);
                            intent.putExtra("TemplateId", getTemplateId);
                            intent.putExtra("AssetName", getAssetName);

                            startActivity(intent);

                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }, 1200);
                }

            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getCheckInData()) {
                    Intent intent = new Intent(AssetDetails.this, CheckInDetails.class);
                    intent.putExtra("AssetId", getAssetId);
                    intent.putExtra("TemplateId", getTemplateId);
                    intent.putExtra("AssetName", getAssetName);


                    startActivity(intent);
                } else {
                    Snackbar.make(mainLayout, "You first have to create CheckIn Template..", Snackbar.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(AssetDetails.this, CheckInTemplate.class);
                            intent.putExtra("from", "AssetDetails");
                            intent.putExtra("AssetId", getAssetId);
                            intent.putExtra("TemplateId", getTemplateId);
                            intent.putExtra("AssetName", getAssetName);

                            startActivity(intent);

                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }, 1200);
                }

            }
        });

        Inspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getInspectData()) {
                    Intent intent = new Intent(AssetDetails.this, InspectDetails.class);
                    intent.putExtra("AssetId", getAssetId);
                    intent.putExtra("TemplateId", getTemplateId);
                    intent.putExtra("AssetName", getAssetName);


                    startActivity(intent);

                } else {
                    Snackbar.make(mainLayout, "You first have to create Inspect Template..", Snackbar.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(AssetDetails.this, InspectTemplate.class);
                            intent.putExtra("from", "AssetDetails");
                            intent.putExtra("AssetId", getAssetId);
                            intent.putExtra("TemplateId", getTemplateId);
                            intent.putExtra("AssetName", getAssetName);

                            startActivity(intent);

                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }, 1200);
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void getWidgets() {

        String val = "SELECT asset_template_data.Data_Id, asset.Asset_Name, asset_template.Widget_Type, asset_template.Widget_Label, asset_template_data.Widget_Data " +
                " FROM asset_template_data " +
                " LEFT JOIN asset ON asset_template_data.Asset_Id = asset.Asset_Id " +
                " LEFT JOIN asset_template ON asset_template_data.Widget_Id = asset_template.Widget_Id" +
                " LEFT JOIN template ON asset_template_data.Template_Id = template.Template_Id " +
                " where asset.Asset_Id = " + getAssetId;

        Cursor cursor = databaseHelper.RetrieveData(val);

        while (cursor.moveToNext()) {

            //Condition for EditText.............................
            if (cursor.getString(2).equals("EditText")) {

                //Parent Layout..............................
                LinearLayout parentLayout = new LinearLayout(this);
                parentLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 10, 0, 0);
                parentLayout.setLayoutParams(parent_params);

                //TextView Label Layout..............................
                LinearLayout label_Layout = new LinearLayout(this);
                LinearLayout.LayoutParams label_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                label_Layout.setLayoutParams(label_Params);

                TextView label = new TextView(this);
                label_Layout.addView(label);

                //TextView Data Layout..............................
                LinearLayout data_Layout = new LinearLayout(this);
                LinearLayout.LayoutParams data_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                data_Params.setMargins(10, 0, 0, 0);
                data_Layout.setLayoutParams(data_Params);

                TextView data = new TextView(this);
                data_Layout.addView(data);

                label.setText(cursor.getString(3) + ":");
                data.setText(cursor.getString(4));

                font();
                label.setTypeface(ubuntu_light_font);
                data.setTypeface(ubuntu_light_font);

                parentLayout.addView(label_Layout);
                parentLayout.addView(data_Layout);

                mainLayout.addView(parentLayout);
            }

            //Condition for CheckBox.............................
            else if (cursor.getString(2).equals("CheckBox")) {

                //Parent Layout..............................
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 10, 0, 0);
                linearLayout.setLayoutParams(parent_params);

                //TextView Label Layout..............................
                LinearLayout label_Layout = new LinearLayout(this);
                LinearLayout.LayoutParams label_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                label_Layout.setLayoutParams(label_Params);

                TextView label = new TextView(this);
                label_Layout.addView(label);


                //TextView Data Layout..............................
                LinearLayout data_Layout = new LinearLayout(this);
                LinearLayout.LayoutParams data_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                data_Params.setMargins(10, 0, 0, 0);
                data_Layout.setLayoutParams(data_Params);

                TextView data = new TextView(this);
                data_Layout.addView(data);

                label.setText(cursor.getString(3) + ":");
                data.setText(cursor.getString(4));

                font();
                label.setTypeface(ubuntu_light_font);
                data.setTypeface(ubuntu_light_font);

                linearLayout.addView(label_Layout);
                linearLayout.addView(data_Layout);

                mainLayout.addView(linearLayout);
            }

            //Condition for TextView.............................
            else if (cursor.getString(2).equals("TextView")) {


                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView label = new TextView(this);
                TextView data = new TextView(this);

                label.setTextSize(16);

                font();
                label.setTypeface(ubuntu_light_font, ubuntu_light_font.BOLD);
                data.setTypeface(ubuntu_light_font, ubuntu_light_font.BOLD);

                label.setText(cursor.getString(3) + ":");
                data.setText(cursor.getString(4));

                linearLayout.addView(label);
                linearLayout.addView(data);

                mainLayout.addView(linearLayout);
            }

            //Condition for Section.............................
            else if (cursor.getString(2).equals("Section")) {


                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView label = new TextView(this);
                TextView data = new TextView(this);

                label.setTextSize(16);

                font();
                label.setTypeface(ubuntu_light_font, ubuntu_light_font.BOLD);
                data.setTypeface(ubuntu_light_font, ubuntu_light_font.BOLD);

                label.setText(cursor.getString(3) + ":");
                data.setText(cursor.getString(4));

                linearLayout.addView(label);
                linearLayout.addView(data);

                mainLayout.addView(linearLayout);
            }

            //Condition for Camera.............................
            else if (cursor.getString(2).equals("Camera")) {

                String getPath = cursor.getString(4);
                String[] splitPath = getPath.split(",");

                for (int i = 0; i < splitPath.length; i++) {

                    Log.e("Path", splitPath[i]);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bitmap = BitmapFactory.decodeFile(splitPath[i], options);

                    TextView textView = new TextView(this);

                    LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    parent_params.setMargins(0, 10, 0, 0);
                    textView.setLayoutParams(parent_params);

                    textView.setText(cursor.getString(3) + ":");

                    font();
                    textView.setTypeface(ubuntu_light_font);

                    LinearLayout image_Layout = new LinearLayout(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300, 400);
                    params.setMargins(10, 10, 0, 0);
                    ImageView imageView = new ImageView(this);
                    imageView.setPadding(0, 10, 0, 10);
                    imageView.setBackground(getResources().getDrawable(R.drawable.custom_status_layout));
                    imageView.setLayoutParams(params);
                    imageView.setImageBitmap(bitmap);

                    mainLayout.addView(textView);
                    image_Layout.addView(imageView);

                    mainLayout.addView(image_Layout);
                }
            }

            //Condition for Signature.............................
            else if (cursor.getString(2).equals("Signature")) {

                TextView label = new TextView(this);
                String getPath = cursor.getString(4);

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 10, 0, 0);
                label.setLayoutParams(parent_params);

                Bitmap bitmap = BitmapFactory.decodeFile(getPath);
                ImageView imageView = new ImageView(this);
                imageView.setPadding(0, 10, 0, 10);
                imageView.setBackground(getResources().getDrawable(R.drawable.custom_status_layout));

                LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                image_params.setMargins(0, 10, 0, 0);
                imageView.setLayoutParams(parent_params);

                label.setText(cursor.getString(3));

                font();
                label.setTypeface(ubuntu_light_font);

                imageView.setImageBitmap(bitmap);

                mainLayout.addView(label);
                mainLayout.addView(imageView);

            }

            //Condition for Date.............................
            else if (cursor.getString(2).equals("Date")) {

                //Parent Layout..............................
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 10, 0, 0);
                linearLayout.setLayoutParams(parent_params);

                //TextView Label Layout..............................
                LinearLayout label_Layout = new LinearLayout(this);
                LinearLayout.LayoutParams label_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                label_Layout.setLayoutParams(label_Params);

                TextView label = new TextView(this);
                label_Layout.addView(label);

                //TextView Data Layout..............................
                LinearLayout data_Layout = new LinearLayout(this);
                LinearLayout.LayoutParams data_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                data_Params.setMargins(10, 0, 0, 0);
                data_Layout.setLayoutParams(data_Params);

                TextView data = new TextView(this);
                data_Layout.addView(data);

                label.setText(cursor.getString(3) + ":");
                data.setText(cursor.getString(4));

                font();
                label.setTypeface(ubuntu_light_font);
                data.setTypeface(ubuntu_light_font);


                linearLayout.addView(label_Layout);
                linearLayout.addView(data_Layout);

                mainLayout.addView(linearLayout);
            }

            //Condition for Time.............................
            else if (cursor.getString(2).equals("Time")) {

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);


                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 10, 0, 0);
                linearLayout.setLayoutParams(parent_params);

                //TextView Label Layout..............................
                LinearLayout label_Layout = new LinearLayout(this);
                LinearLayout.LayoutParams label_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                label_Layout.setLayoutParams(label_Params);

                TextView label = new TextView(this);
                label_Layout.addView(label);

                //TextView Data Layout..............................
                LinearLayout data_Layout = new LinearLayout(this);
                LinearLayout.LayoutParams data_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                data_Params.setMargins(10, 0, 0, 0);
                data_Layout.setLayoutParams(data_Params);

                TextView data = new TextView(this);
                data_Layout.addView(data);


                label.setText(cursor.getString(3) + ":");
                data.setText(cursor.getString(4));

                font();
                label.setTypeface(ubuntu_light_font);
                data.setTypeface(ubuntu_light_font);


                linearLayout.addView(label_Layout);
                linearLayout.addView(data_Layout);

                mainLayout.addView(linearLayout);
            }

        }
    }

    void checkOperation() {

        Cursor cursor = databaseHelper.RetrieveData("SELECT status FROM asset where Asset_Id = '" + getAssetId + "'");

        while (cursor.moveToNext()) {
            status = cursor.getString(0);

            Log.e("Status", status);
        }
    }

    void font() {
        ubuntu_light_font = Typeface.createFromAsset(AssetDetails.this.getAssets(), "font/ubuntu_light.ttf");
        ubuntu_medium_font = Typeface.createFromAsset(AssetDetails.this.getAssets(), "font/Ubuntu-Medium.ttf");
        source_sans_pro = Typeface.createFromAsset(AssetDetails.this.getAssets(), "font/SourceSansPro-Light.ttf");
    }

    boolean getCheckoutData() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String count = "SELECT count(*) FROM checkout where Template_Id = " + getTemplateId;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {

            return true;

        }
        return false;
    }

    boolean getCheckInData() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String count = "SELECT count(*) FROM checkin where Template_Id = " + getTemplateId;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {

            return true;

        }
        return false;
    }

    boolean getInspectData() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String count = "SELECT count(*) FROM inspect where Template_Id = " + getTemplateId;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {

            return true;

        }
        return false;
    }
}