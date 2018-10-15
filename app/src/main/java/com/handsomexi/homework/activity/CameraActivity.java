package com.handsomexi.homework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;
import com.handsomexi.homework.R;
import com.handsomexi.homework.util.ImageUtil;
import com.handsomexi.homework.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class CameraActivity extends SwipeBackActivity {

    @BindView(R.id.camera_cameraView)
    CameraView cameraView;
    @BindView(R.id.camera_flash)
    ImageView imageView;

    Disposable disposable;

    @OnClick({R.id.camera_album,R.id.camera_flash,R.id.camera_paizhao})
    void onClick(View view){
        switch(view.getId()){
            case R.id.camera_album:{
                //打开相册选择图片
                ImageUtil.selectFormSystem(this);
                break;
            }
            case R.id.camera_flash:{
                int stats = cameraView.getFlash();
                if(stats == CameraView.FLASH_TORCH) {//关闭闪光灯
                    cameraView.setFlash(CameraView.FLASH_OFF);
                    imageView.setImageResource(R.mipmap.flash);
                }
                else {//开启闪光灯
                    cameraView.setFlash(CameraView.FLASH_TORCH);
                    imageView.setImageResource(R.mipmap.flash_on);
                }
                break;
            }
            case R.id.camera_paizhao:{
                cameraView.takePicture();//拍照
                break;
            }
            case R.id.camera_cameraView:{
                cameraView.setAutoFocus(true);
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarAlpha(this);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        cameraView.addCallback(new CameraView.Callback() {
            @Override
            public void onCameraOpened(CameraView cameraView) {
                super.onCameraOpened(cameraView);
                cameraView.setAspectRatio(AspectRatio.of(1920,1080));//设置图片尺寸
            }

            @Override
            public void onCameraClosed(CameraView cameraView) {
                super.onCameraClosed(cameraView);
            }

            @Override
            public void onPictureTaken(CameraView cameraView, byte[] data) {//在这里保存拍到的图片
                super.onPictureTaken(cameraView, data);
                cameraView.setFlash(CameraView.FLASH_OFF);
                String filePath = Util.getNewPicFile().getPath();
                boolean isSuccess = FileIOUtils.writeFileFromBytesByStream(filePath,data);
                if(isSuccess){
                    setResult(1,new Intent().putExtra("data",filePath));
                }else {
                    setResult(0);
                }
                finish();
            }
        });
       // initCheckFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            data.putExtra("data","uri");
            setResult(1,data);
        }else {
            setResult(0);
        }
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
        cameraView.setAutoFocus(false);
        cameraView.setAutoFocus(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.stop();
        if(disposable!=null)
            disposable.dispose();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraView.stop();
    }

    void initCheckFocus(){
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            while (true){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    Log.e("camera",e.toString());
                }
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        cameraView.setAutoFocus(false);
                        cameraView.setAutoFocus(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
