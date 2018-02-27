package com.example.muhammadzeeshan.aims_new.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.muhammadzeeshan.aims_new.Models.AssetData;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;

/**
 * Created by Muhammad Zeeshan on 2/24/2018.
 */


public class ViewAssetAdapter extends BaseAdapter {

    Context c;
    ArrayList<AssetData> formList;

    public ViewAssetAdapter(@NonNull Context context, int Resources, ArrayList<AssetData> formList) {

        this.c = context;
        this.formList = formList;
    }


    @Override
    public int getCount() {
        return formList.size();
    }

    @Override
    public Object getItem(int i) {
        return formList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            //INFLATE CUSTOM LAYOUT
            view = LayoutInflater.from(c).inflate(R.layout.view_asset_layout, viewGroup, false);
        }

        final AssetData assetFormData = (AssetData) this.getItem(position);

        TextView nameTxt = (TextView) view.findViewById(R.id.nameTxt);
        TextView operation = (TextView) view.findViewById(R.id.operation);
        ImageView img = (ImageView) view.findViewById(R.id.pdfImage);

        //BIND DATA
        nameTxt.setText(assetFormData.getAsset_name());
        operation.setText(assetFormData.getStatus());
        img.setImageResource(R.drawable.ic_action_report);

        return view;
    }
}
