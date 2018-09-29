package com.handsomexi.homework.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.handsomexi.homework.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SaveSubjectActivity extends SwipeBackActivity {
    @BindView(R.id.savesub_img)
    ImageView img;
    @BindView(R.id.savesub_spinner)
    Spinner spinner;
    @BindView(R.id.savesub_edit)
    EditText editText;
    @BindView(R.id.savesub_flow1)
    TagFlowLayout flowview;


    private List<String> flowArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savesub);
        ButterKnife.bind(this);
        setFlowview();
    }
    void setFlowview(){
        initArray();
        TagAdapter<String> adapter = new TagAdapter<String>(flowArray) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.layout_flow,flowview,false);
                textView.setText(s);
                return textView;
            }
        };
        flowview.setAdapter(adapter);

    }
    void initArray(){
        flowArray = Arrays.asList(getResources().getStringArray(R.array.all_subject));
    }
}
