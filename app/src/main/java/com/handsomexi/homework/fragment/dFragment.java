package com.handsomexi.homework.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handsomexi.homework.R;
import com.handsomexi.homework.activity.ChangPasswordActivity;

import java.util.ArrayList;
import java.util.List;

public class dFragment extends Fragment {

    private List<Picture> pictureList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPicture();
        PictureAdapter adapter = new PictureAdapter(getContext(), R.layout.picture_item, pictureList);
        ListView listView =view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if(position==4){
                startActivity(new Intent(getActivity(),ChangPasswordActivity.class));
            }
        });
    }
    private void initPicture(){
        pictureList.add(new Picture("我的会员",R.mipmap.my3));
        pictureList.add(new Picture("年级",R.mipmap.my3));
        pictureList.add(new Picture("姓名",R.mipmap.my3));
        pictureList.add(new Picture("就读地",R.mipmap.my3));
        pictureList.add(new Picture("修改密码",R.mipmap.my3));
        pictureList.add(new Picture("意见反馈",R.mipmap.my3));
    }
    class Picture{
        private String name;
        private int imageId;
        Picture(String name, int imageId){
            this.name = name;
            this.imageId = imageId;
        }
        public String getName(){
            return name;
        }
        int getImageId(){
            return imageId;
        }
    }
    class PictureAdapter extends ArrayAdapter<Picture> {
        private int resourceId;
        PictureAdapter(Context context, int textViewResourceId, List<Picture> objects){
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parene){
            Picture picture = getItem(position);
            ViewHolder holder;
            if (view == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
                holder = new ViewHolder();
                holder.pictureImage = view.findViewById(R.id.picture_image);
                holder.pictureName = view.findViewById(R.id.picture_name);
                view.setTag(holder);    //将ViewHolder存储在Viewz中
            }else {
                holder = (ViewHolder) view.getTag(); //重新获取ViewHolder
            }
            holder.pictureImage.setImageResource(picture.getImageId());
            holder.pictureName.setText(picture.getName());
            return view;
        }

        class ViewHolder{
            ImageView pictureImage;
            TextView pictureName;
        }
    }
}
