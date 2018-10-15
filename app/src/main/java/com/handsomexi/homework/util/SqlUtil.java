package com.handsomexi.homework.util;

import com.handsomexi.homework.Myapp;
import com.handsomexi.homework.bean.HomeWorkBean;
import com.handsomexi.homework.bean.HomeWorkBeanDao;
import com.handsomexi.homework.bean.subject;
import com.handsomexi.homework.bean.subjectDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: 2018/10/7 得添加一些防止数据重复添加的措施
public class SqlUtil {
    private static HomeWorkBeanDao getDao(){
        return Myapp.getInstances().getDaoSession().getHomeWorkBeanDao();
    }
    public static subjectDao getSubDao(){
        return Myapp.getInstances().getDaoSession().getSubjectDao();
    }

    public static void saveSub(subject[] subjects ){
        getSubDao().insertInTx(subjects);
    }
    public static void save(String ImagePath, String Subject, String SchoolYear, String Semester,int difficulty,int reviews,String subjectId){
        HomeWorkBean bean = new HomeWorkBean( ImagePath,  Subject, SchoolYear, Semester, difficulty , reviews,new Date().getTime(),subjectId);
        getDao().insert(bean);
    }

    public static void saveAll(HomeWorkBean[] homeWorkBeans){
        //if ()
        getDao().insertInTx(homeWorkBeans);

    }
    public static List<subject> getSub(){
        QueryBuilder<subject> queryBuilder = getSubDao().queryBuilder();
        return getSubDao().loadAll();
    }

    public static List<HomeWorkBean> queryOnSubject(String subject){
        QueryBuilder<HomeWorkBean> builder = getDao().queryBuilder();

        if (subject != null){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(subject)).list();
        } else {
            //
        }
        return new ArrayList<>();
    }
    public static void delete(String ImagePath){
        getDao().deleteByKey(ImagePath);
    }
    public static void delete(HomeWorkBean bean){
        getDao().delete(bean);
    }
    public static void delete(List<HomeWorkBean> beans){
        getDao().deleteInTx(beans);
    }
    public static void deleteAll(){
        getDao().deleteAll();
    }
    public static List<HomeWorkBean> query(String Subject, String SchoolYear, String Semester){
        QueryBuilder<HomeWorkBean> builder = getDao().queryBuilder();

        if(Subject.equals("全部")&&SchoolYear.equals("全部")&&Semester.equals("全部")){
            return getDao().loadAll();
        }
        if(Subject.equals("全部")&&SchoolYear.equals("全部")&&!Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Semester.eq(Semester)).list();
        }
        if(Subject.equals("全部")&&!SchoolYear.equals("全部")&&Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.SchoolYear.eq(SchoolYear)).list();
        }
        if(Subject.equals("全部")&&!SchoolYear.equals("全部")&&!Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.SchoolYear.eq(SchoolYear), HomeWorkBeanDao.Properties.Semester.eq(Semester)).list();
        }


        if(!Subject.equals("全部")&&SchoolYear.equals("全部")&&Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(Subject)).list();
        }
        if(!Subject.equals("全部")&&SchoolYear.equals("全部")&&!Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(Subject), HomeWorkBeanDao.Properties.Semester.eq(Semester)).list();
        }
        if(!Subject.equals("全部")&&!SchoolYear.equals("全部")&&Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(Subject), HomeWorkBeanDao.Properties.SchoolYear.eq(SchoolYear)).list();
        }
        if(!Subject.equals("全部")&&!SchoolYear.equals("全部")&&!Semester.equals("全部")){
            return builder.where(HomeWorkBeanDao.Properties.Subject.eq(Subject), HomeWorkBeanDao.Properties.Semester.eq(Semester), HomeWorkBeanDao.Properties.SchoolYear.eq(SchoolYear)).list();
        }

        return new ArrayList<>();
    }


}
