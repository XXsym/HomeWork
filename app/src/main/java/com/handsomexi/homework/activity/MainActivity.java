package com.handsomexi.homework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.handsomexi.homework.R;
import com.handsomexi.homework.adapter.FragmentAdapter;
import com.handsomexi.homework.fragment.aFragment;
import com.handsomexi.homework.util.ImageUtil;
import com.handsomexi.homework.util.PermissionUtil;
import com.handsomexi.homework.util.Util;
import com.handsomexi.homework.view.BottomRow;
import com.handsomexi.homework.view.MainViewpager;

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
                ImageUtil.openCamera(this);
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

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), new Fragment[]{new aFragment()});
        mainPager.setAdapter(fragmentAdapter);

        boolean isFirstUse = SPUtils.getInstance().getBoolean("isFirstUse", true);
        if (isFirstUse) startActivity(new Intent(this, IntroActivity.class));

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
                    data.setClass(this, CropActivity.class);
                    startActivityForResult(data, 888);
                } else {
                    ToastUtils.showShort("用户取消或者拍照失败"+requestCode);
                }
                break;
            }
            case 888: {
                if (resultCode == 1) {
                }
            }
        }
    }

    void setRow(int i) {
        for (int j = 0; j < 4; j++) {
            if (j == i) rows[j].select();
            else rows[j].unSelected();
        }
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

}
