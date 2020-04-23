import ui.MainFrame;
import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args){
        System.setProperty("apple.laf.useScreenMenuBar", "true");


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();

                JFrame t = new JFrame();
                t.getContentPane().add(new JTextArea("" +
                        "如何体验本程序(必读)\n" +
                        "0. 按照以下顺序, 体验本程序的所有功能.\n" +
                        "1. 右键学生信息管理中的表格, 添加学生\n" +
                        "2. 在添加学生时在ID和日期栏输入非数字字符\n" +
                        "3. 在设置日期时, 输入2020-1-1, 之后点击别的文本框, 查看日期文本框的变化\n" +
                        "4. 保存添加的学生后回到表格, 双击某个单元格即可编辑.\n" +
                        "5. 在编辑表格时, 测试ID和日期的格式化功能.\n" +
                        "6. 右键学生, 删除学生功能.\n" +
                        "7. 重新添加一个学生.\n" +
                        "8. 在课程管理选项卡中右键添加课程.\n" +
                        "9. 添加课程后右键编辑学生.\n" +
                        "10. 在编辑学生窗口内, 右键成绩表格, 添加考试成绩.\n" +
                        "11. 在成绩管理选项卡, 使用查看所有学生成绩功能.\n" +
                        "12. 在成绩管理选项卡, 双击编辑成绩或学生信息.\n" +
                        "13. 菜单-文件-保存.\n" +
                        "14. 菜单-文件-新建.\n" +
                        "15. 菜单-文件-打开, 打开代码文件夹中提供的示例数据库文件, 体验细节功能.\n" +
                        "细节功能包括但不限于, 添加学生姓名留空, 关闭添加学生窗口, 关闭程序时的保存提示等.\n"
                        ));
                t.setDefaultCloseOperation(t.DO_NOTHING_ON_CLOSE);
                t.pack();
                t.setVisible(true);
                t.setLocation(100,100);
            }
        });
    }
}
