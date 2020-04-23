package store;

import model.Course;
import model.Score;
import model.Student;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Store implements Serializable {
    protected ArrayList<Student> students;
    protected ArrayList<Course> courses;

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public StudentTableModel getStudentTableModel(){
        return new StudentTableModel();
    }

    public CourseTableModel getCourseTableModel(){
        return new CourseTableModel();
    }

    public ScoresTableModel getScoresTableModel() {
        return new ScoresTableModel();
    }

    class StudentTableModel extends AbstractTableModel {
        @Override
        public int getRowCount() {
            return students.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if(rowIndex < students.size()){
                switch(columnIndex){
                    case 0:
                        return students.get(rowIndex).getID();
                    case 1:
                        return students.get(rowIndex).getName();
                    case 2:
                        return students.get(rowIndex).getMajor();
                    case 3:
                        return new SimpleDateFormat("yyyy-MM-dd").format(students.get(rowIndex).getBirth());
                }
                return null;
            }else{
                return null;
            }
        }

        @Override
        public String getColumnName(int col){
            switch(col){
                case 0:
                    return "ID";
                case 1:
                    return "姓名";
                case 2:
                    return "专业";
                case 3:
                    return "生日";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col){
            return true;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if(rowIndex < students.size()){
                if(columnIndex < 4){
                    switch(columnIndex){
                        case 0:
                            try {
                                students.get(rowIndex).setID(Integer.parseInt((String) aValue));
                            } catch (NumberFormatException e){
                                JOptionPane.showMessageDialog(null, "非法的数字!");
                            }
                            break;
                        case 1:
                            students.get(rowIndex).setName((String) aValue);
                            break;
                        case 2:
                            students.get(rowIndex).setMajor((String) aValue);
                            break;
                        case 3:
                            try {
                                students.get(rowIndex).setBirth(new SimpleDateFormat("yyyy-MM-dd").parse((String)aValue));
                            } catch (ParseException e) {
                                JOptionPane.showMessageDialog(null, "日期格式为 yyyy-MM-dd!");
                            }
                            break;
                    }
                }
                //fireTableCellUpdated(rowIndex,columnIndex);
            }
        }
    }

    class CourseTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return courses.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if(rowIndex < courses.size()){
                switch(columnIndex){
                    case 0:
                        return courses.get(rowIndex).getName();
                    case 1:
                        return courses.get(rowIndex).getTeacher();
                }
                return null;
            }else{
                return null;
            }
        }

        @Override
        public String getColumnName(int col){
            switch(col){
                case 0:
                    return "名称";
                case 1:
                    return "教师";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col){
            return true;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if(rowIndex < courses.size()){
                if(columnIndex < 4){
                    switch(columnIndex){
                        case 0:
                            courses.get(rowIndex).setName((String) aValue);
                            break;
                        case 1:
                            courses.get(rowIndex).setTeacher((String) aValue);
                            break;
                    }
                }
            }
        }
    }

    class ScoresTableModel extends AbstractTableModel {
        Student getStudentFromRow(int row){
            for(Student s:students){
                if(row < s.getScores().size()){
                    return s;
                }else{
                    row -= s.getScores().size();
                }
            }
            return null;
        }

        Score getScoreFromRow(int row){
            for(Student s:students){
                if(row < s.getScores().size()){
                    return s.getScores().get(row);
                }else{
                    row -= s.getScores().size();
                }
            }
            return null;
        }

        @Override
        public int getRowCount() {
            int ret = 0;
            for(Student s:students){
                ret += s.getScores().size();
            }
            return ret;
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if(rowIndex < students.size()){
                switch(columnIndex){
                    case 0:
                        return getStudentFromRow(rowIndex).getID();
                    case 1:
                        return getStudentFromRow(rowIndex).getName();
                    case 2:
                        return getStudentFromRow(rowIndex).getMajor();
                    case 3:
                        return new SimpleDateFormat("yyyy-MM-dd").format(getStudentFromRow(rowIndex).getBirth());
                    case 4:
                        return getScoreFromRow(rowIndex).getC().getName();
                    case 5:
                        return getScoreFromRow(rowIndex).getC().getTeacher();
                    case 6:
                        return getScoreFromRow(rowIndex).getScore();
                    case 7:
                        return new SimpleDateFormat("yyyy-MM-dd").format(getScoreFromRow(rowIndex).getDate());
                }
                return null;
            }else{
                return null;
            }
        }

        @Override
        public String getColumnName(int col){
            switch(col){
                case 0:
                    return "ID";
                case 1:
                    return "姓名";
                case 2:
                    return "专业";
                case 3:
                    return "生日";
                case 4:
                    return "课程";
                case 5:
                    return "教师";
                case 6:
                    return "成绩";
                case 7:
                    return "考试日期";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col){
            return true;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if(rowIndex < students.size()){
                if(columnIndex < 7){
                    switch(columnIndex){
                        case 0:
                            try {
                                getStudentFromRow(rowIndex).setID(Integer.parseInt((String) aValue));
                            } catch (NumberFormatException e){
                                JOptionPane.showMessageDialog(null, "非法的数字!");
                            }
                            break;
                        case 1:
                            getStudentFromRow(rowIndex).setName((String) aValue);
                            break;
                        case 2:
                            getStudentFromRow(rowIndex).setMajor((String) aValue);
                            break;
                        case 3:
                            try {
                                getStudentFromRow(rowIndex).setBirth(new SimpleDateFormat("yyyy-MM-dd").parse((String)aValue));
                            } catch (ParseException e) {
                                JOptionPane.showMessageDialog(null, "日期格式为 yyyy-MM-dd!");
                            }
                            break;
                        case 4:
                            getScoreFromRow(rowIndex).getC().setName((String) aValue);
                            break;
                        case 5:
                            getScoreFromRow(rowIndex).getC().setTeacher((String) aValue);
                            break;
                        case 6:
                            try {
                                getScoreFromRow(rowIndex).setScore(Integer.parseInt((String) aValue));
                            } catch (NumberFormatException e){
                                JOptionPane.showMessageDialog(null, "非法的数字!");
                            }
                            break;
                        case 7:
                            try {
                                getScoreFromRow(rowIndex).setDate(new SimpleDateFormat("yyyy-MM-dd").parse((String)aValue));
                            } catch (ParseException e) {
                                JOptionPane.showMessageDialog(null, "日期格式为 yyyy-MM-dd!");
                            }
                            break;
                    }
                }
                fireTableDataChanged();
            }
        }
    }
}
