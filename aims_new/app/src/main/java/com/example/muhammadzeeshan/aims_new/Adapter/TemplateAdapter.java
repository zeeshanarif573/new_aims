package com.example.muhammadzeeshan.aims_new.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.CheckInDetails;
import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.CheckOutDetails;
import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.InspectDetails;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.Models.TemplateDescription;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;

/**
 * Created by Muhammad Zeeshan on 2/25/2018.
 */

public class TemplateAdapter extends ArrayAdapter<TemplateDescription> {

    DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
    TemplateDescription templateDescription;

    public TemplateAdapter(@NonNull Context context, ArrayList<TemplateDescription> templateList) {
        super(context, R.layout.custom_template_management_layout, templateList);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            v = LayoutInflater.from(getContext()).inflate(R.layout.custom_template_management_layout, parent, false);

        }

        templateDescription = getItem(position);

        TextView custom_template_title = v.findViewById(R.id.custom_template_title);
        LinearLayout checkOut = v.findViewById(R.id.custom_checkOut);
        LinearLayout checkIn = v.findViewById(R.id.custom_checkIn);
        LinearLayout inspect = v.findViewById(R.id.custom_Inspect);
        View divider_01 = v.findViewById(R.id.divider_01);
        View divider_02 = v.findViewById(R.id.divider_02);

        custom_template_title.setText(templateDescription.getTemplate_name());

        //CheckCheckoutTemplate()....................
        if (CheckCheckoutTemplate()) {
            checkOut.setVisibility(View.VISIBLE);
            checkIn.setVisibility(View.GONE);
            inspect.setVisibility(View.GONE);
            divider_01.setVisibility(View.GONE);
            divider_02.setVisibility(View.GONE);
        }

        //CheckCheckinTemplate()..........
        if (CheckCheckinTemplate()) {
            checkIn.setVisibility(View.VISIBLE);
            checkOut.setVisibility(View.GONE);
            inspect.setVisibility(View.GONE);
            divider_01.setVisibility(View.GONE);
            divider_02.setVisibility(View.GONE);

        }

        //CheckInspectTemplate().................
        if (CheckInspectTemplate()) {
            inspect.setVisibility(View.VISIBLE);
            checkIn.setVisibility(View.GONE);
            checkOut.setVisibility(View.GONE);
            divider_01.setVisibility(View.GONE);
            divider_02.setVisibility(View.GONE);
        }

        //CheckCheckoutTemplate() && CheckCheckinTemplate()...........
        if (CheckCheckoutTemplate() && CheckCheckinTemplate()) {

            checkOut.setVisibility(View.VISIBLE);
            divider_01.setVisibility(View.VISIBLE);
            checkIn.setVisibility(View.VISIBLE);
            inspect.setVisibility(View.GONE);
            divider_02.setVisibility(View.GONE);
        }

        //CheckCheckoutTemplate() && CheckInspectTemplate()...........
        if (CheckCheckoutTemplate() && CheckInspectTemplate()) {

            checkOut.setVisibility(View.VISIBLE);
            divider_01.setVisibility(View.VISIBLE);
            divider_02.setVisibility(View.GONE);
            inspect.setVisibility(View.VISIBLE);
            checkIn.setVisibility(View.GONE);
        }

        //CheckCheckinTemplate() && CheckInspectTemplate()........
        if (CheckCheckinTemplate() && CheckInspectTemplate()) {

            checkIn.setVisibility(View.VISIBLE);
            divider_01.setVisibility(View.GONE);
            checkOut.setVisibility(View.GONE);
            divider_02.setVisibility(View.VISIBLE);
            inspect.setVisibility(View.VISIBLE);
        }

        //CheckCheckoutTemplate() && CheckCheckinTemplate() && CheckInspectTemplate()....
        if (CheckCheckoutTemplate() && CheckCheckinTemplate() && CheckInspectTemplate()) {

            checkOut.setVisibility(View.VISIBLE);
            divider_01.setVisibility(View.VISIBLE);
            checkIn.setVisibility(View.VISIBLE);
            divider_02.setVisibility(View.VISIBLE);
            inspect.setVisibility(View.VISIBLE);
        }


        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                templateDescription = getItem(position);

                String CheckoutTemplateId = templateDescription.getTemplate_id();
                Intent intent = new Intent(getContext(), CheckOutDetails.class);
                intent.putExtra("CheckoutTemplateId" ,CheckoutTemplateId);
                intent.putExtra("from" ,"TemplateAdapter");
                getContext().startActivity(intent);

            }
        });

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                templateDescription = getItem(position);
                String CheckinTemplateId = templateDescription.getTemplate_id();
                Intent intent = new Intent(getContext(), CheckInDetails.class);
                intent.putExtra("CheckinTemplateId" ,CheckinTemplateId);
                intent.putExtra("from" ,"TemplateAdapter");
                getContext().startActivity(intent);

            }
        });

        inspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                templateDescription = getItem(position);

                String InspectTemplateId = templateDescription.getTemplate_id();

                Intent intent = new Intent(getContext(), InspectDetails.class);
                intent.putExtra("InspectTemplateId" ,InspectTemplateId);
                intent.putExtra("from" ,"TemplateAdapter");
                getContext().startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    boolean CheckCheckoutTemplate() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String count = "SELECT count(*) FROM checkout where Template_Id = " + templateDescription.getTemplate_id();
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            return true;
        }
        return false;
    }

    boolean CheckCheckinTemplate() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String count = "SELECT count(*) FROM checkin where Template_Id = " + templateDescription.getTemplate_id();
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            return true;
        }
        return false;
    }

    boolean CheckInspectTemplate() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String count = "SELECT count(*) FROM inspect where Template_Id = " + templateDescription.getTemplate_id();
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0) {
            return true;
        }
        return false;
    }

}