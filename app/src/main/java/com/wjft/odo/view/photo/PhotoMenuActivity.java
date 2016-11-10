package com.wjft.odo.view.photo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wjft.odo.PermissionsActivity;
import com.wjft.odo.R;
import com.wjft.odo.util.PermissionsChecker;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.bt_take_photo)
    Button btTakePhoto;
    @Bind(R.id.bt_api)
    Button btApi;

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    PermissionsChecker mPermissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_menu);
        ButterKnife.bind(this);
        btApi.setOnClickListener(this);
        btTakePhoto.setOnClickListener(this);
        mPermissionChecker = new PermissionsChecker(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_take_photo:
                // 缺少权限时, 进入权限配置页面
                if (mPermissionChecker.lacksPermissions(PERMISSIONS)) {
                    startPermissionsActivity();
                } else {
                    startActivity(PhotoActivity.class);
                }
                break;
            case R.id.bt_api:
                startActivity(TakePhotoActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            System.out.println("resultCode = " + resultCode);
            // finish();
            //PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        }
        if (requestCode == REQUEST_CODE_System) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Log.i("photomenuactivity", "onActivityResult granted");
                } else {
                    Log.i("photomenuactivity", "onActivityResult fail");
                }
            }
        }
    }

    public void startActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    private static final int REQUEST_CODE_System = 1;

    private void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE_System);
    }


}
