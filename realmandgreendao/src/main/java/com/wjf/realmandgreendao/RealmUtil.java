package com.wjf.realmandgreendao;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by Administrator on 2016/11/11.
 */
public class RealmUtil {
    private Context mContext;
    private Realm mRealm;
    private String mDBName = "con.wjf.rag";
    private static RealmUtil mRealmUtil;
    private RealmConfiguration mRealmConfiguration;
    private String mUserName;


    private RealmUtil(Context context) {
        mContext = context;
        mRealmConfiguration = new RealmConfiguration.Builder(mContext).name(mDBName).schemaVersion(3).build();
        Log.d("RealmUtil", mRealmConfiguration.getRealmFileName());
        Log.d("RealmUtil", mRealmConfiguration.getPath());
    }

    public static RealmUtil getInstance(Context context ) {
        if (mRealmUtil == null) {
            synchronized (RealmUtil.class) {
                if (mRealmUtil == null) {
                    mRealmUtil = new RealmUtil(context);
                }
            }
        }
        return mRealmUtil;
    }



    public Realm getRealm() {
        try {
            mRealm = Realm.getInstance(mRealmConfiguration);
        } catch (RealmMigrationNeededException e) {
         //   e.printStackTrace();
            try {
                Realm.migrateRealm(mRealmConfiguration, new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                        System.out.println("user realm = [" + realm + "], oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
                        RealmSchema realmSchema = realm.getSchema();

                        if (oldVersion == 1) {
                            realmSchema.get("User")
                                    .addField("money", Integer.class, null);
                            oldVersion++;
                        }
                        if (oldVersion==2){
                            realmSchema.get("User")
                                    .addField("dog", String.class, null);
                            oldVersion++;
                        }
                    }
                });
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            mRealm = Realm.getInstance(mRealmConfiguration);
        }

        return mRealm;

    }
}
