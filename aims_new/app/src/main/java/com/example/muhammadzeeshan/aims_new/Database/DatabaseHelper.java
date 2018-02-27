package com.example.muhammadzeeshan.aims_new.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.muhammadzeeshan.aims_new.Models.AssetData;
import com.example.muhammadzeeshan.aims_new.Models.AssetTemplate.AssetTemplateData;
import com.example.muhammadzeeshan.aims_new.Models.AssetTemplate.AssetTemplatesWidgets;
import com.example.muhammadzeeshan.aims_new.Models.CheckIn.CheckInWidgets;
import com.example.muhammadzeeshan.aims_new.Models.CheckOut.CheckOutData;
import com.example.muhammadzeeshan.aims_new.Models.CheckOut.CheckOutWidgets;
import com.example.muhammadzeeshan.aims_new.Models.Inspect.InspectWidgets;
import com.example.muhammadzeeshan.aims_new.Models.TemplateData;

/**
 * Created by Muhammad Zeeshan on 12/30/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "ty";

    protected static final String TABLE_ASSET = "asset";
    protected static final String TABLE_TEMPLATE = "template";
    protected static final String TABLE_ASSET_TEMPLATE = "asset_template";
    protected static final String TABLE_CHECKOUT = "checkout";
    protected static final String TABLE_CHECKIN = "checkin";
    protected static final String TABLE_INSPECT = "inspect";

    protected static final String TABLE_ASSET_TEMPLATE_DATA = "asset_template_data";
    protected static final String TABLE_CHECKOUT_DATA = "checkout_data";
    protected static final String TABLE_CHECKIN_DATA = "checkin_data";
    protected static final String TABLE_INSPECT_DATA = "inspect_data";


    String CREATE_TABLE_ASSET = "CREATE TABLE " + TABLE_ASSET +
            "( Asset_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Asset_Name TEXT, " +
            "Asset_Description TEXT, " +
            "Asset_Type TEXT, " +
            "Status TEXT, " +
            "Template_Id INTEGER," +
            "FOREIGN KEY (Template_Id) REFERENCES Template(Template_Id)); ";

    String CREATE_TABLE_TEMPLATE = "CREATE TABLE " + TABLE_TEMPLATE +
            "( Template_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Template_Name TEXT, " +
            "Template_Description TEXT); ";

    String CREATE_TABLE_ASSET_TEMPLATE = "CREATE TABLE " + TABLE_ASSET_TEMPLATE +
            "( Widget_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Widget_Type TEXT, " +
            "Widget_Label TEXT, " +
            "Template_Id INTEGER, " +
            "FOREIGN KEY (Template_Id) REFERENCES template(Template_Id)); ";

    String CREATE_TABLE_CHECKOUT = "CREATE TABLE " + TABLE_CHECKOUT +
            "( Widget_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Widget_Type TEXT, " +
            "Widget_Label TEXT, " +
            "Template_Id INTEGER, " +
            "FOREIGN KEY (Template_Id) REFERENCES template(Template_Id)); ";

    String CREATE_TABLE_CHECKIN = "CREATE TABLE " + TABLE_CHECKIN +
            "( Widget_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Widget_Type TEXT, " +
            "Widget_Label TEXT, " +
            "Template_Id INTEGER, " +
            "FOREIGN KEY (Template_Id) REFERENCES template(Template_Id)); ";

    String CREATE_TABLE_INSPECT = "CREATE TABLE " + TABLE_INSPECT +
            "( Widget_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Widget_Type TEXT, " +
            "Widget_Label TEXT, " +
            "Template_Id INTEGER, " +
            "FOREIGN KEY (Template_Id) REFERENCES template(Template_Id)); ";

    String CREATE_TABLE_ASSET_TEMPLATE_DATA = "CREATE TABLE " + TABLE_ASSET_TEMPLATE_DATA +
            "( Data_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Template_Id TEXT, " +
            "Asset_Id TEXT, " +
            "Widget_Id INTEGER, " +
            "Widget_Data INTEGER, " +
            "FOREIGN KEY (Template_Id) REFERENCES template(Template_Id) " +
            "FOREIGN KEY (Asset_Id) REFERENCES asset(Asset_Id)" +
            "FOREIGN KEY (Widget_Id) REFERENCES asset_template(Widget_Id)); ";

    String CREATE_TABLE_CHECKOUT_DATA = "CREATE TABLE " + TABLE_CHECKOUT_DATA +
            "( Data_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Template_Id TEXT, " +
            "Asset_Id TEXT, " +
            "Widget_Id INTEGER, " +
            "Widget_Data INTEGER, " +
            "FOREIGN KEY (Template_Id) REFERENCES template(Template_Id) " +
            "FOREIGN KEY (Asset_Id) REFERENCES asset(Asset_Id)" +
            "FOREIGN KEY (Widget_Id) REFERENCES asset_template(Widget_Id)); ";

    String CREATE_TABLE_CHECKIN_DATA = "CREATE TABLE " + TABLE_CHECKIN_DATA +
            "( Data_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Template_Id TEXT, " +
            "Asset_Id TEXT, " +
            "Widget_Id INTEGER, " +
            "Widget_Data INTEGER, " +
            "FOREIGN KEY (Template_Id) REFERENCES template(Template_Id) " +
            "FOREIGN KEY (Asset_Id) REFERENCES asset(Asset_Id)" +
            "FOREIGN KEY (Widget_Id) REFERENCES asset_template(Widget_Id)); ";

    String CREATE_TABLE_INSPECT_DATA = "CREATE TABLE " + TABLE_INSPECT_DATA +
            "( Data_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Template_Id TEXT, " +
            "Asset_Id TEXT, " +
            "Widget_Id INTEGER, " +
            "Widget_Data INTEGER, " +
            "FOREIGN KEY (Template_Id) REFERENCES template(Template_Id) " +
            "FOREIGN KEY (Asset_Id) REFERENCES asset(Asset_Id)" +
            "FOREIGN KEY (Widget_Id) REFERENCES asset_template(Widget_Id)); ";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_ASSET);
        db.execSQL(CREATE_TABLE_ASSET_TEMPLATE);
        db.execSQL(CREATE_TABLE_CHECKOUT);
        db.execSQL(CREATE_TABLE_CHECKIN);
        db.execSQL(CREATE_TABLE_TEMPLATE);
        db.execSQL(CREATE_TABLE_INSPECT);
        db.execSQL(CREATE_TABLE_ASSET_TEMPLATE_DATA);
        db.execSQL(CREATE_TABLE_CHECKOUT_DATA);
        db.execSQL(CREATE_TABLE_CHECKIN_DATA);
        db.execSQL(CREATE_TABLE_INSPECT_DATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String sql1 = "DROP TABLE IF EXISTS " + CREATE_TABLE_ASSET;
        String sql2 = "DROP TABLE IF EXISTS " + CREATE_TABLE_ASSET_TEMPLATE;
        String sql3 = "DROP TABLE IF EXISTS " + CREATE_TABLE_CHECKOUT;
        String sql4 = "DROP TABLE IF EXISTS " + CREATE_TABLE_CHECKIN;
        String sql5 = "DROP TABLE IF EXISTS " + CREATE_TABLE_TEMPLATE;
        String sql6 = "DROP TABLE IF EXISTS " + CREATE_TABLE_INSPECT;
        String sql7 = "DROP TABLE IF EXISTS " + CREATE_TABLE_ASSET_TEMPLATE_DATA;
        String sql8 = "DROP TABLE IF EXISTS " + CREATE_TABLE_CHECKOUT_DATA;
        String sql9 = "DROP TABLE IF EXISTS " + CREATE_TABLE_CHECKIN_DATA;
        String sql10 = "DROP TABLE IF EXISTS " + CREATE_TABLE_INSPECT_DATA;

        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);
        db.execSQL(sql9);
        db.execSQL(sql10);

        onCreate(db);
    }

    //Insert Data Into Asset Table.....................
    public boolean insertDataIntoAsset(AssetData data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Template_Id", data.getTemplate_id());
        values.put("Asset_Name", data.getAsset_name());
        values.put("Asset_Description", data.getAsset_description());
        values.put("Asset_Type", data.getAsset_type());
        values.put("Status", data.getStatus());

        long result = db.insert(TABLE_ASSET, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Insert Data Into Template Table.....................
    public boolean insertDataIntoTemplate(TemplateData template_data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Template_Name", template_data.getTemplate_name());
        values.put("Template_Description", template_data.getTemplate_description());

        long result = db.insert(TABLE_TEMPLATE, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Insert Data Into Asset_Template Table.....................
    public boolean insertDataIntoAssetTemplate(AssetTemplatesWidgets widgets) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Template_Id", widgets.getTemplate_id());
        values.put("Widget_Type", widgets.getWidget_type());
        values.put("Widget_Label", widgets.getWidget_label());

        long result = db.insert(TABLE_ASSET_TEMPLATE, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Insert Data Into CheckOut Table.....................
    public boolean insertDataIntoCheckoutTemplate(CheckOutWidgets widgets) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Template_Id", (widgets.getTemplate_id()));
        values.put("Widget_Type", widgets.getWidget_type());
        values.put("Widget_Label", widgets.getWidget_label());

        long result = db.insert(TABLE_CHECKOUT, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Insert Data Into CheckIn Table.....................
    public boolean insertDataIntoCheckinTemplate(CheckInWidgets widgets) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Template_Id", (widgets.getTemplate_id()));
        values.put("Widget_Type", widgets.getWidget_type());
        values.put("Widget_Label", widgets.getWidget_label());

        long result = db.insert(TABLE_CHECKIN, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Insert Data Into Inspect Table.....................
    public boolean insertDataIntoInspectTemplate(InspectWidgets widgets) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Template_Id", (widgets.getTemplate_id()));
        values.put("Widget_Type", widgets.getWidget_type());
        values.put("Widget_Label", widgets.getWidget_label());

        long result = db.insert(TABLE_INSPECT, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Insert Record Into Asset_Template_Data Table.....................
    public boolean insertIntoAssetTemplateData(AssetTemplateData widgets) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Template_Id", (widgets.getTemplate_Id()));
        values.put("Asset_Id", widgets.getAsset_Id());
        values.put("Widget_Id", widgets.getWidget_Id());
        values.put("Widget_data", widgets.getWidget_Data());

        long result = db.insert(TABLE_ASSET_TEMPLATE_DATA, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Insert Record Into Checkout Table.....................
    public boolean insertIntoCheckoutData(CheckOutData widgets) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Template_Id", (widgets.getTemplate_Id()));
        values.put("Asset_Id", widgets.getAsset_Id());
        values.put("Widget_Id", widgets.getWidget_Id());
        values.put("Widget_data", widgets.getWidget_Data());

        long result = db.insert(TABLE_CHECKOUT_DATA, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }





    //Retrieve Data from Table......................
    public Cursor RetrieveData(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, null);
    }


    //Update Data Into Asset Table......................
    public int UpdateAssetTable(String id, String data) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("status", data);

        int count = database.update(TABLE_ASSET, values, "Asset_Id = '" + id + "'", null);
        return count;
    }


    //Delete Data in Form Table,.......................
    public Integer deleteRecordfromAssetsWidgets(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ASSET, "Asset_Id = ?", new String[]{id});
    }


    //Insert Data Into Asset Form Table.....................
//    public boolean insertDataIntoAssetForms(AssetFormData data) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("Asset_Form_Name", data.getAsset_form_name());
//        values.put("Asset_Form_Description", data.getAsset_form_description());
//        values.put("Asset_Operations", data.getAsset_Operations());
//
//        long result = db.insert(TABLE_ASSET_FORMS, null, values);
//
//        if (result == -1)
//            return false;
//        else
//            return true;
//    }

    //Insert Data Into Asset Widget Table.....................
//    public boolean insertDataIntoAssetWidgets(Asset_Widgets_Data widgetsData) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("Asset_Form_Id", widgetsData.getAsset_form_id());
//        values.put("Asset_Widget_type", widgetsData.getAsset_widget_type());
//        values.put("Asset_Widget_label", widgetsData.getAsset_widget_label());
//        values.put("Asset_Widget_data", widgetsData.getAsset_widget_data());
//
//        long result = db.insert(TABLE_ASSET_WIDGETS, null, values);
//
//        if (result == -1)
//            return false;
//        else
//            return true;
//    }


    //Update Data Into Widgets Table......................
//    public int UpdateAssetWidgetTable(String id, String data) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("Asset_Widget_Data", data);
//
//        int count = database.update(TABLE_ASSET_WIDGETS, values, "Asset_Widget_Id = '" + id + "'", null);
//        return count;
//    }

    //Update Operations in Asset Form Table,.......................
//    public int UpdateAssetFormTable(String id, String data) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("Asset_Operations", data);
//
//        int count = database.update(TABLE_ASSET_FORMS, values, "Asset_Form_Id = '" + id + "'", null);
//        return count;
//    }

    //Delete Data in Form Table,.......................
//    public Integer deleteRecordfromForm(String id)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_FORMS, "Form_Id = ?", new String[]{id});
//    }

    //Delete Data in Asset Form Table,.......................
//    public Integer deleteRecordfromAssetWidgets(String id)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_ASSET_WIDGETS, "Asset_Form_Id = ?", new String[]{id});
//    }

    //Delete Data in Asset Form Table,.......................
//    public Integer deleteRecordfromAssetForm(String id)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_ASSET_FORMS, "Asset_Form_Id = ?", new String[]{id});
//    }

}