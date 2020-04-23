package model;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {
    protected Student s;
    protected Course c;
    protected Date date;
    protected int score;

    public Score(Student s, Course c, int score, Date date){
        this.s = s;
        this.c = c;
        this.score = score;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    public void setC(Course c) {
        this.c = c;
    }

    public Student getS() {
        return s;
    }

    public Course getC() {
        return c;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setS(Student s) {
        this.s = s;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
