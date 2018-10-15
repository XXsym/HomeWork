package com.handsomexi.homework.adapter;

import android.content.Context;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.handsomexi.homework.R;
import com.handsomexi.homework.bean.HomeWorkBean;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ContentAdapter extends SimpleAdapter<HomeWorkBean> {

      @BindView(R.id.review_rating)
       RatingBar reViewRating;
      @BindView(R.id.degree_of_difficult)
      RatingBar difficultRating;



    public ContentAdapter(Context context,List<HomeWorkBean> contentList){
       super(context, R.layout.content_items,contentList);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, HomeWorkBean item) {
        ButterKnife.bind(this,viewHoder.itemView);
        setRating(reViewRating,item.getReviews());
        setRating(difficultRating ,item.getDifficulty());
        Glide.with(context).asBitmap().thumbnail(0.2f).load(new File(item.getImagePath())).into(viewHoder.getImageView(R.id.content_picture));
        viewHoder.getTextView(R.id.time).setText("时间："+ Long.toString(item.getTime()));
        viewHoder.getTextView(R.id.grade).setText("年级：" + item.getSchoolYear());
        viewHoder.getTextView(R.id.content_subject).setText("科目：" + item.getSubject());
        viewHoder.getTextView(R.id.reviews).setText("已经复习" + item.getReviews() + "次");

    }
    //设置星星控件
    private void setRating(RatingBar rating, int number){
        rating.setRating(number);
        rating.setOnRatingBarChangeListener((ratingBar, rating1, fromUser) -> {
        });

        }
    }


