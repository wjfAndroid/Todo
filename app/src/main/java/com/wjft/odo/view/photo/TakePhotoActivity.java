package com.wjft.odo.view.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wjft.odo.R;
import com.wjft.odo.util.FileSizeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.LogManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class TakePhotoActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.bt_tak_photo)
    Button btTakPhoto;
    @Bind(R.id.bt_gallary)
    Button btGallary;
    @Bind(R.id.imageView)
    ImageView imageView;
    private String TAG = "TakePhotoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ButterKnife.bind(this);
        btTakPhoto.setOnClickListener(this);
        btGallary.setOnClickListener(this);
    }

    public static final int REQUEST_TAKE_PHOTO = 0X12;
    public static final int REQUEST_CROP = 0X13;
    Uri uri;
    Uri uriContent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tak_photo:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File photFile = createFile();
                    if (photFile != null) {
                        uriContent = FileProvider.getUriForFile(this, "com.wjft.odo.view.photo.fileprovider", photFile);
                        uri = Uri.fromFile(photFile);
                        //如果api>=24使用content：//
                        //如果api<24使用filename：// 和使用content：//都没问题
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriContent);
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    }
                }
                break;
            case R.id.bt_gallary:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //data会返回空，在没有设置保存路径时会保存到默认的路径，如果设置了保存路径则data为null
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                cutImage();
            }
        }
        if (requestCode == REQUEST_CROP) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                imageView.setImageBitmap(bitmap);
            }
        }
    }


    private void cutImage() {
        if (uri != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            //如果api>=24使用content：//
            //如果api<24使用filename：//
            intent.setDataAndType(uri, "image/*");//需要裁剪的照片地址
            //裁剪框是否显示
            intent.putExtra("crop", true);
            //华为特殊处理 不然会显示圆   裁剪框比例
            if (android.os.Build.MANUFACTURER.contains("HUAWEI")) {
                intent.putExtra("aspectX", 9998);
                intent.putExtra("aspectY", 9999);
            } else {
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
            }
            //设置照片大小
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            //是否返回数据，如果不返回则可以使用uri找到
            intent.putExtra("return-data", true);
            intent.putExtra("output", uri);//裁剪完成保存的地址
            intent.putExtra("scale", true);

            intent.putExtra("noFaceDetection", false);//取消人脸识别

            startActivityForResult(intent, REQUEST_CROP);
        }
    }

    String currentFilePath;
//    File cropFile;
//    String cropFilePath;

    public File createFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = "JPG_" + timeStamp;
        //图片保存到/Android/data/com.wjft.odo/files/Pictures/
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File cropDir = getExternalFilesDir(Environment.DIRECTORY_DCIM);
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
}
