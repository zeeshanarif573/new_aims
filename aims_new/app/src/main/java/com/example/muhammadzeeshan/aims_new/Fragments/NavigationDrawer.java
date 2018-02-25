package com.example.muhammadzeeshan.aims_new.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.TemplateManagement;
import com.example.muhammadzeeshan.aims_new.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment {

    View v;
    LinearLayout reports, settings, aboutUs, logout, template_mgt;
    Button drawer_close;


    public NavigationDrawer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        initialization();

        drawer_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().onBackPressed();
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();
           //     startActivity(new Intent(getContext(), Reports.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        template_mgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getContext(), TemplateManagement.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Settings Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "About Us Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Logout Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    void initialization(){

        drawer_close = v.findViewById(R.id.back_arrow);

        template_mgt = v.findViewById(R.id.asset_mgt);
        reports = v.findViewById(R.id.reports);
        settings = v.findViewById(R.id.settings);
        aboutUs = v.findViewById(R.id.aboutUs);
        logout = v.findViewById(R.id.logout);
    }

}
