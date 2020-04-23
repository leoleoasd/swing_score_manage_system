package ui;

import model.Student;
import store.Store;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ScoresTable extends JTable {
    Student student;
    Store store;

    public ScoresTable(Store store,Student student, StudentDetailFrame fr){
        super();
        this.student = student;
        this.store = store;
        super.setModel(new ScorseTableModel(student));
        super.setDragEnabled(false);
        super.setShowGrid(true);


        super.setAlignmentX(LEFT_ALIGNMENT);
        ScoresTable students = this;
        super.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    final int row = students.rowAtPoint(me.getPoint());
                    System.out.println("row:"+row);
                    if(row!=-1 && row < student.getScores().size()){
                        final int col = students.columnAtPoint(me.getPoint());

                        final JPopupMenu popup = new JPopupMenu();
                        JMenuItem create = new JMenuItem("添加考试成绩");
                        create.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                AddScoreFrame frame = new AddScoreFrame(fr, store, student);
                                fr.setEnabled(false);
                            }
                        });
                        popup.add(create);
                        JMenuItem remove = new JMenuItem("删除考试成绩");
                        remove.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Object[] options = {
                                        "取消",
                                        "确认",
                                };
                                int n = JOptionPane.showOptionDialog(fr,
                                        "是否确定删除学生" + student.getName() + "的" + student.getScores().get(row).getC().getName() + "成绩?",
                                        "提示",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,options,options[0]);
                                if(n == 1){
                                    student.getScores().remove(row);
                                    fr.triggerDataChanged();
                                }
                            }
                        });
                        popup.add(remove);
                        popup.show(me.getComponent(), me.getX(), me.getY());
                    } else {
                        final JPopupMenu popup = new JPopupMenu();
                        JMenuItem create = new JMenuItem("添加考试成绩");
                        create.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                AddScoreFrame frame = new AddScoreFrame(fr, store, student);
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

    class ScorseTableModel extends AbstractTableModel {
        protected Student student;

        public ScorseTableModel(Student student){
            this.student = student;
        }

        @Override
        public int getRowCount() {
            return student.getScores().size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if(rowIndex < student.getScores().size()){
                switch(columnIndex){
                    case 0:
                        return student.getScores().get(rowIndex).getC().getName();
                    case 1:
                        return student.getScores().get(rowIndex).getC().getTeacher();
                    case 2:
                        return student.getScores().get(rowIndex).getScore();
                    case 3:
                        return new SimpleDateFormat("yyyy-MM-dd").format(student.getScores().get(rowIndex).getDate());
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
                    return "课程名称";
                case 1:
                    return "课程教师";
                case 2:
                    return "考试分数";
                case 3:
                    return "考试时间";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int row, int col){
            return col > 1;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if(rowIndex < student.getScores().size()){
                if(columnIndex < 4){
                    switch(columnIndex){
                        case 2:
                            try {
                                student.getScores().get(rowIndex).setScore(Integer.parseInt((String) aValue));
                            } catch (NumberFormatException e){
                                JOptionPane.showMessageDialog(null, "非法的数字");
                            }
                            break;
                        case 3:
                            try {
                                student.getScores().get(rowIndex).setDate(new SimpleDateFormat("yyyy-MM-dd").parse((String)aValue));
                            } catch (ParseException e) {
                                JOptionPane.showMessageDialog(null, "日期格式为 yyyy-MM-dd!");
                            }
                            break;
                    }
                }
            }
        }
    }
}
