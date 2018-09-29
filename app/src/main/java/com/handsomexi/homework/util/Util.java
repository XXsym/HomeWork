package com.handsomexi.homework.util;

import android.content.Intent;
import android.os.Environment;

import com.handsomexi.homework.bean.HomeWorkBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Util {
    public static void mkDirs(){
        File file = getWrongFile();
        boolean a = false;
        if(!file.exists())
            a = file.mkdirs();
    }
    public static File getWrongFile(){
        return  new File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/Wrong");
    }
    public static File getNewPicFile(){
        return new File(getWrongFile().getPath(),new Date().getTime()+".jpg");
    }
    public static List<Integer> intArray2List(int[] a){
        List<Integer> integers = new ArrayList<>();
        for (int i :a){
            integers.add(i);
        }
        return integers;
    }
    public static String long2Date(long time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        String year = cal.get(Calendar.YEAR)+"年";
        String month = cal.get(Calendar.MONTH)+1 + "月";
        String day = cal.get(Calendar.DATE) +"日";
        return year + month +day;
    }

    public static Intent getHomeIntent(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        return intent;

    }
}
