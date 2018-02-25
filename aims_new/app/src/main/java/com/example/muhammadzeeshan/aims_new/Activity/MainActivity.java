package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.AssetDetails;
import com.example.muhammadzeeshan.aims_new.Adapter.ViewAssetAdapter;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.Fragments.NavigationDrawer;
import com.example.muhammadzeeshan.aims_new.Models.newModels.AssetData;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;

import static com.example.muhammadzeeshan.aims_new.GeneralMethods.DeletingRecord;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.progress3;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton createAsset;
    android.support.v4.app.FragmentManager fragmentManager;
    ImageView menu_btn;
    public static ListView listView;
    DatabaseHelper databaseHelper;
    public static ArrayList<AssetData> form_list;
    ViewAssetAdapter adapter;
    AlertDialog.Builder alertDialog;
    String AssetId, AssetName, AssetDescription, Status;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        getAssetData();

        createAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, CreateAsset.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right);

                fragmentTransaction.add(R.id.home_contianer, new NavigationDrawer());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
//
//                alertDialog.setTitle("Alert");
//                alertDialog.setMessage("Are you sure you want to delete this Asset?");
//                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, final int position) {
//
//                        DeletingRecord(view, MainActivity.this);
//
//                        AssetData assetData = form_list.get(position);
//                        databaseHelper.deleteRecordfromAssetsWidgets(assetData.getAsset_id());
//
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                progress3.dismiss();
//
//                                form_list.remove(position);
//                                listView.invalidateViews();
//
//                                Snackbar.make(view, "Record is Deleted...", Snackbar.LENGTH_SHORT).show();
//
//                            }
//                        }, 2000);
//
//                    }
//                });
//
//                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                AlertDialog dialog = alertDialog.create();
//                dialog.show();
//
//                return true;
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                AssetData assetData = form_list.get(position);
                String TemplateId = assetData.getTemplate_id();

                Intent intent = new Intent(MainActivity.this, AssetDetails.class);
                intent.putExtra("TemplateId", TemplateId);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        if (fragmentManager != null) {
            fragmentManager.popBackStack();

        }
    }

    void initialization() {

        alertDialog = new AlertDialog.Builder(this);

        form_list = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.AssetListView);
        createAsset = findViewById(R.id.createAsset);
        menu_btn = findViewById(R.id.menuBtn);

        listView.setEmptyView(findViewById(R.id.welcome_text));

    }

    public void getAssetData() {

        adapter = new ViewAssetAdapter(this, R.layout.view_asset_layout, form_list);
        listView.setAdapter(adapter);

        Cursor cursor = databaseHelper.RetrieveData("SELECT * FROM asset ORDER By Asset_Id DESC");

        while (cursor.moveToNext()) {

            AssetId = cursor.getString(0);
            AssetName = cursor.getString(1);
            AssetDescription = cursor.getString(2);
            Status = cursor.getString(4);

            Log.e("Asset_form_ID ", AssetId + "form_name " + AssetName);

            form_list.add(new AssetData(AssetId, AssetName, AssetDescription, Status));
        }
        adapter.notifyDataSetChanged();

    }

    void LayoutAnimations() {
        ScaleAnimation fade_in = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(1200);     // animation duration in milliseconds
        fade_in.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.

    }
}
