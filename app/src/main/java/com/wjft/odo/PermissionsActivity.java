package com.wjft.odo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wjft.odo.util.PermissionsChecker;

import java.util.ArrayList;

public class PermissionsActivity extends AppCompatActivity {

    public static final int PERMISSIONS_GRANTED = 0; // 权限授权
    public static final int PERMISSIONS_DENIED = 1; // 权限拒绝

    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
    private static final String EXTRA_PERMISSIONS =
            "me.chunyu.clwang.permission.extra_permission"; // 权限参数
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案

    private PermissionsChecker mChecker; // 权限检测器
    private boolean isRequireCheck; // 是否需要系统权限检测
    String[] permissions;

    // 启动当前权限页面的公开接口
    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        setContentView(R.layout.activity_permissions);

        mChecker = new PermissionsChecker(this);
        isRequireCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            permissions = getPermissions();
            if (mChecker.lacksPermissions(permissions)) {
                requestPermissions(permissions); // 请求权限
            } else {
                allPermissionsGranted(); // 全部权限都已获取
            }
        } else {
            isRequireCheck = true;
        }
    }

    // 返回传递的权限参数
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    // 请求权限兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    // 全部权限均已获取
    private void allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("PermissionsActivity.onRequestPermissionsResult");
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults) == 0) {
            isRequireCheck = true;
            allPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }

    ArrayList<String> deniedPermission = new ArrayList<>();

    // 含有全部的权限
    private int hasAllPermissionsGranted(@NonNull int[] grantResults) {

        for (int i = 0; i < grantResults.length; i++) {
            System.out.println("grantResults[i] = " + grantResults[i]);
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedPermission.add(permissions[i]);
                //  return false;
            }
        }
        return deniedPermission.size();
        // return true;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PermissionsActivity.this);
        builder.setTitle("申请权限");
        builder.setMessage(resultMessage());

        // 拒绝, 退出应用
        builder.setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
        this.finish();
    }


    public String resultMessage() {
        StringBuilder stringBuilder = new StringBuilder("需要获取以下权限才能进行接下来的操作：");
        if (deniedPermission.size() > 0) {
            for (int i = 0; i < deniedPermission.size(); i++) {
                switch (deniedPermission.get(i)) {
                    case Manifest.permission.CAMERA:
                        stringBuilder.append("相机权限;");
                        break;
                    case Manifest.permission.WRITE_APN_SETTINGS:
                    case Manifest.permission.READ_EXTERNAL_STORAGE:
                        stringBuilder.append("SD卡权限;");
                        break;
                    case Manifest.permission.READ_CONTACTS:
                    case Manifest.permission.WRITE_CONTACTS:
                    case Manifest.permission.GET_ACCOUNTS:
                        stringBuilder.append("获取联系人权限;");
                        break;
                    case Manifest.permission.RECORD_AUDIO:
                        stringBuilder.append("允许程序录制音频;");
                        break;
                    case Manifest.permission.READ_PHONE_STATE:
                    case Manifest.permission.CALL_PHONE:
                    case Manifest.permission.READ_CALL_LOG:
                    case Manifest.permission.WRITE_CALL_LOG:
                    case Manifest.permission.ADD_VOICEMAIL:
                    case Manifest.permission.USE_SIP:
                    case Manifest.permission.PROCESS_OUTGOING_CALLS:
                        stringBuilder.append("读取手机状态;");
                        break;
                    case Manifest.permission.BODY_SENSORS:
                        stringBuilder.append("传感器;");
                        break;
                    case Manifest.permission.SEND_SMS:
                    case Manifest.permission.RECEIVE_SMS:
                    case Manifest.permission.READ_SMS:
                    case Manifest.permission.RECEIVE_WAP_PUSH:
                    case Manifest.permission.RECEIVE_MMS:
                        stringBuilder.append("短信权限;");
                        break;
                    case Manifest.permission.READ_CALENDAR:
                    case Manifest.permission.WRITE_CALENDAR:
                        stringBuilder.append("日历权限;");
                        break;
                }
            }
        }
        stringBuilder.append("。去系统设置权限");
        return stringBuilder.toString();
    }


}
