package ui;

import com.sun.tools.javac.Main;
import model.Course;
import model.Student;
import store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCourseFrame extends JFrame {
    Store s;
    MainFrame parent;
    public AddCourseFrame(MainFrame parent, Store s){
        super("添加课程");
        this.parent = parent;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel l_name = new JLabel("名称");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        panel.add(l_name, c);
        JTextField f_name = new JTextField(20);
        c.gridx = 1;
        panel.add(f_name, c);

        JLabel l_teacher = new JLabel("教师");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        panel.add(l_teacher, c);
        JTextField f_teacher = new JTextField(20);
        c.gridx = 1;
        panel.add(f_teacher, c);

        JButton submit = new JButton("提交");
        JFrame frame = this;
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (f_name.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "请输入姓名",
                            "提交错误",
                            JOptionPane.ERROR_MESSAGE);
                    f_name.grabFocus();
                    return;
                }
                if (f_teacher.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "请输入老师名称",
                            "提交错误",
                            JOptionPane.ERROR_MESSAGE);
                    f_teacher.grabFocus();
                    return;
                }
                parent.triggerDataChanged();
                parent.setEnabled(true);
                frame.dispose();
                s.getCourses().add(new Course(f_name.getText(), f_teacher.getText()));
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
        super.setSize(new Dimension(300, 200));
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        super.getRootPane().setDefaultButton(submit);
        super.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if (f_teacher.getText().length() == 0 && f_name.getText().length() == 0){
                    parent.triggerDataChanged();
                    parent.setEnabled(true);
                    frame.dispose();
                    return;
                }
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
