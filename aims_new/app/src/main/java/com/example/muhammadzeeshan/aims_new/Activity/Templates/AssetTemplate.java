package com.example.muhammadzeeshan.aims_new.Activity.Templates;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.CreateAsset;
import com.example.muhammadzeeshan.aims_new.Database.DatabaseHelper;
import com.example.muhammadzeeshan.aims_new.DisableSwipeBehavior;
import com.example.muhammadzeeshan.aims_new.Models.AssetTemplate.AssetTemplatesWidgets;
import com.example.muhammadzeeshan.aims_new.R;
import com.example.muhammadzeeshan.aims_new.Utility.utils;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

import static android.graphics.Typeface.BOLD;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.CreatingTemplateLoader;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.creatingTemplate;

public class AssetTemplate extends AppCompatActivity {

    Snackbar snackbar;
    FloatingActionButton fab_Open, fab_Close;
    ArrayList<AssetTemplatesWidgets> list;
    View snackView;
    AlertDialog.Builder alertDialog;
    String Template_Id;
    DatabaseHelper databaseHelper;
    Button doneAssettemplate;
    EditText label_editText, label_checkBox, label_textView, label_date, label_time, label_camera, label_signature, label_section;
    Dialog editText_dialog, checkbox_dialog, textView_dialog, section_dialog, date_dialog, time_dialog, signature_dialog, camera_dialog;
    Button done_label_editText, done_label_checkBox, done_label_textView, done_label_date, done_label_time, done_label_camera, done_label_signature, done_label_section;
    LinearLayout AssetTemplateWidgetsLayout, skip_asset;
    int index = 0;
    Typeface ubuntu_light_font, ubuntu_medium_font, source_sans_pro;
    CoordinatorLayout snackbar_Layout;
    AlertDialog dialog;
    String from = "";
    CircleButton editTextButton, textViewButton, sectionButton, checkBoxButton, signatureButton, cameraButton, dateButton, timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_template);

        initialization();
        dialog_transparency();

        getTemplateData();

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra("from")) {
                from = getIntent().getStringExtra("from");
            }
        }

        fab_Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fab_Close.setVisibility(View.VISIBLE);
                fab_Open.setVisibility(View.GONE);

                snackbar = Snackbar.make(snackbar_Layout, "", Snackbar.LENGTH_INDEFINITE);
                final Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

                snackView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_snackbar, null);
                initButtons();

                snackView.setBackgroundColor(Color.WHITE);
                layout.setMinimumHeight(30);
                layout.setBackgroundColor(Color.WHITE);

                layout.addView(snackView, 0);
                snackbar.show();

                //Edit Text Functionality Implemented..............
                editTextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText_dialog.show();

                        done_label_editText.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View view) {

                                if (label_editText.getText().toString().isEmpty()) {

                                    Toast.makeText(AssetTemplate.this, "Please Provide Label Before Adding Widget", Toast.LENGTH_LONG).show();
                                    editText_dialog.dismiss();

                                } else {

                                    editText_dialog.dismiss();

                                    hideKeyboard();

                                    final AssetTemplatesWidgets widgetsData = new AssetTemplatesWidgets("EditText", label_editText.getText().toString());
                                    list.add(widgetsData);

                                    //Horizontal Layout...........................
                                    LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    final LinearLayout parent_horizontal_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, horizontal_params, getResources().getDrawable(R.drawable.custom_linear_background));
                                    horizontal_params.setMargins(0, 0, 10, 10);

                                    //Vertical Layout Contain Textview and Edittext......................
                                    LinearLayout.LayoutParams vertical_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25);
                                    LinearLayout vertical_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, vertical_params, null);

                                    //Textview Layout...........................
                                    TextView textView = utils.CreateTextView(AssetTemplate.this, label_editText.getText().toString(), 25, 10, 0, 0);

                                    //EditText Layout...........................
                                    LinearLayout.LayoutParams editText_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    editText_params.setMargins(20, 0, 20, 20);
                                    LinearLayout linearLayout_for_editText = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, editText_params, getResources().getDrawable(R.drawable.custom_edittext_widget));
                                    EditText myEdittext = utils.CreateEdittext(AssetTemplate.this, "", 15, 20, 10, 15, new ColorDrawable(Color.TRANSPARENT));

                                    //Delete Button Layout...........................
                                    LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40, (float) 3);
                                    button_params.setMargins(0, 55, 12, 5);
                                    LinearLayout linearLayout_for_button = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, button_params, null);
                                    Button delete_Button = utils.CreateButton(AssetTemplate.this, "", 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_cancel));

                                    //Button Functionality............................
                                    delete_Button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Log.e("TypeClicked", widgetsData.getWidget_type());
                                            AssetTemplateWidgetsLayout.removeView(parent_horizontal_Layout);
                                            list.remove(widgetsData);
                                        }
                                    });

                                    vertical_Layout.addView(textView);

                                    linearLayout_for_editText.addView(myEdittext);
                                    vertical_Layout.addView(linearLayout_for_editText);

                                    parent_horizontal_Layout.addView(vertical_Layout);

                                    linearLayout_for_button.addView(delete_Button);
                                    parent_horizontal_Layout.addView(linearLayout_for_button);

                                    AssetTemplateWidgetsLayout.addView(parent_horizontal_Layout);

                                    label_editText.setText("");
                                }
                            }
                        });
                    }
                });

                //Check Box Functionality Implemented..............
                checkBoxButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        checkbox_dialog.show();

                        done_label_checkBox.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View view) {

                                if (label_checkBox.getText().toString().isEmpty()) {

                                    Toast.makeText(AssetTemplate.this, "Please Provide Label Before Adding Widget", Toast.LENGTH_LONG).show();
                                    checkbox_dialog.dismiss();

                                } else {

                                    checkbox_dialog.dismiss();

                                    final AssetTemplatesWidgets widgetsData = new AssetTemplatesWidgets("CheckBox", label_checkBox.getText().toString());
                                    list.add(widgetsData);

                                    //Horizontal Layout...........................
                                    LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    final LinearLayout parent_horizontal_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, horizontal_params, getResources().getDrawable(R.drawable.custom_linear_background));
                                    horizontal_params.setMargins(0, 0, 10, 10);


                                    LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25);
                                    parent_params.setMargins(0, 0, 10, 15);
                                    LinearLayout vertical_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, parent_params, null);

                                    LinearLayout.LayoutParams child_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    child_params.setMargins(12, 10, 0, 0);

                                    LinearLayout childLayout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, child_params, null);

                                    CheckBox checkBox = utils.CreateCheckBox(AssetTemplate.this, 0, 0, 0, 0, null);
                                    TextView textView_hint = utils.CreateTextView(AssetTemplate.this, label_checkBox.getText().toString(), 0, 0, 10, 15);

                                    //Delete Button Layout...........................
                                    LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40, (float) 3);
                                    button_params.setMargins(0, 23, 12, 5);
                                    LinearLayout linearLayout_for_button = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, button_params, null);
                                    Button delete_Button = utils.CreateButton(AssetTemplate.this, "", 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_cancel));

                                    //Button Functionality............................
                                    delete_Button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AssetTemplateWidgetsLayout.removeView(parent_horizontal_Layout);
                                            list.remove(widgetsData);
                                        }
                                    });


                                    vertical_Layout.addView(childLayout);
                                    childLayout.addView(checkBox);
                                    childLayout.addView(textView_hint);

                                    parent_horizontal_Layout.addView(vertical_Layout);
                                    parent_horizontal_Layout.addView(linearLayout_for_button);
                                    linearLayout_for_button.addView(delete_Button);

                                    AssetTemplateWidgetsLayout.addView(parent_horizontal_Layout);

                                    label_checkBox.setText("");
                                }
                            }
                        });

                    }
                });

                //TextView Functionality Implemented...............
                textViewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        textView_dialog.show();

                        done_label_textView.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View view) {

                                if (label_textView.getText().toString().isEmpty()) {

                                    Toast.makeText(AssetTemplate.this, "Please Provide Label Before Adding Widget", Toast.LENGTH_LONG).show();
                                    textView_dialog.dismiss();

                                } else {
                                    textView_dialog.dismiss();

                                    final AssetTemplatesWidgets widgetsData = new AssetTemplatesWidgets("TextView", label_textView.getText().toString());
                                    list.add(widgetsData);

                                    //Horizontal Layout...........................
                                    LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    final LinearLayout parent_horizontal_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, horizontal_params, getResources().getDrawable(R.drawable.custom_linear_background));
                                    horizontal_params.setMargins(0, 0, 10, 10);

                                    //Vertical Layout................................
                                    LinearLayout.LayoutParams vertical_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25);
                                    vertical_params.setMargins(20, 0, 10, 15);
                                    LinearLayout vertical_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, vertical_params, null);

                                    TextView textView_heading = utils.CreateTextView(AssetTemplate.this, label_textView.getText().toString(), 3, 15, 10, 15);

                                    //Delete Button Layout...........................
                                    LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40, (float) 3);
                                    button_params.setMargins(0, 25, 12, 5);
                                    LinearLayout linearLayout_for_button = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, button_params, null);
                                    Button delete_Button = utils.CreateButton(AssetTemplate.this, "", 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_cancel));

                                    //Button Functionality............................
                                    delete_Button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AssetTemplateWidgetsLayout.removeView(parent_horizontal_Layout);
                                            list.remove(widgetsData);
                                        }
                                    });

                                    vertical_Layout.addView(textView_heading);

                                    parent_horizontal_Layout.addView(vertical_Layout);

                                    linearLayout_for_button.addView(delete_Button);
                                    parent_horizontal_Layout.addView(linearLayout_for_button);

                                    AssetTemplateWidgetsLayout.addView(parent_horizontal_Layout);

                                    label_textView.setText("");
                                }
                            }
                        });
                    }
                });

                //Section Functionality Implemented...............
                sectionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        section_dialog.show();

                        done_label_section.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View view) {

                                if (label_section.getText().toString().isEmpty()) {

                                    Toast.makeText(AssetTemplate.this, "Please Provide Label Before Adding Widget", Toast.LENGTH_LONG).show();
                                    section_dialog.dismiss();

                                } else {
                                    section_dialog.dismiss();

                                    final AssetTemplatesWidgets widgetsData = new AssetTemplatesWidgets("Section", label_textView.getText().toString());
                                    list.add(widgetsData);

                                    //Horizontal Layout...........................
                                    LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    final LinearLayout parent_horizontal_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, horizontal_params, getResources().getDrawable(R.drawable.custom_linear_background));
                                    horizontal_params.setMargins(0, 0, 10, 10);

                                    //Vertical Layout................................
                                    LinearLayout.LayoutParams vertical_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25);
                                    vertical_params.setMargins(20, 0, 10, 15);
                                    LinearLayout vertical_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, vertical_params, null);

                                    TextView textView_heading = utils.CreateTextView(AssetTemplate.this, label_section.getText().toString(), 3, 15, 10, 15);
                                    textView_heading.setTextSize(18);

                                    font();
                                    textView_heading.setTypeface(ubuntu_medium_font, BOLD);

                                    //Delete Button Layout...........................
                                    LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40, (float) 3);
                                    button_params.setMargins(0, 25, 12, 5);
                                    LinearLayout linearLayout_for_button = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, button_params, null);
                                    Button delete_Button = utils.CreateButton(AssetTemplate.this, "", 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_cancel));

                                    //Button Functionality............................
                                    delete_Button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AssetTemplateWidgetsLayout.removeView(parent_horizontal_Layout);
                                            list.remove(widgetsData);
                                        }
                                    });

                                    vertical_Layout.addView(textView_heading);

                                    parent_horizontal_Layout.addView(vertical_Layout);

                                    linearLayout_for_button.addView(delete_Button);
                                    parent_horizontal_Layout.addView(linearLayout_for_button);

                                    AssetTemplateWidgetsLayout.addView(parent_horizontal_Layout);

                                    label_section.setText("");
                                }
                            }
                        });
                    }
                });

                //Date Functionality Implemented...............
                dateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        date_dialog.show();

                        done_label_date.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View view) {

                                if (label_date.getText().toString().isEmpty()) {

                                    Toast.makeText(AssetTemplate.this, "Please Provide Label Before Adding Widget", Toast.LENGTH_LONG).show();
                                    date_dialog.dismiss();

                                }
                                date_dialog.dismiss();

                                final AssetTemplatesWidgets widgetsData = new AssetTemplatesWidgets("Date", label_date.getText().toString());
                                list.add(widgetsData);

                                //Horizontal Layout...........................
                                LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                final LinearLayout parent_horizontal_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, horizontal_params, getResources().getDrawable(R.drawable.custom_linear_background));
                                horizontal_params.setMargins(0, 0, 10, 10);

                                //Vertical Layout...........................
                                LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25);
                                parent_params.setMargins(0, 0, 10, 15);
                                LinearLayout vertical_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, parent_params, null);

                                LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT);
                                linear_params.setMargins(25, 0, 0, 0);
                                LinearLayout horizontal_layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, linear_params, null);

                                TextView textView_heading = utils.CreateTextView(AssetTemplate.this, label_date.getText().toString(), 25, 15, 10, 15);
                                ImageView date_icon = utils.CreateImageView(AssetTemplate.this, 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_calendar));


                                //Delete Button Layout...........................
                                LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40, (float) 3);
                                button_params.setMargins(0, 55, 12, 5);
                                LinearLayout linearLayout_for_button = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, button_params, null);
                                Button delete_Button = utils.CreateButton(AssetTemplate.this, "", 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_cancel));

                                //Button Functionality............................
                                delete_Button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AssetTemplateWidgetsLayout.removeView(parent_horizontal_Layout);
                                        list.remove(widgetsData);
                                    }
                                });

                                vertical_Layout.addView(textView_heading);
                                vertical_Layout.addView(horizontal_layout);

                                horizontal_layout.addView(date_icon);
                                //       horizontal_layout.addView(date);

                                parent_horizontal_Layout.addView(vertical_Layout);

                                linearLayout_for_button.addView(delete_Button);
                                parent_horizontal_Layout.addView(linearLayout_for_button);

                                AssetTemplateWidgetsLayout.addView(parent_horizontal_Layout);

                                label_date.setText("");

                            }
                        });
                    }
                });

                //Time Functionality Implemented...............
                timeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        time_dialog.show();

                        done_label_time.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View view) {

                                if (label_time.getText().toString().isEmpty()) {

                                    Toast.makeText(AssetTemplate.this, "Please Provide Label Before Adding Widget", Toast.LENGTH_LONG).show();
                                    time_dialog.dismiss();

                                } else {

                                    time_dialog.dismiss();

                                    final AssetTemplatesWidgets widgetsData = new AssetTemplatesWidgets("Time", label_time.getText().toString());
                                    list.add(widgetsData);

                                    //Horizontal Layout...........................
                                    LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    final LinearLayout parent_horizontal_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, horizontal_params, getResources().getDrawable(R.drawable.custom_linear_background));
                                    horizontal_params.setMargins(0, 0, 10, 10);

                                    //Vertical Layout............................
                                    LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25);
                                    parent_params.setMargins(0, 0, 10, 0);
                                    LinearLayout vertical_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, parent_params, null);

                                    LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT);
                                    linear_params.setMargins(25, 0, 0, 10);
                                    LinearLayout horizontal_layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, linear_params, null);

                                    TextView textView_heading = utils.CreateTextView(AssetTemplate.this, label_time.getText().toString(), 25, 15, 10, 15);
                                    ImageView time_icon = utils.CreateImageView(AssetTemplate.this, 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_clock));


                                    //Delete Button Layout...........................
                                    LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40, (float) 3);
                                    button_params.setMargins(0, 50, 12, 5);
                                    LinearLayout linearLayout_for_button = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, button_params, null);
                                    Button delete_Button = utils.CreateButton(AssetTemplate.this, "", 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_cancel));

                                    //Button Functionality............................
                                    delete_Button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AssetTemplateWidgetsLayout.removeView(parent_horizontal_Layout);
                                            list.remove(widgetsData);
                                        }
                                    });

                                    parent_horizontal_Layout.addView(vertical_Layout);

                                    vertical_Layout.addView(textView_heading);
                                    vertical_Layout.addView(horizontal_layout);

                                    horizontal_layout.addView(time_icon);

                                    linearLayout_for_button.addView(delete_Button);
                                    parent_horizontal_Layout.addView(linearLayout_for_button);

                                    AssetTemplateWidgetsLayout.addView(parent_horizontal_Layout);

                                    label_time.setText("");
                                }
                            }
                        });
                    }
                });

                //Camera Functionality Implemented...............
                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        index++;
                        Log.e("Index", index + "");

                        if (index < 10) {

                            camera_dialog.show();

                            done_label_camera.setOnClickListener(new View.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onClick(View view) {

                                    if (label_camera.getText().toString().isEmpty()) {

                                        Toast.makeText(AssetTemplate.this, "Please Provide Label Before Adding Widget", Toast.LENGTH_LONG).show();
                                        camera_dialog.dismiss();

                                    } else {

                                        camera_dialog.dismiss();

                                        final AssetTemplatesWidgets widgetsData = new AssetTemplatesWidgets("Camera", label_camera.getText().toString());
                                        list.add(widgetsData);

                                        //Horizontal Layout...........................
                                        LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        final LinearLayout parent_horizontal_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, horizontal_params, getResources().getDrawable(R.drawable.custom_linear_background));
                                        horizontal_params.setMargins(0, 0, 10, 10);

                                        //Vertical Layout...........................
                                        LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25);
                                        parent_params.setMargins(0, 0, 10, 15);
                                        LinearLayout vertical_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, parent_params, null);

                                        TextView textView_heading = utils.CreateTextView(AssetTemplate.this, label_camera.getText().toString(), 25, 15, 10, 15);

                                        //Horizontal Layout with button and Label...........................
                                        LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        linear_params.setMargins(25, 10, 0, 10);
                                        LinearLayout horizontal_layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, linear_params, null);

                                        LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(70, 80);
                                        linear_params.setMargins(25, 10, 0, 10);
                                        LinearLayout image_layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, image_params, null);

                                        ImageView camera_icon = utils.CreateImageView(AssetTemplate.this, 0, 0, 0, 0, getResources().getDrawable(R.drawable.icon_add_attachments));

                                        //Delete Button Layout...........................
                                        LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40, (float) 3);
                                        button_params.setMargins(0, 70, 13, 10);
                                        LinearLayout linearLayout_for_button = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, button_params, null);
                                        Button delete_Button = utils.CreateButton(AssetTemplate.this, "", 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_cancel));

                                        //Button Functionality............................
                                        delete_Button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                AssetTemplateWidgetsLayout.removeView(parent_horizontal_Layout);
                                                list.remove(widgetsData);
                                            }
                                        });


                                        vertical_Layout.addView(textView_heading);
                                        vertical_Layout.addView(horizontal_layout);

                                        parent_horizontal_Layout.addView(vertical_Layout);

                                        horizontal_layout.addView(image_layout);
                                        image_layout.addView(camera_icon);
//                                        horizontal_layout.addView(camera);

                                        linearLayout_for_button.addView(delete_Button);
                                        parent_horizontal_Layout.addView(linearLayout_for_button);

                                        AssetTemplateWidgetsLayout.addView(parent_horizontal_Layout);

                                        label_camera.setText("");
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(AssetTemplate.this, "You can not add more than 9 Camera Widget", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Signature Functionality Implemented...............
                signatureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        signature_dialog.show();

                        done_label_signature.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View view) {

                                if (label_signature.getText().toString().isEmpty()) {

                                    Toast.makeText(AssetTemplate.this, "Please Provide Label Before Adding Widget", Toast.LENGTH_LONG).show();
                                    signature_dialog.dismiss();

                                } else {

                                    signature_dialog.dismiss();

                                    final AssetTemplatesWidgets widgetsData = new AssetTemplatesWidgets("Signature", label_signature.getText().toString());
                                    list.add(widgetsData);

                                    //Horizontal Layout...........................
                                    LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    final LinearLayout parent_horizontal_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, horizontal_params, getResources().getDrawable(R.drawable.custom_linear_background));
                                    horizontal_params.setMargins(0, 0, 10, 10);

                                    //Vertical Layout...........................
                                    LinearLayout.LayoutParams parent_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.25);
                                    parent_params.setMargins(0, 0, 10, 15);
                                    LinearLayout vertical_Layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, parent_params, null);

                                    TextView textView_heading = utils.CreateTextView(AssetTemplate.this, label_signature.getText().toString(), 25, 15, 10, 15);

                                    LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    linear_params.setMargins(25, 10, 0, 10);
                                    LinearLayout horizontal_layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.HORIZONTAL, linear_params, null);

                                    LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(70, 80);
                                    linear_params.setMargins(25, 10, 0, 10);
                                    LinearLayout signature_layout = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, image_params, null);

                                    ImageView signature_icon = utils.CreateImageView(AssetTemplate.this, 0, 0, 0, 0, getResources().getDrawable(R.drawable.signature));


                                    //Delete Button Layout...........................
                                    LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40, (float) 3);
                                    button_params.setMargins(0, 70, 13, 10);
                                    LinearLayout linearLayout_for_button = utils.CreateLinearLayout(AssetTemplate.this, LinearLayout.VERTICAL, button_params, null);
                                    Button delete_Button = utils.CreateButton(AssetTemplate.this, "", 0, 0, 0, 0, getResources().getDrawable(R.drawable.ic_action_cancel));

                                    //Button Functionality............................
                                    delete_Button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            AssetTemplateWidgetsLayout.removeView(parent_horizontal_Layout);
                                            list.remove(widgetsData);
                                        }
                                    });


                                    vertical_Layout.addView(textView_heading);
                                    vertical_Layout.addView(horizontal_layout);

                                    parent_horizontal_Layout.addView(vertical_Layout);

                                    horizontal_layout.addView(signature_layout);
                                    signature_layout.addView(signature_icon);

                                    linearLayout_for_button.addView(delete_Button);
                                    parent_horizontal_Layout.addView(linearLayout_for_button);

                                    AssetTemplateWidgetsLayout.addView(parent_horizontal_Layout);

                                    label_signature.setText("");
                                }
                            }
                        });

                    }
                });


                //Snackbar Removing on Swiping
                layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ViewGroup.LayoutParams lp = layout.getLayoutParams();
                        if (lp instanceof CoordinatorLayout.LayoutParams) {
                            ((CoordinatorLayout.LayoutParams) lp).setBehavior(new DisableSwipeBehavior());
                            layout.setLayoutParams(lp);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            //noinspection deprecation
                            layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                    }
                });
            }
        });

        fab_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_Open.setVisibility(View.VISIBLE);
                fab_Close.setVisibility(View.GONE);
                snackbar.dismiss();
            }
        });

        skip_asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you sure you want to skip Asset Template?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                        finish();

                        Intent intent = new Intent(AssetTemplate.this, CheckOutTemplate.class);
                        intent.putExtra("from", "AssetTemplate");
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

        doneAssettemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AssetTemplateWidgetsLayout.getChildCount() == 0) {

                    Snackbar.make(AssetTemplateWidgetsLayout, "Please Add Widgets first..", Snackbar.LENGTH_SHORT).show();
                }
                else {

                    if (from.equalsIgnoreCase("CreateAsset")) {

                        CreatingTemplateLoader(view, AssetTemplate.this);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                creatingTemplate.dismiss();
                                for (int i = 0; i < list.size(); i++) {

                                    Log.e("ListSize", String.valueOf(list.size()));
                                    databaseHelper.insertDataIntoAssetTemplate(new AssetTemplatesWidgets(Template_Id, list.get(i).getWidget_type(), list.get(i).getWidget_label()));

                                    getAssetTemplateData();
                                }

                                Toast.makeText(AssetTemplate.this, "AssetTemplate is Created Successfully", Toast.LENGTH_SHORT).show();

                                finish();
                                Intent intent = new Intent(AssetTemplate.this, CreateAsset.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            }
                        }, 2000);

                    } else {

                        CreatingTemplateLoader(view, AssetTemplate.this);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                creatingTemplate.dismiss();
                                AlertDialog dialog = alertDialog.create();
                                dialog.show();

                                for (int i = 0; i < list.size(); i++) {

                                    Log.e("ListSize", String.valueOf(list.size()));
                                    databaseHelper.insertDataIntoAssetTemplate(new AssetTemplatesWidgets(Template_Id, list.get(i).getWidget_type(), list.get(i).getWidget_label()));

                                    getAssetTemplateData();
                                }

                                Toast.makeText(AssetTemplate.this, "AssetTemplate is Created Successfully", Toast.LENGTH_SHORT).show();

                            }
                        }, 2000);

                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Want to create checkout, checkin and inspect?");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(AssetTemplate.this, CheckOutTemplate.class);
                                intent.putExtra("from", "AssetTemplate");
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        });

                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(AssetTemplate.this, CreateAsset.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        });

                    }
                }

            }
        });
    }

    void initialization() {

        skip_asset = findViewById(R.id.skip_asset);

        list = new ArrayList<>();

        databaseHelper = new DatabaseHelper(this);
        //widgetList = new ArrayList<>();

        editText_dialog = new Dialog(this);
        editText_dialog.setContentView(R.layout.edittext_label_dialog);

        checkbox_dialog = new Dialog(this);
        checkbox_dialog.setContentView(R.layout.checkbox_label_dialog);

        textView_dialog = new Dialog(this);
        textView_dialog.setContentView(R.layout.textview_label_dialog);

        section_dialog = new Dialog(this);
        section_dialog.setContentView(R.layout.section_label_dialog);

        date_dialog = new Dialog(this);
        date_dialog.setContentView(R.layout.date_label_dialog);

        time_dialog = new Dialog(this);
        time_dialog.setContentView(R.layout.time_label_dialog);

        camera_dialog = new Dialog(this);
        camera_dialog.setContentView(R.layout.camera_label_dialog);

        signature_dialog = new Dialog(this);
        signature_dialog.setContentView(R.layout.signature_label_dialog);


        snackbar_Layout = findViewById(R.id.asset_snackbar_Layout);
        AssetTemplateWidgetsLayout = (LinearLayout) findViewById(R.id.AssetTemplateWidgetsLayout);

        label_editText = editText_dialog.findViewById(R.id.label_edittext);
        done_label_editText = editText_dialog.findViewById(R.id.done_editText_label);

        label_checkBox = checkbox_dialog.findViewById(R.id.label_checkbox);
        done_label_checkBox = checkbox_dialog.findViewById(R.id.done_checkbox_labels);

        label_textView = textView_dialog.findViewById(R.id.label_textview);
        done_label_textView = textView_dialog.findViewById(R.id.done_textview_labels);

        label_section = section_dialog.findViewById(R.id.label_section);
        done_label_section = section_dialog.findViewById(R.id.done_section_label);

        label_date = date_dialog.findViewById(R.id.label_date);
        done_label_date = date_dialog.findViewById(R.id.done_date_labels);

        label_time = time_dialog.findViewById(R.id.label_time);
        done_label_time = time_dialog.findViewById(R.id.done_time_labels);

        label_camera = camera_dialog.findViewById(R.id.label_image);
        done_label_camera = camera_dialog.findViewById(R.id.done_image_labels);

        label_signature = signature_dialog.findViewById(R.id.label_signature);
        done_label_signature = signature_dialog.findViewById(R.id.done_signature_labels);

        fab_Open = (FloatingActionButton) findViewById(R.id.fab_Open_asset);
        fab_Close = (FloatingActionButton) findViewById(R.id.fab_Close_asset);

        doneAssettemplate = findViewById(R.id.doneAssettemplate);
        alertDialog = new AlertDialog.Builder(this);
    }

    void dialog_transparency() {
        editText_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editText_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        checkbox_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        checkbox_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        textView_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textView_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        section_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        section_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;


        date_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        date_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        time_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        time_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        camera_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        camera_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

        signature_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        signature_dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.FILL_PARENT;

    }

    public void initButtons() {
        editTextButton = (CircleButton) snackView.findViewById(R.id.circle_btn1);
        checkBoxButton = (CircleButton) snackView.findViewById(R.id.circle_btn2);
        textViewButton = (CircleButton) snackView.findViewById(R.id.circle_btn3);
        sectionButton = (CircleButton) snackView.findViewById(R.id.circle_btn4);
        cameraButton = (CircleButton) snackView.findViewById(R.id.circle_btn5);
        signatureButton = (CircleButton) snackView.findViewById(R.id.circle_btn6);
        dateButton = (CircleButton) snackView.findViewById(R.id.circle_btn7);
        timeButton = (CircleButton) snackView.findViewById(R.id.circle_btn8);

    }

    public void hideKeyboard() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    void getTemplateData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from Template");
        while (cursor.moveToNext()) {

            Template_Id = cursor.getString(0);
        }
    }

    void getAssetTemplateData() {

        Cursor cursor = databaseHelper.RetrieveData("select * from asset_template");
        while (cursor.moveToNext()) {

            String AssetTemplate_Id = cursor.getString(0);
            String Widget_Type = cursor.getString(1);
            String Widget_Label = cursor.getString(2);
            String Template_Id = cursor.getString(3);

            Log.e("AssetTemplate_Data", "Widget_Id: " + AssetTemplate_Id + " ,Template_Id: " + Template_Id + " ,Widget_Type: " + Widget_Type + " ,Widget_Label: " + Widget_Label);
        }
    }

    @Override
    public void onBackPressed() {
    }

    void font() {
        ubuntu_light_font = Typeface.createFromAsset(AssetTemplate.this.getAssets(), "font/ubuntu_light.ttf");
        ubuntu_medium_font = Typeface.createFromAsset(AssetTemplate.this.getAssets(), "font/Ubuntu-Medium.ttf");
        source_sans_pro = Typeface.createFromAsset(AssetTemplate.this.getAssets(), "font/SourceSansPro-Light.ttf");
    }
}
