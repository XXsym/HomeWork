package com.handsomexi.homework.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.handsomexi.homework.R;

import java.util.ArrayList;
import java.util.List;

public class FlexboxUtil {
    private FlexboxLayout flexbox;
    private List<String> strings;
    private Context context;

    private List<TextView> textViews = new ArrayList<>();

    private int current = 0;

    public FlexboxUtil(Context context, FlexboxLayout layout, List<String> strings){
        this.flexbox = layout;
        this.strings = strings;
        this.context = context;
        init();
    }
    private void init(){
        for (int i = 0 ; i < strings.size() ; i++){
            TextView textView = new TextView(context);
            int finalI = i;
            textView.setOnClickListener(v -> {
                setFlexbox(finalI);
                current = finalI;
            });
            textView.setText(strings.get(i));
            textView.setTextSize(20);
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_savesub_flexbox));
            textView.setPadding(25,5,25,5);
            textView.setGravity(Gravity.CENTER);
            flexbox.addView(textView);
            textViews.add(textView);
            FlexboxLayout.LayoutParams params = (FlexboxLayout.LayoutParams) textView.getLayoutParams();
            params.setMargins(10,5,10,5);
            textView.setLayoutParams(params);
        }
        textViews.get(0).setBackground(ContextCompat.getDrawable(context,R.drawable.shape_savesub_flexbox_selected));
    }

    private void setFlexbox(int i){
        for (TextView textView:textViews){
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_savesub_flexbox));
        }
        textViews.get(i).setBackground(ContextCompat.getDrawable(context,R.drawable.shape_savesub_flexbox_selected));
    }
    public String getCurrentItem(){
        return strings.get(current);
    }
}
