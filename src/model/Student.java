package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Student implements Serializable {
    protected int ID;
    protected String name;
    protected String major;
    protected Date birth;
    protected ArrayList<Score> scores;

    public Date getBirth() {
        return birth;
    }

    public int getID() {
        return ID;
    }

    public String getMajor() {
        return major;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public Student(int ID, String name, String major, Date birth){
        this.ID = ID;
        this.name = name;
        this.major = major;
        this.birth = birth;
        this.scores = new ArrayList<Score>();
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", major='" + major + '\'' +
                ", birth=" + birth +
                ", scores=" + scores +
                '}';
    }
}
