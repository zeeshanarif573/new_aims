package com.example.muhammadzeeshan.aims_new;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Muhammad Zeeshan on 2/12/2018.
 */

public class GeneralMethods {

    static byte[] byteArray;
    public static ProgressDialog progress1;
    public static ProgressDialog creatingTemplate;
    public static ProgressDialog creatingAsset;
    public static ProgressDialog progress2;
    public static ProgressDialog progress3;


    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }

    public static void getArrayList(File photoFile, final Context context, Activity activity, int id) {

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams Image = new LinearLayout.LayoutParams(100, 90);
        Image.setMargins(10, 0, 0, 0);
        imageView.setLayoutParams(Image);

        LinearLayout linearLayout = activity.findViewById(id);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        final Bitmap takenImage = BitmapFactory.decodeFile(String.valueOf(photoFile), options);
        imageView.setImageBitmap(takenImage);

        Log.e("getImage", String.valueOf(takenImage));

        linearLayout.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                //    Intent intent = new Intent(context, ImageEditing.class);

                Bitmap bitmap = takenImage;

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();

                Log.e("Byte Array", String.valueOf(byteArray));
//
                //    intent.putExtra("ByteArray", byteArray);
                //   context.startActivity(intent);
            }
        });
    }

    public static void CreatingAssetLoader(View view, Context context) {
        creatingAsset = new ProgressDialog(context);
        creatingAsset.setTitle("Creating Asset");
        creatingAsset.setMessage("Please Wait...");
        creatingAsset.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        creatingAsset.show();
    }

    public static void CreatingTemplateLoader(View view, Context context) {
        creatingTemplate = new ProgressDialog(context);
        creatingTemplate.setTitle("Creating Template");
        creatingTemplate.setMessage("Please Wait...");
        creatingTemplate.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        creatingTemplate.show();
    }

    public static void SavingData(View view, Context context) {
        progress1 = new ProgressDialog(context);
        progress1.setTitle("Record is Saving");
        progress1.setMessage("Please Wait...");
        progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress1.show();
    }


    public static void Loader2(View view, Context context) {
        progress2 = new ProgressDialog(context);
        progress2.setTitle("Pdf is Generating");
        progress2.setMessage("Please Wait...");
        progress2.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress2.show();
    }

    public static void DeletingRecord(View view, Context context) {
        progress3 = new ProgressDialog(context);
        progress3.setTitle("Deleting Record");
        progress3.setMessage("Please Wait...");
        progress3.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progress3.show();
    }
}

