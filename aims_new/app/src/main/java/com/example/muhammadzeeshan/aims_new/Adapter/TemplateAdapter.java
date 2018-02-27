package com.example.muhammadzeeshan.aims_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.muhammadzeeshan.aims_new.Models.TemplateData;
import com.example.muhammadzeeshan.aims_new.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Muhammad Zeeshan on 2/25/2018.
 */

public class TemplateAdapter extends BaseExpandableListAdapter {

    Context context;
    HashMap<String, List<TemplateData>> map;
    List<TemplateData> list;

    public TemplateAdapter(Context context , HashMap<String , List<TemplateData>> map, List<TemplateData> list){

        this.context = context;
        this.map = map;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {

        return map.get(list.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int parent, int child) {
        return map.get(list.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View view, ViewGroup parentView) {

        TemplateData templateData = list.get(parent);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exbandable_listview_parent_layout, parentView, false);
        }

        TextView parentTextView = view.findViewById(R.id.parentText);
        parentTextView.setText(templateData.getTemplate_name());

        return view;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertView, ViewGroup parentGroup) {

        String childTitle = (String) getChild(parent, child);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exbandable_listview_child_layout, parentGroup ,false);
        }

        TextView textView = convertView.findViewById(R.id.childText);
        textView.setText(childTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
