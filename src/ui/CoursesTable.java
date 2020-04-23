package ui;

import store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CoursesTable extends JTable {
    Store store;

    public CoursesTable(Store store, MainFrame fr){
        super();
        this.store = store;
        super.setModel(store.getCourseTableModel());
        super.setDragEnabled(false);
        super.setShowGrid(true);


        super.setAlignmentX(LEFT_ALIGNMENT);
        CoursesTable Courses = this;
        super.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    final int row = Courses.rowAtPoint(me.getPoint());
                    System.out.println("row:"+row);
                    if(row!=-1){
                        final int col = Courses.columnAtPoint(me.getPoint());

                        final JPopupMenu popup = new JPopupMenu();
                        JMenuItem create = new JMenuItem("新建课程");
                        create.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                AddCourseFrame f = new AddCourseFrame(fr, store);
                                fr.setEnabled(false);
                            }
                        });
                        popup.add(create);
                        popup.add(new JSeparator());
                        JMenuItem remove = new JMenuItem("删除课程");
                        remove.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Object[] options = {
                                        "取消",
                                        "确认",
                                };
                                int n = JOptionPane.showOptionDialog(fr,
                                        "是否确定删除课程" + store.getCourses().get(row).getName() + "?",
                                        "提示",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,options,options[0]);
                                if(n == 1){
                                    store.getCourses().remove(row);
                                    fr.triggerDataChanged();
                                }
                            }
                        });
                        popup.add(remove);
                        popup.show(me.getComponent(), me.getX(), me.getY());
                    } else {
                        final JPopupMenu popup = new JPopupMenu();
                        JMenuItem create = new JMenuItem("新建课程");
                        create.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                AddCourseFrame f = new AddCourseFrame(fr, store);
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
