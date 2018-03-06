package com.example.muhammadzeeshan.aims_new.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.muhammadzeeshan.aims_new.Activity.NavigationDrawer.TemplateManagement;
import com.example.muhammadzeeshan.aims_new.Models.TemplateDescription;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Muhammad Zeeshan on 2/25/2018.
 */

public class TemplateAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> itemList; // header titles
    private List<TemplateDescription> listDataHeader; // header titles
    private HashMap<TemplateDescription, List<String>> listDataChild;


    public TemplateAdapter(Context context, List<TemplateDescription> listDataHeader, HashMap<TemplateDescription, List<String>> listDataChild) {

        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;

        itemList = new ArrayList<>();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exbandable_listview_child_layout, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.childText);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        TemplateDescription templateDescription = listDataHeader.get(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exbandable_listview_parent_layout, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.parentText);
        lblListHeader.setText(templateDescription.getTemplate_name());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
