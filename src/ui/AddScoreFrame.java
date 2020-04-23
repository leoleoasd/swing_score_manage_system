package ui;

import com.sun.tools.javac.Main;
import model.Course;
import model.Score;
import model.Student;
import store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddScoreFrame extends JFrame {
    Store s;
    Student student;
    StudentDetailFrame parent;
    Course selected = null;

    public AddScoreFrame(StudentDetailFrame parent, Store s, Student student){
        super("添加考试成绩");
        this.parent = parent;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        panel.add(new JLabel("成绩"), c);
        JFormattedTextField f_score = new JFormattedTextField(NumberFormat.getIntegerInstance());
        f_score.setColumns(20);
        c.gridx = 1;
        panel.add(f_score, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        panel.add(new JLabel("考试日期"), c);
        JFormattedTextField f_date = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        f_date.setColumns(20);
        c.gridx = 1;
        panel.add(f_date, c);

        String[] course_names = new String[s.getCourses().size()];
        int i = 0;
        for(Course cc: s.getCourses()){
            course_names[i++] = (cc.getName());
        }

        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("课程"), c);

        JComboBox<String> courses_list = new JComboBox<String>(course_names);
        courses_list.setSelectedIndex(0);
        courses_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = s.getCourses().get(courses_list.getSelectedIndex());
            }
        });
        c.gridx = 1;
        panel.add(courses_list, c);

        selected = s.getCourses().get(0);
        JButton submit = new JButton("提交");
        JFrame frame = this;
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (f_score.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "请输入分数",
                            "提交错误",
                            JOptionPane.ERROR_MESSAGE);
                    f_score.grabFocus();
                    return;
                }
                if (f_date.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "请输入考试日期",
                            "提交错误",
                            JOptionPane.ERROR_MESSAGE);
                    f_date.grabFocus();
                    return;
                }
                Score ss = new Score(student, selected, (int)(((Long)f_score.getValue()).longValue()), (Date)f_date.getValue());
                student.getScores().add(ss);
                frame.dispose();
                parent.triggerDataChanged();
                parent.setEnabled(true);

            }
        });
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        panel.add(submit, c);

        super.getContentPane().add(panel);
        super.pack();
        super.setVisible(true);
        System.out.println(getHeight());
        super.setSize(new Dimension(400, 200));
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        super.getRootPane().setDefaultButton(submit);
        super.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(frame, "是否保存?");
                if(i==0){
                    submit.doClick();
                }else if(i == 1){
                    parent.triggerDataChanged();
                    parent.setEnabled(true);
                    frame.dispose();
                }
            }
        });
        super.setResizable(false);
    }
}
