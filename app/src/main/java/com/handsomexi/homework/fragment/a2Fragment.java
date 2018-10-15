package com.handsomexi.homework.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.handsomexi.homework.R;
import com.handsomexi.homework.adapter.ContentAdapter;
import com.handsomexi.homework.adapter.SubjectAdapter;
import com.handsomexi.homework.bean.HomeWorkBean;
import com.handsomexi.homework.bean.subject;
import com.handsomexi.homework.util.DividerUtil;
import com.handsomexi.homework.util.SqlUtil;
import com.handsomexi.homework.view.IndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class a2Fragment extends Fragment {

    private IndicatorView indicatorView;

    private SubjectAdapter mSubjectAdpter;
    private ContentAdapter mContentAdapter;

    private List<subject> mSubjectList = new ArrayList<>();
    private List<HomeWorkBean> mContentList = new ArrayList<>();

    @BindView(R.id.subject_recycle__list)
    RecyclerView subjectRecyclerView;
    @BindView(R.id.content_recycler_list)
    RecyclerView contentRecyclerView;


    Unbinder unbinder;

    public a2Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a2, null);
        unbinder = ButterKnife.bind(this,view);
        initBean();
        showSubject(mSubjectList);
        //选择默认的科目进行显示
        if (mSubjectList != null && mSubjectList.size() != 0){
            subject defaultSubject = mSubjectList.get(0);
            requestContentList(defaultSubject);
        }

        //showContent(mContentList);
        return view;
    }

    private void showContent(List<HomeWorkBean> contentList) {

    }


    private void initBean() {
        //从数据库中提取出数据，分别初始化mSubjectList，homeWorkBeanList
        List<HomeWorkBean> homeWorkBeanList = SqlUtil.query("全部", "全部", "全部");

        mSubjectList = SqlUtil.getSub();

    }

    private void showSubject(List<subject> subjectList) {

        mSubjectAdpter = new SubjectAdapter(getContext(),subjectList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        mSubjectAdpter.setOnBinding(() -> {
            View child = manager.findViewByPosition(0);
            if (child != null){
                indicatorView.openAnimator(child);
            }
        });

        mSubjectAdpter.setOnItemClickListener((view, position) -> {
            //indicatorView.openAnimator(view);
            subject subject = mSubjectAdpter.getItem(position);

            requestContentList(subject);
            ToastUtils.showShort("点击了 "+ mSubjectAdpter.getItem(position));
        });
        subjectRecyclerView.setAdapter(mSubjectAdpter);
        subjectRecyclerView.setLayoutManager(manager);

    }

    private void requestContentList(subject subject) {
        //筛选出contentList
        mContentList = SqlUtil.queryOnSubject(subject.getSubject());



        //开始显示ContentRecyclerView
        if (mContentAdapter == null){
            mContentAdapter = new ContentAdapter(getContext(),mContentList);
            contentRecyclerView.setAdapter(mContentAdapter);
            contentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            contentRecyclerView.addItemDecoration(new DividerUtil());                      //添加分割线

        } else {
            mContentAdapter.clear();
            mContentAdapter.addData(mContentList);
        }

    }
}
