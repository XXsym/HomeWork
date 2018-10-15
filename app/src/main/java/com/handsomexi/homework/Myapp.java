package com.handsomexi.homework;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.handsomexi.homework.bean.DaoMaster;
import com.handsomexi.homework.bean.DaoSession;

import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class Myapp extends Application {
    public static Myapp instances;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    public DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
        OpenCVLoader.initDebug();
    }
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "main", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public static Myapp getInstances(){
        return instances;
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }


}
