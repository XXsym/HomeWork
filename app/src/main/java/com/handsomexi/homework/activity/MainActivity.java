package com.handsomexi.homework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.handsomexi.homework.R;
import com.handsomexi.homework.adapter.FragmentAdapter;
import com.handsomexi.homework.bean.HomeWorkBean;
import com.handsomexi.homework.bean.subject;
import com.handsomexi.homework.fragment.aFragment;
import com.handsomexi.homework.fragment.bFragment;
import com.handsomexi.homework.fragment.cFragment;
import com.handsomexi.homework.fragment.dFragment;
import com.handsomexi.homework.util.ImageUtil;
import com.handsomexi.homework.util.PermissionUtil;
import com.handsomexi.homework.util.SqlUtil;
import com.handsomexi.homework.util.Util;
import com.handsomexi.homework.view.BottomRow;
import com.handsomexi.homework.view.MainViewpager;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.main_pager)
    MainViewpager mainPager;
    @BindView(R.id.main_edit)
    EditText mainEdit;

    @OnClick({R.id.main_add,R.id.main_camera})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.main_add:{
                startActivity(new Intent(this,TestActivity.class));
                break;
            }

            case R.id.main_camera: {
                ImageUtil.openCamera(this);
                break;
            }
        }
    }

    private BottomRow[] rows = new BottomRow[4];

    void initView() {
        rows[0] = findViewById(R.id.main_brow1);
        rows[1] = findViewById(R.id.main_brow2);
        rows[2] = findViewById(R.id.main_brow3);
        rows[3] = findViewById(R.id.main_brow4);
        for (BottomRow row : rows) row.setOnClickListener(this);
        rows[0].callOnClick();

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), new Fragment[]{new aFragment(),new bFragment(),new cFragment(),new dFragment()});
        mainPager.setAdapter(fragmentAdapter);

        boolean isFirstUse = SPUtils.getInstance().getBoolean("isFirstUse", true);
        if (isFirstUse) {
            iniBean();
            startActivity(new Intent(this, IntroActivity.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (PermissionUtil.allGranted()) {
            initView();
        } else
            PermissionUtil.checkPermissions(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 666: {//Camera
                if (resultCode == 1) {
                    data.setClass(this, AdjustActivity.class);
                    startActivity(data);
                }
                break;
            }
        }
    }

    void setRow(int i) {
        for (int j = 0; j < 4; j++) {
            if (j == i) rows[j].select();
            else rows[j].unSelected();
        }
        mainPager.setCurrentItem(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_brow1: {
                setRow(0);
                break;
            }
            case R.id.main_brow2: {
                setRow(1);
                break;
            }
            case R.id.main_brow3: {
                setRow(2);
                break;
            }
            case R.id.main_brow4: {
                setRow(3);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtil.allGranted()) {
            initView();
        } else {
            PermissionUtil.checkPermissions(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(Util.getHomeIntent());
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    private void iniBean() {
        HomeWorkBean[] mHomeWorkBean = new HomeWorkBean[10];
        mHomeWorkBean[0] = new HomeWorkBean("a1","语文","小学二年级","上学期",3,2,new Date().getTime(),"语文");
        mHomeWorkBean[1] = new HomeWorkBean("a2","数学","初中三年级","下学期",2,3,new Date().getTime(),"数学");
        mHomeWorkBean[2] = new HomeWorkBean("a3","物理","初中二年级","下学期",1,4,new Date().getTime(),"物理");
        mHomeWorkBean[3] = new HomeWorkBean("a4","生物","小学五年级","上学期",4,1,new Date().getTime(),"生物");
        mHomeWorkBean[4] = new HomeWorkBean("a5","英语","小学六年级","下学期",5,1,new Date().getTime(),"英语");
        mHomeWorkBean[5] = new HomeWorkBean("a6","化学","初中一年级","上学期",2,5,new Date().getTime(),"化学");
        mHomeWorkBean[6] = new HomeWorkBean("a7","语文","小学一年级","下学期",1,5,new Date().getTime(),"语文");
        mHomeWorkBean[7] = new HomeWorkBean("a8","语文","高中二年级","上学期",2,3,new Date().getTime(),"语文");
        mHomeWorkBean[8] = new HomeWorkBean("a9","数学","高中三年级","下学期",3,2,new Date().getTime(),"数学");
        mHomeWorkBean[9] = new HomeWorkBean("a10","英语","初中三年级","上学期",3,1,new Date().getTime(),"英语");
        SqlUtil.saveAll(mHomeWorkBean);
        //初始化subject
        subject[] mSubject = new subject[10];
        mSubject[0] = new subject("语文");
        mSubject[1] = new subject("数学");
        mSubject[2] = new subject("英语");
        mSubject[3] = new subject("物理");
        mSubject[4] = new subject("化学");
        mSubject[5] = new subject("生物");
        mSubject[6] = new subject("政治");
        mSubject[7] = new subject("历史");
        mSubject[8] = new subject("地理");
        mSubject[9] = new subject("计算机");
        SqlUtil.saveSub(mSubject);



    }

}
