package com.handsomexi.homework.adapter;

import android.content.Context;
import android.widget.TextView;

import com.handsomexi.homework.R;
import com.handsomexi.homework.bean.subject;

import java.util.List;

import butterknife.BindView;

public class SubjectAdapter extends SimpleAdapter<subject> {

    @BindView(R.id.subject_list_title)
    TextView subjectListTitle;

    public SubjectAdapter(Context context, List<subject> subjectList) {
        super(context, R.layout.subject_items, subjectList);

    }


    @Override
    protected void convert(BaseViewHolder viewHoder, subject item) {
//        ButterKnife.bind(this,viewHoder.itemView);

        viewHoder.getTextView(R.id.subject_list_title).setText(item.getSubject());
        //indicatorView.openAnimator(viewHoder.itemView);
    }

    private OnBinding onBinding;

    public void setOnBinding(OnBinding onBinding) {
        this.onBinding = onBinding;
    }

    public interface OnBinding {
        void onBinding();
    }


}
