package com.wjft.odo.view.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wjft.odo.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.bt_take_photo)
    Button btTakePhoto;
    @Bind(R.id.imgv)
    ImageView imgv;
    @Bind(R.id.bt_take_clear_photo)
    Button btTakeClearPhoto;
    @Bind(R.id.bt_take_save)
    Button btTakeSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);


        init();

//        getPath();
//        getApplicationDirectories(this);
    }

    public void getPath() {
        Log.i("PhotoActivity", "getRootDirectory(): "
                + Environment.getRootDirectory().toString());
        Log.i("PhotoActivity", "getDataDirectory(): "
                + Environment.getDataDirectory().toString());
        Log.i("PhotoActivity", "getDownloadCacheDirectory(): "
                + Environment.getDownloadCacheDirectory().toString());
        Log.i("PhotoActivity", "getExternalStorageDirectory(): "
                + Environment.getExternalStorageDirectory().toString());

        Log.i("PhotoActivity", "getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES): "
                + Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString());

        Log.i(
                "PhotoActivity",
                "isExternalStorageEmulated(): "
                        + Environment.isExternalStorageEmulated());

        Log.i(
                "PhotoActivity",
                "isExternalStorageRemovable(): "
                        + Environment.isExternalStorageRemovable());

    }

    public static void getApplicationDirectories(Context context) {

        Log.i("PhotoActivity", "context.getFilesDir():"
                + context.getFilesDir().toString());
        Log.i("PhotoActivity", "context.getCacheDir():"
                + context.getCacheDir().toString());

        // methods below will return null if the permissions denied
        Log.i(
                "PhotoActivity",
                "context.getExternalFilesDir(Environment.DIRECTORY_MOVIES): "
                        + context
                        .getExternalFilesDir(Environment.DIRECTORY_MOVIES));

        Log.i(
                "PhotoActivity",
                "context.getExternalCacheDir(): "
                        + context.getExternalCacheDir());
    }

    private void init() {
        btTakePhoto.setOnClickListener(this);
        btTakeClearPhoto.setOnClickListener(this);
        btTakeSave.setOnClickListener(this);

    }

    public static final int REQUEST_THUMB = 0X11;
    public static final int REQUEST_TAKE_PHOTO = 0X12;
    public static final int REQUEST_TAKE_PHOTO_AND_SAVE = 0X13;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_take_photo:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_THUMB, null);
                }
                break;
            case R.id.bt_take_clear_photo:
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent1.resolveActivity(getPackageManager()) != null) {
                    File photFile = createFile();
                    if (photFile != null) {
                        Uri photoUri = FileProvider.getUriForFile(this, "com.wjft.odo.view.photo.fileprovider", photFile);
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent1, REQUEST_TAKE_PHOTO);
                    }
                }
                break;
            case R.id.bt_take_save:
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent2.resolveActivity(getPackageManager()) != null) {
                    File photFile = createSaveFile();
                    if (photFile != null) {
                        Uri photoUri = FileProvider.getUriForFile(this, "com.wjft.odo.view.photo.fileprovider", photFile);
                        Log.d("PhotoActivity", "photoUri:" + photoUri);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent2, REQUEST_TAKE_PHOTO_AND_SAVE);

                        galleryAdd();
                    }
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_THUMB && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            Log.d("PhotoActivity", "bitmap.getHeight():" + bitmap.getHeight() + "bitmap.getWidth():" + bitmap.getWidth());
            imgv.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            imgv.setImageBitmap(BitmapFactory.decodeFile(currentFilePath));
        }

        if (requestCode == REQUEST_TAKE_PHOTO_AND_SAVE && resultCode == Activity.RESULT_OK) {
            imgv.setImageBitmap(BitmapFactory.decodeFile(saveFilePath));
        }
    }

    String currentFilePath;

    public File createFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = null;
        try {
            file = File.createTempFile(fileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentFilePath = file.getAbsolutePath();
        Log.d("PhotoActivity", currentFilePath);
        return file;
    }

    String saveFilePath;

    public File createSaveFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = "JPEG_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File file = null;
        try {
            file = File.createTempFile(fileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveFilePath = file.getAbsolutePath();
        Log.d("PhotoActivity", saveFilePath);
        return file;
    }

    public void galleryAdd() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(saveFilePath);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);
    }

}
