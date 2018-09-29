package com.handsomexi.homework.util;

import android.widget.Toast;

import com.handsomexi.homework.Myapp;

public class Toasty {
    public static void Info(String s){
        Toast.makeText(Myapp.getInstances(),s,Toast.LENGTH_SHORT).show();
    }
}
