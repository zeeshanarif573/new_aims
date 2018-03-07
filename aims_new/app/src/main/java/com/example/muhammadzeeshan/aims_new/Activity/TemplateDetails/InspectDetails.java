package com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.HeaderFooterPageEvent;
import com.example.muhammadzeeshan.aims_new.Activity.MainActivity;
import com.example.muhammadzeeshan.aims_new.Activity.NavigationDrawer.TemplateManagement;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.GeneralMethods;
import com.example.muhammadzeeshan.aims_new.Models.AssetTemplate.AssetTemplatesWidgets;
import com.example.muhammadzeeshan.aims_new.Models.CameraModel;
import com.example.muhammadzeeshan.aims_new.Models.DateModel;
import com.example.muhammadzeeshan.aims_new.Models.SignaturePadModel;
import com.example.muhammadzeeshan.aims_new.Models.TemplateData;
import com.example.muhammadzeeshan.aims_new.Models.Widgets_Model;
import com.example.muhammadzeeshan.aims_new.R;
import com.example.muhammadzeeshan.aims_new.Utility.utils;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.muhammadzeeshan.aims_new.GeneralMethods.SavingData;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.getArrayList;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.getOutputMediaFile;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.progress1;

public class InspectDetails extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    String Widget_Id, generate_type, generate_label;
    LinearLayout layout;
    ArrayList<Widgets_Model> widgetList;
    Dialog date_dialog, time_dialog;
    DatePicker date_picker;
    Button getDate, getTime, back_btn_generate_widget;
    TimePicker time_picker;
    EditText report_name, header_name, footer_name;
    Button continue_dialog, logo, generate_report;
    ImageView dis_Image;
    LinearLayout displayImages;
    ArrayList<CameraModel> ImageList;
    AlertDialog.Builder alertDialog;
    Dialog dialog, get_header_footer_pdf;
    Button doneInspectDetails;
    int hour, minute;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    Uri uri;
    File uriToFile;
    ArrayList<AssetTemplatesWidgets> generate_list;
    ArrayList<SignaturePadModel> padList;
    ArrayList<File> signatureImage;
    String time, get_hour, get_min;
    File photoFile;
    private int PICK_IMAGE_REQUEST = 20;
    String date;
    GeneralMethods generalMethods;
    String getInspect_AssetId, getCheckin_TemplateId, getInspect_AssetName;
    private ArrayList<DateModel> labelTextViewList = new ArrayList<>();
    private DateModel latestLabel;
    Date currentTime;
    PdfWriter writer;
    String from = "", InspectTemplateId;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_details);

        databaseHelper = new DatabaseHelper(this);

        currentTime = Calendar.getInstance().getTime();

        Log.e("Id", "Template_ID: " + getCheckin_TemplateId + " Asset_ID: " + getInspect_AssetId);
        init();

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra("from")) {

                from = getIntent().getStringExtra("from");
                InspectTemplateId = getIntent().getStringExtra("InspectTemplateId");
                Log.e("InspectTemplateId", InspectTemplateId);

            } else if (getIntent().hasExtra("fromAssetDetails")) {

                from = getIntent().getStringExtra("fromAssetDetails");
                getCheckin_TemplateId = getIntent().getStringExtra("AssetId");
                getInspect_AssetId = getIntent().getStringExtra("TemplateId");
                getInspect_AssetName = getIntent().getStringExtra("AssetName");

                Log.e("getInspect_AssetId", getInspect_AssetId);

            } else if (getIntent().hasExtra("fromInspectTemplate")) {

                from = getIntent().getStringExtra("fromInspectTemplate");
                getCheckin_TemplateId = getIntent().getStringExtra("AssetId");
                getInspect_AssetId = getIntent().getStringExtra("TemplateId");
                getInspect_AssetName = getIntent().getStringExtra("AssetName");

                Log.e("fromInspectTemplate", getInspect_AssetId);
            }
        }


        if (from.equalsIgnoreCase("TemplateAdapter")) {
            getCreatedWidgetsForTemplateAdapter();
            doneInspectDetails.setVisibility(View.GONE);

        } else if (from.equalsIgnoreCase("AssetDetails")) {
            getCreatedWidgets();

        } else if (from.equalsIgnoreCase("InspectTemplate")) {
            getCreatedWidgets();
        }


        doneInspectDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                SavingData(view, InspectDetails.this);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progress1.dismiss();

                        getInspectInfo();
                        InsertDataIntoInspect();
                        generateReport();
                        getInspectData();

                        Snackbar.make(layout, "Record saved Successfully..", Snackbar.LENGTH_LONG).show();
                        startActivity(new Intent(InspectDetails.this, MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
                }, 2000);

            }
        });
    }

    void generateReport() {

        String FILE = Environment.getExternalStorageDirectory().toString() + "/PDF/" + getInspect_AssetName + "\\" + currentTime + ".pdf";

        Log.e("File: ", FILE);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/PDF");
        myDir.mkdirs();

        // create document
        Document document = new Document(PageSize.A4, 36, 36, 90, 36);

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));

            // add header and footer
            HeaderFooterPageEvent event = new HeaderFooterPageEvent(this);
            writer.setPageEvent(event);

            // write to document
            document.open();
            addTitlePage(document);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Toast.makeText(InspectDetails.this, "PDF Genrated Successfully", Toast.LENGTH_SHORT).show();

        Log.e("File_Path", FILE);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void addTitlePage(Document document) throws DocumentException {
        // Font Style for Document

        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 26, Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL);

        try {

            Paragraph prProfile = new Paragraph();

            prProfile.add("\n");
            prProfile.add("\n");

            prProfile.setFont(normal);

            int ImageIndex = 0;
            for (Widgets_Model widgets_model : widgetList) {

                //Condition for EditText..............................
                if (widgets_model.getEditText() != null) {
                    prProfile.add("\n");
                    prProfile.add(" " + widgets_model.getLabel() + ":");
                    prProfile.add(" " + widgets_model.getEditText().getText().toString());

                    prProfile.add("\n");

                }

                //Condition for CheckBox..............................
                else if (widgets_model.getCheckBox() != null) {

                    if (widgets_model.getCheckBox().isChecked()) {
                        prProfile.add("\n");
                        prProfile.add("\n");

                        try {
                            InputStream ims = getAssets().open("check.png");
                            Bitmap bmp = BitmapFactory.decodeStream(ims);
                            Bitmap result = Bitmap.createScaledBitmap(bmp, 20, 20, false);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            result.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image image = Image.getInstance(stream.toByteArray());
//                            prProfile.add(image);
//                            prProfile.add("\n");

                            PdfPTable table = new PdfPTable(2);
                            PdfPCell cellOne = new PdfPCell(new Phrase(widgets_model.getLabel()));
                            PdfPCell cellTwo = new PdfPCell(image);

                            cellOne.setBorder(Rectangle.NO_BORDER);
                            cellTwo.setBorder(Rectangle.NO_BORDER);

                            table.setWidthPercentage(35);
                            table.setHorizontalAlignment(Element.ALIGN_LEFT);
                            table.addCell(cellOne);
                            table.addCell(cellTwo);

                            prProfile.add(table);

                        } catch (IOException ex) {
                            return;
                        }


                    } else {
                        prProfile.add("\n");
                        prProfile.add("\n");

                        try {
                            InputStream ims = getAssets().open("check_box_empty.png");
                            Bitmap bmp = BitmapFactory.decodeStream(ims);
                            Bitmap result = Bitmap.createScaledBitmap(bmp, 20, 20, false);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            result.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            Image image = Image.getInstance(stream.toByteArray());
//                            prProfile.add(image);
//                            prProfile.add("\n");

                            PdfPTable table = new PdfPTable(2);
                            PdfPCell cellOne = new PdfPCell(new Phrase(widgets_model.getLabel()));
                            PdfPCell cellTwo = new PdfPCell(image);

                            cellOne.setBorder(Rectangle.NO_BORDER);
                            cellTwo.setBorder(Rectangle.NO_BORDER);

                            table.setWidthPercentage(35);
                            table.setHorizontalAlignment(Element.ALIGN_LEFT);
                            table.addCell(cellOne);
                            table.addCell(cellTwo);

                            prProfile.add(table);

                        } catch (IOException ex) {
                            return;
                        }
                    }
                }

                //Condition for TextView..............................
                else if (widgets_model.getTextView() != null) {
                    prProfile.add("\n");
                    prProfile.add("\n");
                    prProfile.add(" " + widgets_model.getLabel() + ":");
                    prProfile.add(" " + widgets_model.getTextView().getText().toString());
                    prProfile.add("\n");
                }

                //Condition for Camera..............................
                else if (widgets_model.getImageView() != null) {

                    for (CameraModel cameraModel : ImageList) {
                        if (widgets_model.getId().equals(cameraModel.getId())) {

                            try {

                                Log.e("AbsolutePath", cameraModel.getImage_file().getAbsolutePath());

                                // get input stream
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 20;

                                Bitmap bmp = BitmapFactory.decodeFile(cameraModel.getImage_file().getAbsolutePath(), options);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                Image image = Image.getInstance(stream.toByteArray());

                                prProfile.add("\n");
                                prProfile.add("\n");
                                prProfile.add(" " + widgets_model.getLabel() + ":");
                                prProfile.add("\n");
                                prProfile.add(image);
                                prProfile.add("\n");

                            } catch (IOException ex) {
                                return;
                            }
                        }
                    }

                }

                //Condition for Date..............................
                else if (widgets_model.getDatePicker() != null) {

                    for (DateModel dateModel : labelTextViewList) {

                        if (widgets_model.getId().equals(dateModel.getId())) {

                            prProfile.add("\n");
                            prProfile.add("\n");
                            prProfile.add(" " + widgets_model.getLabel() + ":");
                            prProfile.add(" " + dateModel.getDate());
                            prProfile.add("\n");

                        }
                    }

                }

                //Condition for Time..............................
                else if (widgets_model.getTimePicker() != null) {

                    for (DateModel dateModel : labelTextViewList) {

                        if (widgets_model.getId().equals(dateModel.getId())) {

                            prProfile.add("\n");
                            prProfile.add("\n");
                            prProfile.add(" " + widgets_model.getLabel() + ":");
                            prProfile.add(" " + dateModel.getTime());
                            prProfile.add("\n");

                        }
                    }

                }

                //Condition for Signature..............................
                else if (widgets_model.getSignature() != null) {

                    prProfile.add("\n");
                    prProfile.add("\n");
                    prProfile.add(" " + widgets_model.getLabel() + ":");
                    prProfile.add("\n");

                    try {

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 3;

                        Bitmap bmp = BitmapFactory.decodeFile(signatureImage.get(ImageIndex).getAbsolutePath(), options);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        Image image = Image.getInstance(stream.toByteArray());
                        image.setBorder(2);
                        image.setBorderWidthTop(2);
                        image.setBorderWidthBottom(2);
                        image.setBorderWidthLeft(2);
                        image.setBorderWidthRight(2);

                        prProfile.add(image);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ImageIndex++;
                }
            }

            document.add(prProfile);

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Create new Page in PDF

        document.newPage();
        document.close();

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void getCreatedWidgets() {

        int index = 0;

        Cursor cursor = databaseHelper.RetrieveData("Select * from inspect where Template_Id =" + getCheckin_TemplateId);
        while (cursor.moveToNext()) {

            Widget_Id = (cursor.getString(0));
            generate_type = cursor.getString(1);
            generate_label = cursor.getString(2);

            //Condition for EditText.....................
            if (generate_type.equals("EditText")) {

                hideKeyboard();

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 10, 10);
                LinearLayout linearLayout_parent = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params1.setMargins(20, 0, 20, 15);

                LinearLayout linearLayout_for_editText = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, params1, getResources().getDrawable(R.drawable.custom_edittext_widget));

                TextView textView = utils.CreateTextView(InspectDetails.this, generate_label, 25, 10, 0, 0);

                EditText editText = utils.CreateEdittext(InspectDetails.this, "", 15, 20, 10, 15, new ColorDrawable(Color.TRANSPARENT));

                Widgets_Model widgets_model = new Widgets_Model();
                widgets_model.setId(Widget_Id);
                widgets_model.setEditText(editText);
                widgets_model.setLabel(generate_label);

                widgetList.add(widgets_model);

                layout.addView(linearLayout_parent);
                linearLayout_parent.addView(textView);
                linearLayout_parent.addView(linearLayout_for_editText);
                linearLayout_for_editText.addView(editText);

            }

            //Condition for CheckBox.....................
            else if (generate_type.equals("CheckBox")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams child_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                child_params.setMargins(12, 0, 0, 0);

                LinearLayout childLayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.HORIZONTAL, child_params, null);

                CheckBox checkBox = utils.CreateCheckBox(InspectDetails.this, 0, 0, 0, 0, null);
                TextView textView_hint = utils.CreateTextView(InspectDetails.this, generate_label, 0, 10, 10, 15);

                Widgets_Model widgets_model = new Widgets_Model();
                widgets_model.setId(Widget_Id);
                widgets_model.setCheckBox(checkBox);
                widgets_model.setLabel(generate_label);

                widgetList.add(widgets_model);

                layout.addView(parentlayout);
                //parentlayout.addView(textView_heading);
                parentlayout.addView(childLayout);
                childLayout.addView(checkBox);
                childLayout.addView(textView_hint);

            }

            //Condition for TextView.....................
            else if (generate_type.equals("TextView")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                TextView textView_heading = utils.CreateTextView(InspectDetails.this, generate_label, 25, 15, 10, 15);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 0, 20, 15);

                Widgets_Model widgets_model = new Widgets_Model();
                widgets_model.setId(Widget_Id);
                widgets_model.setLabel(generate_label);
                widgets_model.setTextView(textView_heading);

                widgetList.add(widgets_model);

                parentlayout.addView(textView_heading);
                layout.addView(parentlayout);

            }

            //Condition for Section.....................
            else if (generate_type.equals("Section")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                TextView textView_heading = utils.CreateTextView(InspectDetails.this, generate_label, 25, 15, 10, 15);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 0, 20, 15);

                Widgets_Model widgets_model = new Widgets_Model();
                widgets_model.setId(Widget_Id);
                widgets_model.setLabel(generate_label);
                widgets_model.setSection(textView_heading);

                widgetList.add(widgets_model);

                parentlayout.addView(textView_heading);
                layout.addView(parentlayout);

            }

            //Condition for Camera.....................
            else if (generate_type.equals("Camera")) {

                index++;

                final TextView Label_textView = new TextView(this);//
                Label_textView.setText(generate_label);
                Label_textView.setPadding(8, 25, 0, 0);

                LinearLayout Vertical_Layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 20, 0, 15);
                Vertical_Layout.setLayoutParams(params);

                LinearLayout Button_layout = new LinearLayout(this);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(90, 90);
                params2.setMargins(0, 0, 0, 0);
                Button button = new Button(this);
                Drawable drawable = getResources().getDrawable(R.drawable.icon_add_attachments);
                button.setBackground(drawable);
                Button_layout.setLayoutParams(params2);
                Button_layout.addView(button);

                LinearLayout scrollView_Layout = new LinearLayout(this);

                HorizontalScrollView scrollView = new HorizontalScrollView(this);
                HorizontalScrollView.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
                layoutParams.setMargins(0, 0, 0, 0);
                scrollView.setLayoutParams(layoutParams);

                displayImages = new LinearLayout(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(150, 150);
                params1.setMargins(0, 0, 0, 0);
                displayImages.setLayoutParams(params1);

                if (index == 1)
                    displayImages.setId(R.id.layout1);
                if (index == 2)
                    displayImages.setId(R.id.layout2);
                if (index == 3)
                    displayImages.setId(R.id.layout3);
                if (index == 4)
                    displayImages.setId(R.id.layout4);
                if (index == 5)
                    displayImages.setId(R.id.layout5);
                if (index == 6)
                    displayImages.setId(R.id.layout6);
                if (index == 7)
                    displayImages.setId(R.id.layout7);
                if (index == 8)
                    displayImages.setId(R.id.layout8);
                if (index == 9)
                    displayImages.setId(R.id.layout9);


                scrollView_Layout.addView(scrollView);
                scrollView.addView(displayImages);

                final String finalIndex = String.valueOf(index);
                final String widgetId = Widget_Id;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("Clicked", finalIndex + "/" + widgetId);
                        String indexAndIdString = finalIndex + widgetId;
                        Log.e("Clicked", indexAndIdString);

                        int indexAndId = Integer.parseInt(indexAndIdString);

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        photoFile = getOutputMediaFile();
                        Uri fileProvider = FileProvider.getUriForFile(InspectDetails.this, getApplicationContext().getPackageName() + ".provider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            // Start the image capture intent to take photo
                            startActivityForResult(intent, indexAndId);
                        }
                    }
                });

                Widgets_Model id_widget_model = new Widgets_Model();
                id_widget_model.setId(Widget_Id);
                id_widget_model.setImageView(displayImages);
                id_widget_model.setLabel(generate_label);


                widgetList.add(id_widget_model);

                layout.addView(Label_textView);
                layout.addView(Vertical_Layout);
                Vertical_Layout.addView(Button_layout);
                Vertical_Layout.addView(scrollView_Layout);

            }

            //Condition for Signature.....................
            else if (generate_type.equals("Signature")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linear_params.setMargins(0, 20, 0, 10);
                LinearLayout horizontal_layout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.HORIZONTAL, linear_params, null);

                LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(60, 50);
                linear_params.setMargins(0, 20, 0, 10);
                LinearLayout signature_layout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, image_params, null);

                ImageView signature_icon = utils.CreateImageView(InspectDetails.this, 0, 10, 0, 0, getResources().getDrawable(R.drawable.signature));
                TextView signature_hint = utils.CreateTextView(InspectDetails.this, generate_label, 0, 5, 15, 0);

                LinearLayout.LayoutParams signature_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 230);
                signature_params.setMargins(20, 10, 20, 10);
                LinearLayout signatureLayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, signature_params, null);
                signatureLayout.setLayoutParams(signature_params);

                final SignaturePad pad = utils.CreateSignature(InspectDetails.this, 20, 25, 15, 0, getResources().getDrawable(R.drawable.custom_edittext_widget));

                LinearLayout button_Layout = new LinearLayout(this);
                button_Layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60);
                buttonParams.setMargins(30, 0, 30, 16);
                button_Layout.setLayoutParams(buttonParams);

                Button button = utils.CreateButton(InspectDetails.this, "Clear", 20, 0, 20, 0, getResources().getDrawable(R.drawable.custom_delete_button));
                button.setTextColor(getResources().getColor(R.color.white));
                button_Layout.addView(button);

                Widgets_Model id_widget_model = new Widgets_Model();
                id_widget_model.setId(Widget_Id);
                id_widget_model.setSignature(pad);
                id_widget_model.setLabel(generate_label);

                padList.add(new SignaturePadModel(id_widget_model.getId(), pad));

                Log.e("padListSize", padList.size() + "");

                widgetList.add(id_widget_model);
                Log.e("index", String.valueOf(index));

                layout.addView(parentlayout);
                parentlayout.addView(horizontal_layout);
                parentlayout.addView(signatureLayout);
                parentlayout.addView(button_Layout);
                signatureLayout.addView(pad);
                horizontal_layout.addView(signature_layout);
                signature_layout.addView(signature_icon);
                horizontal_layout.addView(signature_hint);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pad.clear();
                    }
                });

            }

            //Condition for Date.....................
            else if (generate_type.equals("Date")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT);
                linear_params.setMargins(25, 10, 0, 0);
                final LinearLayout horizontal_layout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.HORIZONTAL, linear_params, null);

                TextView textView_heading = utils.CreateTextView(InspectDetails.this, generate_label, 25, 15, 10, 15);

                ImageView date_icon = utils.CreateImageView(InspectDetails.this, 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_calendar));

                final Widgets_Model id_widget_model = new Widgets_Model();
                id_widget_model.setId(Widget_Id);
                id_widget_model.setDatePicker(date_picker);
                id_widget_model.setLabel(generate_label);

                widgetList.add(id_widget_model);

                final TextView date_textView = utils.CreateTextView(InspectDetails.this, "", 10, 5, 10, 15);

                DateModel dateModel = new DateModel();
                dateModel.setId(id_widget_model.getId());
                dateModel.setLabelTextView(date_textView);
                labelTextViewList.add(dateModel);

                parentlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("clickedDate", id_widget_model.getLabel());

                        for (DateModel dateModel1 : labelTextViewList) {
                            if (id_widget_model.getId().equals(dateModel1.getId())) {

                                latestLabel = new DateModel();
                                latestLabel.setId(dateModel1.getId());
                                latestLabel.setLabelTextView(dateModel1.getLabelTextView());
                            }
                        }
                        date_dialog.show();
                    }
                });

                getDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int day = date_picker.getDayOfMonth();
                        int month = date_picker.getMonth() + 1;
                        int year = date_picker.getYear();

                        date = String.valueOf(day + "-" + month + "-" + year);

                        for (int i = 0; i < labelTextViewList.size(); i++) {
                            DateModel dateModel1 = labelTextViewList.get(i);

                            if (latestLabel.getId().equals(dateModel1.getId())) {
                                labelTextViewList.get(i).setDate(date);
                            }
                        }

                        latestLabel.getLabelTextView().setText(date);
                        date_dialog.dismiss();
                    }
                });

                layout.addView(parentlayout);
                parentlayout.addView(textView_heading);
                parentlayout.addView(horizontal_layout);
                horizontal_layout.addView(date_icon);
                horizontal_layout.addView(date_textView);

            }

            //Condition for Time.....................
            else if (generate_type.equals("Time")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 20);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT);
                linear_params.setMargins(25, 10, 0, 5);
                LinearLayout horizontal_layout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.HORIZONTAL, linear_params, null);

                TextView textView_heading = utils.CreateTextView(InspectDetails.this, generate_label, 25, 15, 10, 15);
                ImageView time_icon = utils.CreateImageView(InspectDetails.this, 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_clock));


                final Widgets_Model id_widget_model = new Widgets_Model();
                id_widget_model.setId(Widget_Id);
                id_widget_model.setTimePicker(time_picker);
                id_widget_model.setLabel(generate_label);

                widgetList.add(id_widget_model);

                final TextView time_textView = utils.CreateTextView(InspectDetails.this, "", 10, 5, 10, 15);

                DateModel dateModel = new DateModel();
                dateModel.setId(id_widget_model.getId());
                dateModel.setLabelTextView(time_textView);
                labelTextViewList.add(dateModel);

                parentlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("clickedTime", id_widget_model.getLabel());

                        for (DateModel dateModel1 : labelTextViewList) {
                            if (id_widget_model.getId().equals(dateModel1.getId())) {

                                latestLabel = new DateModel();
                                latestLabel.setId(dateModel1.getId());
                                latestLabel.setLabelTextView(dateModel1.getLabelTextView());
                            }
                        }
                        time_dialog.show();
                    }
                });

                getTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        hour = time_picker.getCurrentHour();
                        minute = time_picker.getCurrentMinute();

                        get_hour = String.valueOf(hour);
                        get_min = String.valueOf(minute);

                        time = String.valueOf(get_hour + ":" + get_min);

                        for (int i = 0; i < labelTextViewList.size(); i++) {
                            DateModel dateModel1 = labelTextViewList.get(i);

                            if (latestLabel.getId().equals(dateModel1.getId())) {
                                labelTextViewList.get(i).setTime(time);
                            }
                        }
                        latestLabel.getLabelTextView().setText(time);
                        time_dialog.dismiss();
                    }
                });

                layout.addView(parentlayout);
                parentlayout.addView(textView_heading);
                parentlayout.addView(horizontal_layout);
                horizontal_layout.addView(time_icon);
                horizontal_layout.addView(time_textView);

            }

            generate_list.add(new AssetTemplatesWidgets(generate_type, generate_label));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void getCreatedWidgetsForTemplateAdapter() {

        int index = 0;

        Cursor cursor = databaseHelper.RetrieveData("Select * from inspect where Template_Id =" + InspectTemplateId);
        while (cursor.moveToNext()) {

            Widget_Id = (cursor.getString(0));
            generate_type = cursor.getString(1);
            generate_label = cursor.getString(2);

            //Condition for EditText.....................
            if (generate_type.equals("EditText")) {

                hideKeyboard();

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 10, 10);
                LinearLayout linearLayout_parent = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params1.setMargins(20, 0, 20, 15);

                LinearLayout linearLayout_for_editText = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, params1, getResources().getDrawable(R.drawable.custom_edittext_widget));

                TextView textView = utils.CreateTextView(InspectDetails.this, generate_label, 25, 10, 0, 0);

                EditText editText = utils.CreateEdittext(InspectDetails.this, "", 15, 20, 10, 15, new ColorDrawable(Color.TRANSPARENT));

                Widgets_Model widgets_model = new Widgets_Model();
                widgets_model.setId(Widget_Id);
                widgets_model.setEditText(editText);
                widgets_model.setLabel(generate_label);

                widgetList.add(widgets_model);

                layout.addView(linearLayout_parent);
                linearLayout_parent.addView(textView);
                linearLayout_parent.addView(linearLayout_for_editText);
                linearLayout_for_editText.addView(editText);

            }

            //Condition for CheckBox.....................
            else if (generate_type.equals("CheckBox")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams child_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                child_params.setMargins(12, 0, 0, 0);

                LinearLayout childLayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.HORIZONTAL, child_params, null);

                CheckBox checkBox = utils.CreateCheckBox(InspectDetails.this, 0, 0, 0, 0, null);
                TextView textView_hint = utils.CreateTextView(InspectDetails.this, generate_label, 0, 10, 10, 15);

                Widgets_Model widgets_model = new Widgets_Model();
                widgets_model.setId(Widget_Id);
                widgets_model.setCheckBox(checkBox);
                widgets_model.setLabel(generate_label);

                widgetList.add(widgets_model);

                layout.addView(parentlayout);
                //parentlayout.addView(textView_heading);
                parentlayout.addView(childLayout);
                childLayout.addView(checkBox);
                childLayout.addView(textView_hint);

            }

            //Condition for TextView.....................
            else if (generate_type.equals("TextView")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                TextView textView_heading = utils.CreateTextView(InspectDetails.this, generate_label, 25, 15, 10, 15);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 0, 20, 15);

                Widgets_Model widgets_model = new Widgets_Model();
                widgets_model.setId(Widget_Id);
                widgets_model.setLabel(generate_label);
                widgets_model.setTextView(textView_heading);

                widgetList.add(widgets_model);

                parentlayout.addView(textView_heading);
                layout.addView(parentlayout);

            }

            //Condition for Section.....................
            else if (generate_type.equals("Section")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                TextView textView_heading = utils.CreateTextView(InspectDetails.this, generate_label, 25, 15, 10, 15);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 0, 20, 15);

                Widgets_Model widgets_model = new Widgets_Model();
                widgets_model.setId(Widget_Id);
                widgets_model.setLabel(generate_label);
                widgets_model.setSection(textView_heading);

                widgetList.add(widgets_model);

                parentlayout.addView(textView_heading);
                layout.addView(parentlayout);

            }

            //Condition for Camera.....................
            else if (generate_type.equals("Camera")) {

                index++;

                final TextView Label_textView = new TextView(this);//
                Label_textView.setText(generate_label);
                Label_textView.setPadding(8, 25, 0, 0);

                LinearLayout Vertical_Layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 20, 0, 15);
                Vertical_Layout.setLayoutParams(params);

                LinearLayout Button_layout = new LinearLayout(this);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(90, 90);
                params2.setMargins(0, 0, 0, 0);
                Button button = new Button(this);
                Drawable drawable = getResources().getDrawable(R.drawable.icon_add_attachments);
                button.setBackground(drawable);
                Button_layout.setLayoutParams(params2);
                Button_layout.addView(button);

                LinearLayout scrollView_Layout = new LinearLayout(this);

                HorizontalScrollView scrollView = new HorizontalScrollView(this);
                HorizontalScrollView.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
                layoutParams.setMargins(0, 0, 0, 0);
                scrollView.setLayoutParams(layoutParams);

                displayImages = new LinearLayout(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(150, 150);
                params1.setMargins(0, 0, 0, 0);
                displayImages.setLayoutParams(params1);

                if (index == 1)
                    displayImages.setId(R.id.layout1);
                if (index == 2)
                    displayImages.setId(R.id.layout2);
                if (index == 3)
                    displayImages.setId(R.id.layout3);
                if (index == 4)
                    displayImages.setId(R.id.layout4);
                if (index == 5)
                    displayImages.setId(R.id.layout5);
                if (index == 6)
                    displayImages.setId(R.id.layout6);
                if (index == 7)
                    displayImages.setId(R.id.layout7);
                if (index == 8)
                    displayImages.setId(R.id.layout8);
                if (index == 9)
                    displayImages.setId(R.id.layout9);


                scrollView_Layout.addView(scrollView);
                scrollView.addView(displayImages);

                final String finalIndex = String.valueOf(index);
                final String widgetId = Widget_Id;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("Clicked", finalIndex + "/" + widgetId);
                        String indexAndIdString = finalIndex + widgetId;
                        Log.e("Clicked", indexAndIdString);

                        int indexAndId = Integer.parseInt(indexAndIdString);

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        photoFile = getOutputMediaFile();
                        Uri fileProvider = FileProvider.getUriForFile(InspectDetails.this, getApplicationContext().getPackageName() + ".provider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            // Start the image capture intent to take photo
                            startActivityForResult(intent, indexAndId);
                        }
                    }
                });

                Widgets_Model id_widget_model = new Widgets_Model();
                id_widget_model.setId(Widget_Id);
                id_widget_model.setImageView(displayImages);
                id_widget_model.setLabel(generate_label);


                widgetList.add(id_widget_model);

                layout.addView(Label_textView);
                layout.addView(Vertical_Layout);
                Vertical_Layout.addView(Button_layout);
                Vertical_Layout.addView(scrollView_Layout);

            }

            //Condition for Signature.....................
            else if (generate_type.equals("Signature")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linear_params.setMargins(0, 20, 0, 10);
                LinearLayout horizontal_layout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.HORIZONTAL, linear_params, null);

                LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(60, 50);
                linear_params.setMargins(0, 20, 0, 10);
                LinearLayout signature_layout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, image_params, null);

                ImageView signature_icon = utils.CreateImageView(InspectDetails.this, 0, 10, 0, 0, getResources().getDrawable(R.drawable.signature));
                TextView signature_hint = utils.CreateTextView(InspectDetails.this, generate_label, 0, 5, 15, 0);

                LinearLayout.LayoutParams signature_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 230);
                signature_params.setMargins(20, 10, 20, 10);
                LinearLayout signatureLayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, signature_params, null);
                signatureLayout.setLayoutParams(signature_params);

                final SignaturePad pad = utils.CreateSignature(InspectDetails.this, 20, 25, 15, 0, getResources().getDrawable(R.drawable.custom_edittext_widget));

                LinearLayout button_Layout = new LinearLayout(this);
                button_Layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60);
                buttonParams.setMargins(30, 0, 30, 16);
                button_Layout.setLayoutParams(buttonParams);

                Button button = utils.CreateButton(InspectDetails.this, "Clear", 20, 0, 20, 0, getResources().getDrawable(R.drawable.custom_delete_button));
                button.setTextColor(getResources().getColor(R.color.white));
                button_Layout.addView(button);

                Widgets_Model id_widget_model = new Widgets_Model();
                id_widget_model.setId(Widget_Id);
                id_widget_model.setSignature(pad);
                id_widget_model.setLabel(generate_label);

                padList.add(new SignaturePadModel(id_widget_model.getId(), pad));

                Log.e("padListSize", padList.size() + "");

                widgetList.add(id_widget_model);
                Log.e("index", String.valueOf(index));

                layout.addView(parentlayout);
                parentlayout.addView(horizontal_layout);
                parentlayout.addView(signatureLayout);
                parentlayout.addView(button_Layout);
                signatureLayout.addView(pad);
                horizontal_layout.addView(signature_layout);
                signature_layout.addView(signature_icon);
                horizontal_layout.addView(signature_hint);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pad.clear();
                    }
                });

            }

            //Condition for Date.....................
            else if (generate_type.equals("Date")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 15);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT);
                linear_params.setMargins(25, 10, 0, 0);
                final LinearLayout horizontal_layout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.HORIZONTAL, linear_params, null);

                TextView textView_heading = utils.CreateTextView(InspectDetails.this, generate_label, 25, 15, 10, 15);

                ImageView date_icon = utils.CreateImageView(InspectDetails.this, 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_calendar));

                final Widgets_Model id_widget_model = new Widgets_Model();
                id_widget_model.setId(Widget_Id);
                id_widget_model.setDatePicker(date_picker);
                id_widget_model.setLabel(generate_label);

                widgetList.add(id_widget_model);

                final TextView date_textView = utils.CreateTextView(InspectDetails.this, "", 10, 5, 10, 15);

                DateModel dateModel = new DateModel();
                dateModel.setId(id_widget_model.getId());
                dateModel.setLabelTextView(date_textView);
                labelTextViewList.add(dateModel);

                parentlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("clickedDate", id_widget_model.getLabel());

                        for (DateModel dateModel1 : labelTextViewList) {
                            if (id_widget_model.getId().equals(dateModel1.getId())) {

                                latestLabel = new DateModel();
                                latestLabel.setId(dateModel1.getId());
                                latestLabel.setLabelTextView(dateModel1.getLabelTextView());
                            }
                        }
                        date_dialog.show();
                    }
                });

                getDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int day = date_picker.getDayOfMonth();
                        int month = date_picker.getMonth() + 1;
                        int year = date_picker.getYear();

                        date = String.valueOf(day + "-" + month + "-" + year);

                        for (int i = 0; i < labelTextViewList.size(); i++) {
                            DateModel dateModel1 = labelTextViewList.get(i);

                            if (latestLabel.getId().equals(dateModel1.getId())) {
                                labelTextViewList.get(i).setDate(date);
                            }
                        }

                        latestLabel.getLabelTextView().setText(date);
                        date_dialog.dismiss();
                    }
                });

                layout.addView(parentlayout);
                parentlayout.addView(textView_heading);
                parentlayout.addView(horizontal_layout);
                horizontal_layout.addView(date_icon);
                horizontal_layout.addView(date_textView);

            }

            //Condition for Time.....................
            else if (generate_type.equals("Time")) {

                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_params.setMargins(0, 0, 10, 20);
                LinearLayout parentlayout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.VERTICAL, parent_params, getResources().getDrawable(R.drawable.custom_linear_background));

                LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT);
                linear_params.setMargins(25, 10, 0, 5);
                LinearLayout horizontal_layout = utils.CreateLinearLayout(InspectDetails.this, LinearLayout.HORIZONTAL, linear_params, null);

                TextView textView_heading = utils.CreateTextView(InspectDetails.this, generate_label, 25, 15, 10, 15);
                ImageView time_icon = utils.CreateImageView(InspectDetails.this, 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_clock));


                final Widgets_Model id_widget_model = new Widgets_Model();
                id_widget_model.setId(Widget_Id);
                id_widget_model.setTimePicker(time_picker);
                id_widget_model.setLabel(generate_label);

                widgetList.add(id_widget_model);

                final TextView time_textView = utils.CreateTextView(InspectDetails.this, "", 10, 5, 10, 15);

                DateModel dateModel = new DateModel();
                dateModel.setId(id_widget_model.getId());
                dateModel.setLabelTextView(time_textView);
                labelTextViewList.add(dateModel);

                parentlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("clickedTime", id_widget_model.getLabel());

                        for (DateModel dateModel1 : labelTextViewList) {
                            if (id_widget_model.getId().equals(dateModel1.getId())) {

                                latestLabel = new DateModel();
                                latestLabel.setId(dateModel1.getId());
                                latestLabel.setLabelTextView(dateModel1.getLabelTextView());
                            }
                        }
                        time_dialog.show();
                    }
                });

                getTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        hour = time_picker.getCurrentHour();
                        minute = time_picker.getCurrentMinute();

                        get_hour = String.valueOf(hour);
                        get_min = String.valueOf(minute);

                        time = String.valueOf(get_hour + ":" + get_min);

                        for (int i = 0; i < labelTextViewList.size(); i++) {
                            DateModel dateModel1 = labelTextViewList.get(i);

                            if (latestLabel.getId().equals(dateModel1.getId())) {
                                labelTextViewList.get(i).setTime(time);
                            }
                        }
                        latestLabel.getLabelTextView().setText(time);
                        time_dialog.dismiss();
                    }
                });

                layout.addView(parentlayout);
                parentlayout.addView(textView_heading);
                parentlayout.addView(horizontal_layout);
                horizontal_layout.addView(time_icon);
                horizontal_layout.addView(time_textView);

            }

            generate_list.add(new AssetTemplatesWidgets(generate_type, generate_label));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != PICK_IMAGE_REQUEST) {

            Log.e("OnResult", requestCode + "");
            int reqCode = Integer.parseInt(String.valueOf(requestCode).substring(0, 1));
            String id = String.valueOf(requestCode).substring(1);
            Log.e("OnResultSplit", reqCode + "/" + id);

            CameraModel cameraModel = new CameraModel();
            cameraModel.setId(id);
            cameraModel.setImage_file(photoFile);

            if (reqCode == 1) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout1);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == 2) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout2);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == 3) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout3);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == 4) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout4);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == 5) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout5);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == 6) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout6);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == 7) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout7);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == 8) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout8);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            } else if (reqCode == 9) {
                if (resultCode == RESULT_OK) {
                    ImageList.add(cameraModel);
                    getArrayList(photoFile, this, InspectDetails.this, R.id.layout9);

                } else { // Result was a failure
                    Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == PICK_IMAGE_REQUEST && data != null) {
            uri = data.getData();
            uriToFile = new File(uri.getPath());

            Log.e("UriToFile", uriToFile.getAbsolutePath());

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                Log.e("BitmapGallery", bitmap.toString());

                dis_Image.setVisibility(View.VISIBLE);
                dis_Image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Picture wasn't Selected!", Toast.LENGTH_SHORT).show();
        }

    }

    public void InsertDataIntoInspect() {

        for (Widgets_Model id_widget_model : widgetList) {

            //Condition for EditText..............................
            if (id_widget_model.getEditText() != null) {
                databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), id_widget_model.getEditText().getText().toString()));

//           Condition for CheckBox..............................
            } else if (id_widget_model.getCheckBox() != null) {
                if (id_widget_model.getCheckBox().isChecked()) {
                    databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), "Checked"));
                } else {
                    databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), "UnChecked"));
                }
            }

//          Condition for TextView..............................
            else if (id_widget_model.getTextView() != null) {
                databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), id_widget_model.getTextView().getText().toString()));
            }

//          Condition for Section..............................
            else if (id_widget_model.getSection() != null) {
                databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), id_widget_model.getSection().getText().toString()));
            }

//          Condition for Camera..............................
            else if (id_widget_model.getImageView() != null) {
                StringBuilder FinalImageString = new StringBuilder("");

                for (CameraModel cameraModel : ImageList) {
                    if (id_widget_model.getId().equals(cameraModel.getId())) {
                        FinalImageString.append(cameraModel.getImage_file().getAbsolutePath() + ",");
                    }
                }

                if (FinalImageString.length() > 1) {
                    String imagePath = FinalImageString.toString().substring(0, FinalImageString.length() - 1);
                    databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), imagePath));


                } else {
                    String imagePath = FinalImageString.toString().substring(0, FinalImageString.length());
                    databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), imagePath));

                }


//                Condition for Date..............................
            } else if (id_widget_model.getDatePicker() != null) {

                for (DateModel dateModel : labelTextViewList) {

                    if (id_widget_model.getId().equals(dateModel.getId())) {
                        databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), dateModel.getDate()));

                    }
                }


//                Condition for Time..............................
            } else if (id_widget_model.getTimePicker() != null) {

                for (DateModel dateModel : labelTextViewList) {

                    if (id_widget_model.getId().equals(dateModel.getId())) {
                        databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), dateModel.getTime()));

                    }
                }

//                Condition for Signature..............................
            } else if (id_widget_model.getSignature() != null) {
                //   InsertSignatureToDB(id_widget_model.getId());
                File photo = null;
                for (SignaturePadModel pad1 : padList) {

                    if (id_widget_model.getId().equals(pad1.getId())) {
                        Bitmap signatureBitmap = pad1.getSignaturePad().getSignatureBitmap();

                        Log.e("Signature", String.valueOf(signatureBitmap));

                        photo = addJpgSignatureToGallery(signatureBitmap);
                        signatureImage.add(photo);
                        databaseHelper.insertIntoInspectData(new TemplateData(getCheckin_TemplateId, getInspect_AssetId, id_widget_model.getId(), photo.toString()));

                    }
                }
            }
            Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    //Signature Methods...........................................
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public File addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        File photo = null;
        try {
            photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);

            Log.e("path", photo.toString());
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        InspectDetails.this.sendBroadcast(mediaScanIntent);
    }


    public void hideKeyboard() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Can not write Images to External Storage", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    void init() {

        doneInspectDetails = findViewById(R.id.doneinspectDetails);
        generalMethods = new GeneralMethods();

        dialog = new Dialog(this);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;


        get_header_footer_pdf = new Dialog(this);
        //    get_header_footer_pdf.setContentView(R.layout.get_header_footer_pdf);

        get_header_footer_pdf.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        get_header_footer_pdf.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

//        report_name = (EditText) dialog.findViewById(R.id.report_name);
//        continue_dialog = (Button) dialog.findViewById(R.id.continue_dialog);
//
//        header_name = get_header_footer_pdf.findViewById(R.id.header_name);
//
//        logo = get_header_footer_pdf.findViewById(R.id.logo);
//        dis_Image = get_header_footer_pdf.findViewById(R.id.dis_image);
//
//        generate_report = get_header_footer_pdf.findViewById(R.id.generate_report);

        alertDialog = new AlertDialog.Builder(this);


        databaseHelper = new DatabaseHelper(this);
        widgetList = new ArrayList<>();
        generate_list = new ArrayList<>();
        ImageList = new ArrayList<>();
        layout = (LinearLayout) findViewById(R.id.inspectMainLayout);

        padList = new ArrayList<>();
        //pad.setBackgroundColor(Color.WHITE);

        date_dialog = new Dialog(InspectDetails.this);
        date_dialog.setContentView(R.layout.click_date_dialog);

        getDate = date_dialog.findViewById(R.id.getDate);

        time_dialog = new Dialog(InspectDetails.this);
        time_dialog.setContentView(R.layout.click_time_dialog);

        getTime = time_dialog.findViewById(R.id.getTime);

        date_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        date_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        time_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        time_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        date_picker = date_dialog.findViewById(R.id.date_picker);
        time_picker = time_dialog.findViewById(R.id.timePicker);

        //  done_submit_form = (Button) findViewById(R.id.done_submit_form);
        //  back_btn_generate_widget = (Button) findViewById(R.id.back_btn_generate_widget);

        signatureImage = new ArrayList<>();
    }

    void getInspectInfo() {

        Cursor cursor = databaseHelper.RetrieveData("select * from inspect");
        while (cursor.moveToNext()) {

            String Widget_Id = cursor.getString(0);
            String Widget_Type = cursor.getString(1);
            String Widget_Label = cursor.getString(2);
            String Template_Id = cursor.getString(3);


            Log.e("Inspect Info", "Widget_Id: " + Widget_Id + " ,Template_Id: " + Template_Id + " ,Widget_Type: " + Widget_Type + " ,Widget_Label: " + Widget_Label);
        }
    }

    void getInspectData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from inspect_data");
        while (cursor.moveToNext()) {

            String Data_Id = cursor.getString(0);
            String Template_Id = cursor.getString(1);
            String Asset_Id = cursor.getString(2);
            String Widget_Id = cursor.getString(3);
            String Widget_Data = cursor.getString(4);


            Log.e("Inspect_Data", "Data_Id: " + Data_Id + " ,Template_Id: " + Template_Id + " ,Asset_Id: " + Asset_Id + " ,Widget_Id: " + Widget_Id + " ,Widget_Data: " + Widget_Data);
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(), TemplateManagement.class));
    }
}
