package com.example.muhammadzeeshan.aims_new.Utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;

/**
 * Created by Muhammad Zeeshan on 1/23/2018.
 */

public class utils {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static LinearLayout CreateLinearLayout(Context context, int orientation, LinearLayout.LayoutParams params, @Nullable Drawable background) {
        LinearLayout linearLayout_parent = new LinearLayout(context);
        linearLayout_parent.setOrientation(orientation);

        if (background != null) {
            linearLayout_parent.setBackground(background);
        }

        linearLayout_parent.setLayoutParams(params);

        return linearLayout_parent;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static EditText CreateEdittext(Context context,String hint, int left, int top, int right, int bottom, Drawable background){

        EditText editText = new EditText(context);
        editText.setHint(hint);
        editText.setPadding(left, top, right, bottom);

        if(background != null){
            editText.setBackground(background);
        }
        return editText;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static CheckBox CreateCheckBox(Context context,  int left, int top, int right, int bottom, Drawable background){

        CheckBox checkBox = new CheckBox(context);
        checkBox.setPadding(left, top, right, bottom);

        if(background != null){
            checkBox.setBackground(background);
        }
        return checkBox;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static TextView CreateTextView(Context context, String hint, int left, int top, int right, int bottom){

        TextView textView = new TextView(context);
        textView.setText(hint);
        textView.setPadding(left, top, right, bottom);

        return textView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static ImageView CreateImageView(Context context, int left, int top, int right, int bottom, Drawable background){

        ImageView imageView = new ImageView(context);
        imageView.setPadding(left, top, right, bottom);

        if(background != null){
            imageView.setImageDrawable(background);
        }
        return imageView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static SignaturePad CreateSignature(Context context, int left, int top, int right, int bottom, Drawable background){

        SignaturePad pad = new SignaturePad(context, null);
        pad.setPadding(left, top, right, bottom);

        if(background != null){
            pad.setBackground(background);
        }

        return pad;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static Button CreateButton(Context context,String hint, int left, int top, int right, int bottom, Drawable background){

        Button button = new Button(context);
        button.setPadding(left, top, right, bottom);
        button.setText(hint);

        if(background != null){
            button.setBackground(background);
        }
        return button;
    }

}
