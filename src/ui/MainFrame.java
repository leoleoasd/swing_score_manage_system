package ui;

import model.Course;
import model.Student;
import store.Store;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainFrame extends JFrame{

    StudentsTable students;
    CoursesTable courses;
    AllScoreTable scores;
    Store store;

    public MainFrame(){
        super("成绩管理系统");
        store = new Store();
        store.setStudents(new ArrayList<Student>(Arrays.asList(
                new Student(1, "a", "计算机科学", new Date()),
                new Student(2, "b", "计算机科学", new Date()),
                new Student(3, "c", "计算机科学", new Date()),
                new Student(4, "d", "计算机科学", new Date()),
                new Student(5, "e", "计算机科学", new Date())
        )));
        store.setCourses(new ArrayList<Course>(
                Arrays.asList(
                        new Course("语文","语文老师"),
                        new Course("数学","数学老师"),
                        new Course("英语","英语老师")
                )
        ));
        students = new StudentsTable(store, this);
        courses = new CoursesTable(store, this);
        scores = new AllScoreTable(store, this);

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane pane_stu = new JScrollPane(students);
        pane_stu.setAlignmentX(LEFT_ALIGNMENT);
        pane_stu.setBorder(BorderFactory.createTitledBorder("学生信息管理"));
        pane_stu.setPreferredSize(new Dimension(800,400));

        JScrollPane pane_sco = new JScrollPane(scores);
        pane_sco.setAlignmentX(LEFT_ALIGNMENT);
        pane_sco.setBorder(BorderFactory.createTitledBorder("学生成绩查看"));
        pane_sco.setPreferredSize(new Dimension(800,400));

        JScrollPane pane_cor = new JScrollPane(courses);
        pane_cor.setAlignmentX(LEFT_ALIGNMENT);
        pane_cor.setBorder(BorderFactory.createTitledBorder("课程信息管理"));
        pane_cor.setPreferredSize(new Dimension(800,400));

        JTabbedPane tab = new JTabbedPane();
        tab.addTab("学生管理",pane_stu);
        tab.addTab("成绩管理",pane_sco);
        tab.addTab("课程管理",pane_cor);

        JPanel panel = new JPanel();
        panel.add(tab);

        super.getContentPane().add(panel);
        super.pack();
        super.setVisible(true);
        super.setResizable(false);
    }

    public void triggerDataChanged(){
        ((AbstractTableModel)students.getModel()).fireTableDataChanged();
        ((AbstractTableModel)courses.getModel()).fireTableDataChanged();
        ((AbstractTableModel)scores.getModel()).fireTableDataChanged();
    }
}
