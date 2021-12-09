import core.JitAdd;
import core.JitCommit;
import core.JitInit;
import utils.Utils;
import utils.ZLibUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SwingJit {

    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("JIT- 你不需要使用命令行");
        frame.setSize(600, 370);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel,frame);

        // 设置界面可见
        frame.setVisible(true);
    }

    public static void placeComponents(JPanel panel,JFrame f) {

        panel.setLayout(null);


        JTextField dirPath = new JTextField(20);
        dirPath.setBounds(160,40,250,40);
        panel.add(dirPath);



        JButton chooseDir = new JButton("选择文件夹");
        chooseDir.setFont(new Font("黑体",Font.BOLD,16));
        chooseDir.setBounds(20, 40, 120, 40);
        panel.add(chooseDir);
        //chooseDir.setActionCommand("dir");
        chooseDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(f);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    try {
                        dirPath.setText(file.getCanonicalPath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        ////////////////////////////////////////////////JIT----INIT//////////////////////////////////////////////////
        final JLabel label = new JLabel();
        label.setBounds(163,40,100,100);
        label.setForeground(Color.blue);
        label.setFont(new Font("黑体",Font.BOLD,16));
        JButton initButton = new JButton("git init");
        initButton.setFont(new Font("黑体",Font.BOLD,16));
        initButton.setBounds(430, 40, 120, 40);
        initButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dirpath = dirPath.getText();
                Utils.setWorkDir(dirpath);
                try {
                    JitInit.init(dirpath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                label.setText("仓库创建成功");
            }
        });
        panel.add(label);
        panel.add(initButton);

        JTextField filePath = new JTextField(20);
        filePath.setBounds(160,110,250,40);
        panel.add(filePath);


        JButton chooseFile = new JButton("选择文件");
        chooseFile.setFont(new Font("黑体",Font.BOLD,16));
        chooseFile.setBounds(20, 110, 120, 40);
        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int option = fileChooser.showOpenDialog(f);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    try {
                        filePath.setText(file.getCanonicalPath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.add(chooseFile);

        ////////////////////////////////////////////////JIT----ADD//////////////////////////////////////////////////

        final JLabel label2 = new JLabel();
        label2.setBounds(163,110,150,100);
        label2.setForeground(Color.blue);
        label2.setFont(new Font("黑体",Font.BOLD,16));
        JButton addButton = new JButton("git add");
        addButton.setFont(new Font("黑体",Font.BOLD,16));
        addButton.setBounds(430, 110, 120, 40);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filepath = filePath.getText();
                try {
                    JitAdd.add(new File(filepath));
                    label2.setText("文件已添加到暂存区");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    label2.setForeground(Color.RED);
                    label2.setText("错误！文件不存在");
                }

            }
        });
        panel.add(label2);
        panel.add(addButton);

        JButton commitButton = new JButton("git commit");
        commitButton.setFont(new Font("黑体",Font.BOLD,16));
        commitButton.setBounds(20, 180, 530, 40);
        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JitCommit jitcommit = new JitCommit();
                try {
                    jitcommit.commit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(commitButton);

        final JLabel label3 = new JLabel();
        label3.setBounds(20,220,100,100);
        label3.setText("KEY");
        panel.add(label3);

        JTextField key = new JTextField(20);
        key.setBounds(60,250,350,40);
        panel.add(key);

        final JLabel label4 = new JLabel();
        label4.setBounds(60,250,700,200);
        label4.setFont(new Font("黑体",Font.BOLD,18));

        JButton value = new JButton("VALUE");
        value.setFont(new Font("黑体",Font.BOLD,16));
        value.setBounds(430, 250, 120, 40);
        value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyText = key.getText();
                System.out.println(Utils.getObjectsPath());
                File file = new File(Utils.getObjectsPath() + File.separator + keyText);
                FileInputStream is = null;
                try {
                    is = new FileInputStream(file);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                byte[] output = ZLibUtils.decompress(is);
                //label4.setText(new String(output));
                String str = new String(output);
                str = str.replaceAll("\n","<br/>");
                str = "<html><body>"+str+"</body></html>";
                System.out.println(str);
                label4.setText(str);
            }
        });

        panel.add(label4);
        panel.add(value);
    }

}