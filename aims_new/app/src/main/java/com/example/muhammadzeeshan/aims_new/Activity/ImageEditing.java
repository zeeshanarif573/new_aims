package com.example.muhammadzeeshan.aims_new.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ImageEditing extends AppCompatActivity {

    LinearLayout linearLayout_undo, linearLayout_delete, linearLayout_save;
    private ImageView btn_back;
    LinearLayout linearLayout_img;
    Paint paint;
    View view_canvas;
    Path canvas_path;
    Bitmap bitmap;
    Canvas canvas;
    ByteArrayOutputStream bytearrayoutputstream;
    File file;
    FileOutputStream fileoutputstream;
    String gettingImageUrl;
    String getting_img_no;


    private File folder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editing);
        init();
        showCaptureimages();

    }

    private void init() {

        folder = new File(Environment.getExternalStorageDirectory() + "/iAuditor Data");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        linearLayout_undo = (LinearLayout) findViewById(R.id.linearLayout_img_undo);
        linearLayout_delete = (LinearLayout) findViewById(R.id.linearLayout_img_delete);
        linearLayout_save = (LinearLayout) findViewById(R.id.linearLayout_img_save);
        //  btn_back = (ImageView) findViewById(R.id.btnbackarrow_image_editing);
        linearLayout_img = (LinearLayout) findViewById(R.id.layout_img);
        view_canvas = new ImageEditing.SketchSheetView(ImageEditing.this);
        paint = new Paint();
        canvas_path = new Path();
        bytearrayoutputstream = new ByteArrayOutputStream();

        linearLayout_img.addView(view_canvas, new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        paint.setColor(Color.parseColor("#ff0000"));

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(10);
        buttons_click();
    }

    class SketchSheetView extends View {

        public SketchSheetView(Context context) {

            super(context);

            bitmap = Bitmap.createBitmap(820, 480, Bitmap.Config.ARGB_4444);

            canvas = new Canvas(bitmap);

            this.setBackgroundColor(Color.WHITE);
        }

        private ArrayList<DrawingClass> DrawingClassArrayList = new ArrayList<DrawingClass>();

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            DrawingClass pathWithPaint = new DrawingClass();

            canvas.drawPath(canvas_path, paint);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                canvas_path.moveTo(event.getX(), event.getY());

                canvas_path.lineTo(event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                canvas_path.lineTo(event.getX(), event.getY());

                pathWithPaint.setPath(canvas_path);

                pathWithPaint.setPaint(paint);

                DrawingClassArrayList.add(pathWithPaint);
            }

            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (DrawingClassArrayList.size() > 0) {

                canvas.drawPath(
                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPath(),

                        DrawingClassArrayList.get(DrawingClassArrayList.size() - 1).getPaint());
            }
        }
    }

    public class DrawingClass {

        Path DrawingClassPath;
        Paint DrawingClassPaint;

        public Path getPath() {
            return DrawingClassPath;
        }

        public void setPath(Path path) {
            this.DrawingClassPath = path;
        }


        public Paint getPaint() {
            return DrawingClassPaint;
        }

        public void setPaint(Paint paint) {
            this.DrawingClassPaint = paint;
        }
    }

    private void showCaptureimages() {

        Bundle extras = getIntent().getExtras();
        gettingImageUrl = extras.getString("capture_img");
        getting_img_no = extras.getString("capture_img_no");

        Log.e("indexImage", getting_img_no);

        Picasso.with(ImageEditing.this).load(new File(gettingImageUrl)).into(new Target() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onBitmapLoaded(Bitmap bitmap2, Picasso.LoadedFrom from) {

                view_canvas.setBackground(new BitmapDrawable(bitmap2));
                bitmap = bitmap2;
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void buttons_click() {

//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ImageEditing.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        linearLayout_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bitmap.eraseColor(Color.TRANSPARENT);
                canvas_path.reset();
                view_canvas.invalidate();
                Log.d("canvas_path", canvas_path.toString());

            }
        });

        linearLayout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File fdelete = new File(gettingImageUrl);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
//                        DatabaseHandler databaseHandler = new DatabaseHandler(ImageEditing.this);
//                        databaseHandler.deleteCaptureimgsData(getting_img_no);

                        Intent intent = new Intent();
                        intent.putExtra("image_id", getting_img_no);
                        intent.putExtra("type", "delete");
                        setResult(987, intent);
                        finish();

                        Log.e("ImageIndex", getting_img_no);

                        Toast.makeText(ImageEditing.this, "Image has been deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ImageEditing.this, "Image not delete", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        linearLayout_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteFirst();
//
//                Intent intent = new Intent();
//                intent.putExtra("updated_image", gettingImageUrl);
//                intent.putExtra("image_id", getting_img_no);
//                intent.putExtra("type", "update");
//                setResult(987, intent);
//                finish();

                v = view_canvas;

                v.setDrawingCacheEnabled(true);
                bitmap = v.getDrawingCache();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytearrayoutputstream);

                //file = new File(gettingImageUrl);
                file = new File(folder.getAbsolutePath(), "wc" + System.currentTimeMillis() + ".jpg");

                Log.e("new path", file.getAbsolutePath());

                try {
                    fileoutputstream = new FileOutputStream(file);
                    fileoutputstream.write(bytearrayoutputstream.toByteArray());
                    fileoutputstream.close();
//                    Intent intent = new Intent(ImageEditing.this, AssetTemplateDetails.class);
//                    startActivity(intent);

                    Intent intent = new Intent();
                    intent.putExtra("updated_image", file.getAbsoluteFile().toString());
                    intent.putExtra("image_id", getting_img_no);
                    intent.putExtra("type", "update");
                    setResult(987, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void deleteFirst() {
        File fdelete = new File(gettingImageUrl);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Toast.makeText(ImageEditing.this, "Image has been deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ImageEditing.this, "Image not delete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
