package com.handsomexi.homework.bean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity
public class subject {
    @Id
    String subject;
    @ToMany(referencedJoinProperty = "subjectId")
    private List<HomeWorkBean> homeWorkBeanList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1627099268)
    private transient subjectDao myDao;
    @Generated(hash = 603730669)
    public subject(String subject) {
        this.subject = subject;
    }
    @Generated(hash = 1459175571)
    public subject() {
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 597581016)
    public List<HomeWorkBean> getHomeWorkBeanList() {
        if (homeWorkBeanList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            HomeWorkBeanDao targetDao = daoSession.getHomeWorkBeanDao();
            List<HomeWorkBean> homeWorkBeanListNew = targetDao
                    ._querySubject_HomeWorkBeanList(subject);
            synchronized (this) {
                if (homeWorkBeanList == null) {
                    homeWorkBeanList = homeWorkBeanListNew;
                }
            }
        }
        return homeWorkBeanList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2062015894)
    public synchronized void resetHomeWorkBeanList() {
        homeWorkBeanList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 937984622)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSubjectDao() : null;
    }
}
