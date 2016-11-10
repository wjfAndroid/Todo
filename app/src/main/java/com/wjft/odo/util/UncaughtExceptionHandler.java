package com.wjft.odo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.util.Calendar;
import java.util.Locale;

/**
 * Create By Anthony on 2016/1/16
 * 当前类注释:异常处理类，将我们的异常信息保存到本地SD卡上面或者上传到服务器
 * 此处保存到sd卡
 * 其他地方调用 需要设置 动态权限
 * Thread.setDefaultUncaughtExceptionHandler(
 new UncaughtExceptionHandler(getApplicationContext(), Thread.getDefaultUncaughtExceptionHandler()));
 */
public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final boolean DEBUG =true;
    private static final String TAG ="Crash";
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    public UncaughtExceptionHandler(Context context, Thread.UncaughtExceptionHandler defaultHandler){
        this.mDefaultHandler = defaultHandler;
        this.mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("Crash", "Application crash", ex);
        writeFile(thread, ex);//将异常信息保存到SD卡上面
        //TODO 在这里写方法将异常信息上传到服务器
        mDefaultHandler.uncaughtException(thread, ex);
    }

    private void writeFile(final Thread thread, final Throwable ex){
        //如果SD卡不存在则无法写入
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            if(DEBUG){
                Log.w(TAG,"SDCard unmounted, skip write exception to file");
                return ;
            }
        }
        try {
            OutputStream os = getLogStream();
            os.write(getExceptionInformation(thread, ex).getBytes("utf-8"));
            os.flush();
            os.close();

            android.os.Process.killProcess(android.os.Process.myPid());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private OutputStream getLogStream() throws IOException {

        //crash_log_pkgname.log
        String fileName = String.format("crash_%s.log", mContext.getPackageName());
        File file  = new File(Environment.getExternalStorageDirectory(), fileName);

        if(!file.exists()){
            file.createNewFile();
        }

        System.out.println("创建文件 file"+file.getAbsolutePath());

        OutputStream os = new FileOutputStream(file, true);

        return os;
    }



    private String getExceptionInformation(Thread thread, Throwable ex){
        long current = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder().append('\n');
        sb.append("THREAD: ").append(thread).append('\n');
        sb.append("BOARD: ").append(Build.BOARD).append('\n');
        sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append('\n');
        sb.append("BRAND: ").append(Build.BRAND).append('\n');
        sb.append("CPU_ABI: ").append(Build.CPU_ABI).append('\n');
        sb.append("CPU_ABI2: ").append(Build.CPU_ABI2).append('\n');
        sb.append("DEVICE: ").append(Build.DEVICE).append('\n');
        sb.append("DISPLAY: ").append(Build.DISPLAY).append('\n');
        sb.append("FINGERPRINT: ").append(Build.FINGERPRINT).append('\n');
        sb.append("HARDWARE: ").append(Build.HARDWARE).append('\n');
        sb.append("HOST: ").append(Build.HOST).append('\n');
        sb.append("ID: ").append(Build.ID).append('\n');
        sb.append("MANUFACTURER: ").append(Build.MANUFACTURER).append('\n');
        sb.append("MODEL: ").append(Build.MODEL).append('\n');
        sb.append("PRODUCT: ").append(Build.PRODUCT).append('\n');
        sb.append("SERIAL: ").append(Build.SERIAL).append('\n');
        sb.append("TAGS: ").append(Build.TAGS).append('\n');
        sb.append("TIME: ").append(Build.TIME).append(' ').append(toDateString(Build.TIME)).append('\n');
        sb.append("TYPE: ").append(Build.TYPE).append('\n');
        sb.append("USER: ").append(Build.USER).append('\n');
        sb.append("VERSION.CODENAME: ").append(Build.VERSION.CODENAME).append('\n');
        sb.append("VERSION.INCREMENTAL: ").append(Build.VERSION.INCREMENTAL).append('\n');
        sb.append("VERSION.RELEASE: ").append(Build.VERSION.RELEASE).append('\n');
        sb.append("VERSION.SDK_INT: ").append(Build.VERSION.SDK_INT).append('\n');
        sb.append("LANG: ").append(mContext.getResources().getConfiguration().locale.getLanguage()).append('\n');
        sb.append("APP.VERSION.NAME: ").append(getVersionName()).append('\n');
        sb.append("APP.VERSION.CODE: ").append(getVersionCode()).append('\n');
        sb.append("CURRENT: ").append(current).append(' ').append(toDateString(current)).append('\n');

        sb.append(getErrorInformation(ex));

        return sb.toString();
    }

    private String getVersionName(){
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;

        return version;
    }

    private int getVersionCode(){
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int version = packInfo.versionCode;

        return version;
    }


    private String getErrorInformation(Throwable t){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter writter = new PrintWriter(baos);
        t.printStackTrace(writter);
        writter.flush();
        String result = new String(baos.toByteArray());
        writter.close();

        return result;
    }

    private String toDateString(long timeMilli){
        Calendar calc = Calendar.getInstance();
        calc.setTimeInMillis(timeMilli);
        return String.format(Locale.CHINESE, "%04d.%02d.%02d %02d:%02d:%02d:%03d",
                calc.get(Calendar.YEAR), calc.get(Calendar.MONTH) + 1, calc.get(Calendar.DAY_OF_MONTH),
                calc.get(Calendar.HOUR_OF_DAY), calc.get(Calendar.MINUTE), calc.get(Calendar.SECOND), calc.get(Calendar.MILLISECOND));
    }



}