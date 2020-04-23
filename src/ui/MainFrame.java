package ui;

import model.Course;
import model.Student;
import store.Store;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
        store.setStudents(new ArrayList<Student>());
        store.setCourses(new ArrayList<Course>());
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


        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        //Build the first menu.
        JMenu menu = new JMenu("文件");
        menuBar.add(menu);

        JMenuItem create = new JMenuItem("新建");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(JOptionPane.showConfirmDialog(MainFrame.this, "将会丢失所有未保存的更改, 是否继续?") == 0){
                    store.setStudents(new ArrayList<Student>());
                    store.setCourses(new ArrayList<Course>());
                }
            }
        });
        menu.add(create);
        menu.add(new JSeparator());

        JMenuItem open = new JMenuItem("打开");
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Database files","dat"));
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//Create a file chooser
                if(JOptionPane.showConfirmDialog(MainFrame.this, "将会丢失所有未保存的更改, 是否继续?") == 0){
                    int ret = fc.showOpenDialog(MainFrame.this);
                    if(ret == JFileChooser.APPROVE_OPTION){
                        try {
                            ObjectInputStream r = new ObjectInputStream(new FileInputStream(fc.getSelectedFile()));
                            Store n = (Store) r.readObject();
                            store.setCourses(n.getCourses());
                            store.setStudents(n.getStudents());
                            triggerDataChanged();
                            JOptionPane.showMessageDialog(null, "读取成功!");
                        } catch (IOException | ClassNotFoundException exception) {
                            exception.printStackTrace();
                            JOptionPane.showMessageDialog(null, "读取成功:"+exception.getMessage());
                        }
                    }
                }

            }
        });
        menu.add(open);
        JMenuItem save = new JMenuItem("保存");
        final JFileChooser fc2 = new JFileChooser();
        fc2.setFileFilter(new FileNameExtensionFilter("Database files","dat"));
        fc2.setSelectedFile(new File("data.dat"));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ret = fc2.showSaveDialog(MainFrame.this);
                if(ret == JFileChooser.APPROVE_OPTION){
                    System.out.println("Saving to "+fc2.getSelectedFile());
                    try {
                        ObjectOutputStream w = new ObjectOutputStream(new FileOutputStream(fc2.getSelectedFile()));
                        w.writeObject(store);
                        JOptionPane.showMessageDialog(null, "保存成功!");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        JOptionPane.showMessageDialog(null, "保存失败:"+ioException.getMessage());
                    }
                }
            }
        });
        menu.add(save);
        menu.add(new JSeparator());
        JMenuItem exit = new JMenuItem("退出");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(MainFrame.this, "是否保存") == 0){
                    save.doClick();
                }
                System.exit(0);
            }
        });
        menu.add(exit);

        super.getContentPane().add(panel);
        super.pack();
        super.setVisible(true);
        super.setResizable(false);
        super.setJMenuBar(menuBar);
        super.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                exit.doClick();
            }
        });
        super.setResizable(false);
    }

    public void triggerDataChanged(){
        ((AbstractTableModel)students.getModel()).fireTableDataChanged();
        ((AbstractTableModel)courses.getModel()).fireTableDataChanged();
        ((AbstractTableModel)scores.getModel()).fireTableDataChanged();
    }
}
