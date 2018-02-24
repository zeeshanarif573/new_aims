package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.muhammadzeeshan.aims_new.Adapter.ViewAssetAdapter;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.Fragments.NavigationDrawer;
import com.example.muhammadzeeshan.aims_new.Models.newModels.AssetData;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton createAsset;
    android.support.v4.app.FragmentManager fragmentManager;
    ImageView menu_btn;
    ListView listView;
    DatabaseHelper databaseHelper;
    ArrayList<AssetData> form_list;
    ViewAssetAdapter adapter;
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
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

        if (fragmentManager != null) {
            fragmentManager.popBackStack();

        }
    }

    void initialization() {

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

            Log.e("Asset_form_ID ",  AssetId + "form_name "+ AssetName);

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
