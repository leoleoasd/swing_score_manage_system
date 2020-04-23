package ui;

import model.Course;
import model.Student;
import store.Store;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class StudentDetailFrame extends JFrame{

    Store store;
    MainFrame parent;
    Student student;
    ScoresTable scoresTable;

    public StudentDetailFrame(MainFrame parent, Store store, Student student){
        super("学生成绩管理");
        this.parent = parent;
        this.store = store;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel l_id = new JLabel("ID");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        panel.add(l_id, c);
        JFormattedTextField f_id = new JFormattedTextField(NumberFormat.getIntegerInstance());
        f_id.setEditable(false);
        f_id.setValue(student.getID());
        f_id.setColumns(20);
        c.gridx = 1;
        panel.add(f_id, c);

        JLabel l_name = new JLabel("姓名");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        panel.add(l_name, c);
        JTextField f_name = new JTextField(20);
        f_name.setEditable(false);
        f_name.setText(student.getName());
        c.gridx = 1;
        panel.add(f_name, c);

        JLabel l_major = new JLabel("专业");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        panel.add(l_major, c);
        JTextField f_major = new JTextField(20);
        f_major.setEditable(false);
        f_major.setText(student.getMajor());
        c.gridx = 1;
        panel.add(f_major, c);

        JLabel l_birth = new JLabel("生日");
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        panel.add(l_birth, c);
        JFormattedTextField f_birth = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        f_birth.setEditable(false);
        f_birth.setValue(student.getBirth());
        f_birth.setColumns(20);
        c.gridx = 1;
        panel.add(f_birth, c);

        scoresTable = new ScoresTable(store, student, this);
        JScrollPane pane = new JScrollPane(scoresTable);
        c.gridx=0;
        c.gridy=4;
        c.weightx = 0;
        c.gridwidth = 2;
        panel.add(pane, c);


        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        super.getContentPane().add(panel);
        super.pack();
        super.setVisible(true);
        super.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                parent.triggerDataChanged();
                parent.setEnabled(true);
                StudentDetailFrame.super.dispose();
            }
        });
    }

    public void triggerDataChanged(){
        ((AbstractTableModel)scoresTable.getModel()).fireTableDataChanged();
    }
}
