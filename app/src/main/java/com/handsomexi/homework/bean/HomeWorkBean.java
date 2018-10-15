package com.handsomexi.homework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class HomeWorkBean {
    @Id
    String ImagePath;
    String Subject;
    String SchoolYear;
    String Semester;
    int difficulty;
    int reviews;
    long time;
    String subjectId;
    @Generated(hash = 2070571880)
    public HomeWorkBean(String ImagePath, String Subject, String SchoolYear,
            String Semester, int difficulty, int reviews, long time,
            String subjectId) {
        this.ImagePath = ImagePath;
        this.Subject = Subject;
        this.SchoolYear = SchoolYear;
        this.Semester = Semester;
        this.difficulty = difficulty;
        this.reviews = reviews;
        this.time = time;
        this.subjectId = subjectId;
    }
    @Generated(hash = 293342445)
    public HomeWorkBean() {
    }
    public String getImagePath() {
        return this.ImagePath;
    }
    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }
    public String getSubject() {
        return this.Subject;
    }
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }
    public String getSchoolYear() {
        return this.SchoolYear;
    }
    public void setSchoolYear(String SchoolYear) {
        this.SchoolYear = SchoolYear;
    }
    public String getSemester() {
        return this.Semester;
    }
    public void setSemester(String Semester) {
        this.Semester = Semester;
    }
    public int getDifficulty() {
        return this.difficulty;
    }
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public int getReviews() {
        return this.reviews;
    }
    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getSubjectId() {
        return this.subjectId;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
