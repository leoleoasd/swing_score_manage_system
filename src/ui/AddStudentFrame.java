package ui;

import com.sun.tools.javac.Main;
import model.Student;
import store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddStudentFrame extends JFrame {
    Store s;
    MainFrame parent;
    public AddStudentFrame(MainFrame parent, Store s){
        super("添加学生");
        this.parent = parent;

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel l_id = new JLabel("ID");
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        panel.add(l_id, c);
        JFormattedTextField f_id = new JFormattedTextField(NumberFormat.getIntegerInstance());
        f_id.setColumns(20);
        c.gridx = 1;
        panel.add(f_id, c);

        JLabel l_name = new JLabel("姓名");
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        panel.add(l_name, c);
        JTextField f_name = new JTextField(20);
        c.gridx = 1;
        panel.add(f_name, c);

        JLabel l_major = new JLabel("专业");
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        panel.add(l_major, c);
        JTextField f_major = new JTextField(20);
        c.gridx = 1;
        panel.add(f_major, c);

        JLabel l_birth = new JLabel("生日");
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0;
        panel.add(l_birth, c);
        JFormattedTextField f_birth = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        f_birth.setColumns(20);
        c.gridx = 1;
        panel.add(f_birth, c);

        JButton submit = new JButton("提交");
        JFrame frame = this;
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked!");
                if (f_id.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "请输入ID",
                            "提交错误",
                            JOptionPane.ERROR_MESSAGE);
                    f_id.grabFocus();
                    return;
                }
                if (f_name.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "请输入姓名",
                            "提交错误",
                            JOptionPane.ERROR_MESSAGE);
                    f_name.grabFocus();
                    return;
                }
                if (f_birth.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "请输入生日",
                            "提交错误",
                            JOptionPane.ERROR_MESSAGE);
                    f_birth.grabFocus();
                    return;
                }
                if (f_major.getText().length() == 0){
                    JOptionPane.showMessageDialog(frame,
                            "请输入专业",
                            "提交错误",
                            JOptionPane.ERROR_MESSAGE);
                    f_major.grabFocus();
                    return;
                }
                Student stu = new Student((int) ((Long)f_id.getValue()).longValue(), f_name.getText(), f_major.getText(), (Date)f_birth.getValue());
                System.out.println(stu);
                s.getStudents().add(stu);
                parent.triggerDataChanged();
                parent.setEnabled(true);
                frame.dispose();
            }
        });
        c.gridx = 0;
        c.gridy = 4;
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
                if (f_birth.getText().length() == 0 && f_id.getText().length() == 0 && f_major.getText().length() == 0 && f_name.getText().length() == 0){
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
