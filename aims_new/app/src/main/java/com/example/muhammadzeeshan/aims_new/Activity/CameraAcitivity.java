package com.example.muhammadzeeshan.aims_new.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muhammadzeeshan.aims_new.Activity.TemplateDetails.AssetTemplateDetails;
import com.example.muhammadzeeshan.aims_new.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static com.example.muhammadzeeshan.aims_new.GeneralMethods.getOutputMediaFile;

@TargetApi(Build.VERSION_CODES.N)
public class CameraAcitivity extends AppCompatActivity implements SurfaceHolder.Callback {


    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Handler customHandler = new Handler();
    int flag = 0;
    int flashType = 1;
    private File tempFile = null;
    private Camera.PictureCallback jpegCallback;
    private static int CAPTURED_TIMER = 1000;
    private static int IMG_TIMEOUT = 1000;
    private String log = "chcking";
    public static ArrayList<String> listOfCardview = new ArrayList<String>();
    public ArrayList<String> listOfCaptureimgs = new ArrayList<String>();
    private MediaRecorder mediaRecorder;
    private SurfaceView imgSurface;
    private ImageView imgCapture;
    private TextView textCounter;
    private TextView hintTextView;
    private ImageView imageView_circle_shape;
    private Float get_x;
    private Float get_y;
    public Bitmap bitmap1;
    byte[] arr;
    private LinearLayout linearLayout_btn_upload;
    private LinearLayout linearLayout;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (myOrientationEventListener != null)
                myOrientationEventListener.enable();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    private File folder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_acitivity);

        initControls();
        identifyOrientationEvents();

        getOutputMediaFile();

        //capture image on callback
        captureImageCallback();

        if (camera != null) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        try {
            if (flag == 0) {
                camera = Camera.open(0);
            } else {
                camera = Camera.open(1);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }

        try {
            Camera.Parameters param;
            param = camera.getParameters();
            List<Camera.Size> sizes = param.getSupportedPreviewSizes();
            //get diff to get perfact preview sizes
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            long diff = (height * 1000 / width);
            long cdistance = Integer.MAX_VALUE;
            int idx = 0;
            for (int i = 0; i < sizes.size(); i++) {
                long value = (long) (sizes.get(i).width * 1000) / sizes.get(i).height;
                if (value > diff && value < cdistance) {
                    idx = i;
                    cdistance = value;
                }
                Log.e("WHHATSAPP", "width=" + sizes.get(i).width + " height=" + sizes.get(i).height);
            }
            Log.e("WHHATSAPP", "INDEX:  " + idx);
            Camera.Size cs = sizes.get(idx);
            param.setPreviewSize(cs.width, cs.height);
            param.setPictureSize(cs.width, cs.height);
            camera.setParameters(param);
            setCameraDisplayOrientation(0);

            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

            if (flashType == 1) {
                param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);

            } else if (flashType == 2) {
                param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                Camera.Parameters params = null;
                if (camera != null) {
                    params = camera.getParameters();

                    if (params != null) {
                        List<String> supportedFlashModes = params.getSupportedFlashModes();

                        if (supportedFlashModes != null) {
                            if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                                param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            } else if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_ON)) {
                                param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                            }
                        }
                    }
                }

            } else if (flashType == 3) {
                param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);

            }


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        try {
            camera.stopPreview();
            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //All Methods Copied from AIMS.............................

    private void snapeffect() {
        AlphaAnimation alpha = new AlphaAnimation(0.8F, 0.8F);
        alpha.setDuration(0); // Make animation instant
        alpha.setFillAfter(true); // Tell it to persist after the animation ends
        // And then on your layout
        linearLayout.startAnimation(alpha);
    }

    private void snapeffect_none() {
        AlphaAnimation alpha = new AlphaAnimation(1F, 1F);
        alpha.setDuration(0); // Make animation instant
        alpha.setFillAfter(true); // Tell it to persist after the animation ends
        // And then on your layout
        linearLayout.startAnimation(alpha);
    }

    private void cancelSavePicTaskIfNeed() {
        if (savePicTask != null && savePicTask.getStatus() == AsyncTask.Status.RUNNING) {
            savePicTask.cancel(true);
        }
    }

    private SavePicTask savePicTask;

    private class SavePicTask extends AsyncTask<Void, Void, String> {
        private byte[] data;
        private int rotation = 0;

        public SavePicTask(byte[] data, int rotation) {
            this.data = data;
            this.rotation = rotation;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                return saveToSDCard(data, rotation);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    public String saveToSDCard(byte[] data, int rotation) throws IOException {
        String imagePath = "";

        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int reqHeight = metrics.heightPixels;
            int reqWidth = metrics.widthPixels;
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            if (rotation != 0) {
                Matrix mat = new Matrix();
                mat.postRotate(rotation);
                bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);

                Paint paint = new Paint();
                paint.setColor(getResources().getColor(R.color.dark_blue));
                paint.setAntiAlias(true);
                paint.setStrokeWidth(4);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeJoin(Paint.Join.ROUND);
                paint.setStrokeCap(Paint.Cap.ROUND);
                Canvas canvas = new Canvas(bitmap1);
                canvas.drawCircle(get_x - 100, get_y - 100, 100, paint);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
                arr = baos.toByteArray();
                this.listOfCardview.add(Base64.encodeToString(arr, Base64.DEFAULT));
                for (int i = 1; i < this.listOfCardview.size(); i++) {
                    Log.e(String.valueOf(i), this.listOfCardview.get(i));

                }

                Log.e("checkcoordinatesave", "X" + String.valueOf(get_x - 100) + " Y " + String.valueOf(get_y - 100).toString());
                if (bitmap != bitmap1) {
                    bitmap.recycle();
                }

                imagePath = getSavePhotoLocal(bitmap1);

                if (bitmap1 != null) {
                    bitmap1.recycle();
                    Toast.makeText(CameraAcitivity.this, "Bitmap Cleared", Toast.LENGTH_SHORT).show();
                }
            } else {
                imagePath = getSavePhotoLocal(bitmap);
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    private void activeCameraCapture() {

        if (imgCapture != null) {
            imgCapture.setAlpha(1.0f);
            try {


                linearLayout_btn_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent_recycler_activity = new Intent(CameraAcitivity.this, AssetTemplateDetails.class);
                        startActivity(intent_recycler_activity);

                        bitmap1.recycle();

//                        Intent intent = new Intent();
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("modelReturn", layoutModel);
//                        intent.putExtra("bundleReturn", bundle);
                        intent_recycler_activity.putStringArrayListExtra("images", listOfCaptureimgs);
                        setResult(AssetTemplateDetails.reqCode, intent_recycler_activity);
                        finish();
                    }
                });

                imgCapture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshCamera();
                        if (isSpaceAvailable()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    captureImage();

                                }
                            }, CAPTURED_TIMER);
                        } else {
                            hintTextView.setText("Your Memory is Full");
                            Toast.makeText(CameraAcitivity.this, "Memory is not available", Toast.LENGTH_SHORT).show();
                            inActiveCameraCapture();
                        }
                    }
                });

                linearLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        linearLayout.setOnTouchListener(null);
                        get_x = event.getX();
                        get_y = event.getY();
                        imageView_circle_shape.setX(get_x - 100);
                        imageView_circle_shape.setY(get_y - 100);
                        imageView_circle_shape.setVisibility(VISIBLE);
                        Log.e("checkcoordinate", "X" + String.valueOf(get_x - 100) + " Y " + String.valueOf(get_y - 100).toString());
//                refreshCamera();
                        if (isSpaceAvailable()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    captureImage();

                                }
                            }, CAPTURED_TIMER);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageView_circle_shape.setVisibility(View.GONE);

                                }
                            }, IMG_TIMEOUT);
                        } else {
                            hintTextView.setText("Your Memory is Full");
                            imageView_circle_shape.setVisibility(View.GONE);
                            Toast.makeText(CameraAcitivity.this, "Memory is not available", Toast.LENGTH_SHORT).show();
                            inActiveCameraCapture();
                        }
                        return true;
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getSavePhotoLocal(Bitmap bitmap) {
        String path = "";

        try {
            OutputStream output;
            File file = new File(folder.getAbsolutePath(), "wc" + System.currentTimeMillis() + ".jpg");
            Log.e("path", file.toString());
            // preferencesHandler.setCurrentCapturedImgs(file.toString());
            Log.e("sor", file.toString());


            listOfCaptureimgs.add(file.toString());
            for (int i = 0; i < this.listOfCaptureimgs.size(); i++) {
                Log.e("CapturedImagesList", this.listOfCaptureimgs.get(i));
            }

            //Data of Comma separated Images Sent into Database...................

//            String Images = "";
//            Log.e("list size", String.valueOf(listOfCaptureimgs.size()));
//            for (int i = 0; i < listOfCaptureimgs.size(); i++) {
//                Log.e("index", String.valueOf(i));
//                if (i != listOfCaptureimgs.size()-1)
//                    Images += listOfCaptureimgs.get(i) + ",";
//                else
//                    Images += listOfCaptureimgs.get(i);
//            }
//            Log.e("ImagesString", Images);


            try {
                output = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                output.flush();
                output.close();
                path = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    private void captureImageCallback() {

        surfaceHolder = imgSurface.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        jpegCallback = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {

                refreshCamera();

                cancelSavePicTaskIfNeed();
                savePicTask = new SavePicTask(data, getPhotoRotation());
                savePicTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

            }
        };
    }

    //Inner Async Task Class....................................
//    private class SaveVideoTask extends AsyncTask<Void, Void, Void> {
//
//        File thumbFilename;
//
//        ProgressDialog progressDialog = null;
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog = new ProgressDialog(CameraAcitivity.this);
//            progressDialog.setMessage("Processing a video...");
//            progressDialog.show();
//            super.onPreExecute();
//            imgCapture.setOnTouchListener(null);
//            textCounter.setVisibility(View.GONE);
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                try {
//                    myOrientationEventListener.enable();
//
//                    customHandler.removeCallbacksAndMessages(null);
//
//                    mediaRecorder.stop();
//                    releaseMediaRecorder();
//
//                    tempFile = new File(folder.getAbsolutePath() + "/" + mediaFileName + ".mp4");
//                    thumbFilename = new File(folder.getAbsolutePath(), "t_" + mediaFileName + ".jpeg");
//                    generateVideoThmb(tempFile.getPath(), thumbFilename);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//            return null;
//        }
//
////        @Override
////        protected void onPostExecute(Void aVoid) {
////            super.onPostExecute(aVoid);
////
////            if (progressDialog != null) {
////                if (progressDialog.isShowing()) {
////                    progressDialog.dismiss();
////                }
////            }
////            if (tempFile != null && thumbFilename != null)
////                onVideoSendDialog(tempFile.getAbsolutePath(), thumbFilename.getAbsolutePath());
////        }
//    }

    private int mPhotoAngle = 90;

    private void identifyOrientationEvents() {

        myOrientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int iAngle) {

                final int iLookup[] = {0, 0, 0, 90, 90, 90, 90, 90, 90, 180, 180, 180, 180, 180, 180, 270, 270, 270, 270, 270, 270, 0, 0, 0}; // 15-degree increments
                if (iAngle != ORIENTATION_UNKNOWN) {

                    int iNewOrientation = iLookup[iAngle / 15];
                    if (iOrientation != iNewOrientation) {
                        iOrientation = iNewOrientation;
                        if (iOrientation == 0) {
                            mOrientation = 90;
                        } else if (iOrientation == 270) {
                            mOrientation = 0;
                        } else if (iOrientation == 90) {
                            mOrientation = 180;
                        }

                    }
                    mPhotoAngle = normalize(iAngle);
                }
            }
        };

        if (myOrientationEventListener.canDetectOrientation()) {
            myOrientationEventListener.enable();
        }
    }

    private void initControls() {
        linearLayout_btn_upload = (LinearLayout) findViewById(R.id.button_upload);
        mediaRecorder = new MediaRecorder();
        imgSurface = (SurfaceView) findViewById(R.id.imgSurface);
        textCounter = (TextView) findViewById(R.id.textCounter);
        imgCapture = (ImageView) findViewById(R.id.imgCapture);
        linearLayout = (LinearLayout) findViewById(R.id.layout_Camera);
        imageView_circle_shape = (ImageView) findViewById(R.id.img_circle_shape);
        imageView_circle_shape.setVisibility(View.GONE);
        textCounter.setVisibility(View.GONE);
        hintTextView = (TextView) findViewById(R.id.hintTextView);

        activeCameraCapture();

    }

    private void captureImage() {

        try {

            camera.takePicture(null, null, jpegCallback);
            activeCameraCapture();
            snapeffect();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    snapeffect_none();
                }
            }, CAPTURED_TIMER);
            final MediaPlayer mp = MediaPlayer.create(CameraAcitivity.this, R.raw.shutter);
            mp.start();

        } catch (Exception ex) {
            Log.e("error", ex.getMessage().toString());
        }


    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = new MediaRecorder();
        }
    }

    public void refreshCamera() {

        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
            Camera.Parameters param = camera.getParameters();

            if (flag == 0) {

                if (flag == 0 && flashType == 2) {
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    Camera.Parameters params = null;
                    if (camera != null) {
                        params = camera.getParameters();

                        if (params != null) {
                            List<String> supportedFlashModes = params.getSupportedFlashModes();

                            if (supportedFlashModes != null) {
                                if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                                    param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                } else if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_ON)) {
                                    param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                                }
                            }
                        }
                    }
                    //   imgFlashOnOff.setImageResource(R.drawable.ic_flash_on);
                } else if (flashType == 3) {
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    //    imgFlashOnOff.setImageResource(R.drawable.ic_flash_off);
                }
            }


            refrechCameraPriview(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refrechCameraPriview(Camera.Parameters param) {
        try {
            camera.setParameters(param);
            setCameraDisplayOrientation(0);

            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            camera.autoFocus(myAutoFocusCallback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCameraDisplayOrientation(int cameraId) {

        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        int rotation = getWindowManager().getDefaultDisplay().getRotation();

        if (Build.MODEL.equalsIgnoreCase("Nexus 6") && flag == 1) {
            rotation = Surface.ROTATION_180;
        }
        int degrees = 0;
        switch (rotation) {

            case Surface.ROTATION_0:

                degrees = 0;
                break;

            case Surface.ROTATION_90:

                degrees = 90;
                break;

            case Surface.ROTATION_180:

                degrees = 180;
                break;

            case Surface.ROTATION_270:

                degrees = 270;
                break;

        }

        int result;

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror

        } else {
            result = (info.orientation - degrees + 360) % 360;

        }

        camera.setDisplayOrientation(result);

    }

    private long timeInMilliseconds = 0L, startTime = SystemClock.uptimeMillis(), updatedTime = 0L, timeSwapBuff = 0L;
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            int hrs = mins / 60;

            secs = secs % 60;
            textCounter.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);

        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        try {

            if (customHandler != null)
                customHandler.removeCallbacksAndMessages(null);

            releaseMediaRecorder();       // if you are using MediaRecorder, release it first

            if (myOrientationEventListener != null)
                myOrientationEventListener.enable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean arg0, Camera arg1) {
            if (arg0) {
                camera.autoFocus(this);
                camera.cancelAutoFocus();
            }
        }
    };

//    public void onVideoSendDialog(final String videopath, final String thumbPath) {
//
//        runOnUiThread(new Runnable() {
//            @SuppressLint("StringFormatMatches")
//            @Override
//            public void run() {
//                if (videopath != null) {
//                    File fileVideo = new File(videopath);
//                    long fileSizeInBytes = fileVideo.length();
//                    long fileSizeInKB = fileSizeInBytes / 1024;
//                    long fileSizeInMB = fileSizeInKB / 1024;
//
//                }
//            }
//        });
//    }

    private void inActiveCameraCapture() {
        if (imgCapture != null) {
            imgCapture.setAlpha(0.5f);
            imgCapture.setOnClickListener(null);
        }
    }


    //Check for Memory...............................
    public int getFreeSpacePercantage() {
        int percantage = (int) (freeMemory() * 100 / totalMemory());
        int modValue = percantage % 5;
        return percantage - modValue;
    }

    public double totalMemory() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdAvailSize = (double) stat.getBlockCount() * (double) stat.getBlockSize();
        return sdAvailSize / 1073741824;
    }

    public double freeMemory() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdAvailSize = (double) stat.getAvailableBlocks() * (double) stat.getBlockSize();
        return sdAvailSize / 1073741824;
    }

    public boolean isSpaceAvailable() {
        if (getFreeSpacePercantage() >= 1) {
            return true;
        } else {
            return false;
        }
    }


    //End Memory Check Method.........................
    private String mediaFileName = null;

    OrientationEventListener myOrientationEventListener;
    int iOrientation = 0;
    int mOrientation = 90;

    public void generateVideoThmb(String srcFilePath, File destFile) {
        try {
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(srcFilePath, 120);
            FileOutputStream out = new FileOutputStream(destFile);
            ThumbnailUtils.extractThumbnail(bitmap, 200, 200).compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int normalize(int degrees) {
        if (degrees > 315 || degrees <= 45) {
            return 0;
        }

        if (degrees > 45 && degrees <= 135) {
            return 90;
        }

        if (degrees > 135 && degrees <= 225) {
            return 180;
        }

        if (degrees > 225 && degrees <= 315) {
            return 270;
        }

        throw new RuntimeException("Error....");
    }

    private int getPhotoRotation() {
        int rotation;
        int orientation = mPhotoAngle;

        Camera.CameraInfo info = new Camera.CameraInfo();
        if (flag == 0) {
            Camera.getCameraInfo(0, info);
        } else {
            Camera.getCameraInfo(1, info);
        }

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotation = (info.orientation - orientation + 360) % 360;
        } else {
            rotation = (info.orientation + orientation) % 360;
        }
        return rotation;
    }
}