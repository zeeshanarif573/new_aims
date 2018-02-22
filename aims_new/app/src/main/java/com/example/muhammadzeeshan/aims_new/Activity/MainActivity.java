package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.muhammadzeeshan.aims_new.Fragments.NavigationDrawer;
import com.example.muhammadzeeshan.aims_new.R;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton createAsset;
    android.support.v4.app.FragmentManager fragmentManager;
    ImageView menu_btn;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

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

        if(fragmentManager!= null){
            fragmentManager.popBackStack();

        }
    }

    void initialization(){

        createAsset = findViewById(R.id.createAsset);
        menu_btn = findViewById(R.id.menuBtn);

    }

    void LayoutAnimations() {
        ScaleAnimation fade_in = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(1200);     // animation duration in milliseconds
        fade_in.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.

    }
}
