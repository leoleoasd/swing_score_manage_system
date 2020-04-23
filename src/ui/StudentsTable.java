package ui;

import store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentsTable extends JTable {
    Store store;

    public StudentsTable(Store store, MainFrame fr){
        super();
        this.store = store;
        super.setModel(store.getStudentTableModel());
        super.setDragEnabled(false);
        super.setShowGrid(true);


        super.setAlignmentX(LEFT_ALIGNMENT);
        StudentsTable students = this;
        super.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    final int row = students.rowAtPoint(me.getPoint());
                    System.out.println("row:"+row);
                    if(row!=-1 && row < store.getStudents().size()){
                        final int col = students.columnAtPoint(me.getPoint());

                        final JPopupMenu popup = new JPopupMenu();
                        JMenuItem create = new JMenuItem("新建学生");
                        create.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                AddStudentFrame f = new AddStudentFrame(fr, store);
                                fr.setEnabled(false);
                            }
                        });
                        popup.add(create);
                        popup.add(new JSeparator());
                        JMenuItem edit = new JMenuItem("编辑学生");
                        edit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (store.getCourses().size() > 0) {
                                    StudentDetailFrame f = new StudentDetailFrame(fr, store, store.getStudents().get(row));
                                    fr.setEnabled(false);
                                } else {
                                    JOptionPane.showMessageDialog(students, "请先到课程管理添加课程!");
                                }
                            }
                        });
                        popup.add(edit);
                        JMenuItem remove = new JMenuItem("删除学生");
                        remove.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Object[] options = {
                                        "取消",
                                        "确认",
                                };
                                int n = JOptionPane.showOptionDialog(fr,
                                        "是否确定删除学生" + store.getStudents().get(row).getName() + "?",
                                        "提示",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,options,options[0]);
                                if(n == 1){
                                    store.getStudents().remove(row);
                                    fr.triggerDataChanged();
                                }
                            }
                        });
                        popup.add(remove);
                        popup.show(me.getComponent(), me.getX(), me.getY());
                    } else {
                        final JPopupMenu popup = new JPopupMenu();
                        JMenuItem create = new JMenuItem("新建学生");
                        create.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                AddStudentFrame f = new AddStudentFrame(fr, store);
                                fr.setEnabled(false);
                            }
                        });
                        popup.add(create);
                        popup.show(me.getComponent(), me.getX(), me.getY());
                    }
                }
            }
        });
    }

    public boolean getScrollableTracksViewportHeight() {
        Container parent = SwingUtilities.getUnwrappedParent(this);
        // Let the Table fill the JScrollPane.
        return getPreferredSize().height < parent.getHeight();
    }
}
