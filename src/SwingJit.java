import core.*;
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


    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        // 创建 JFrame 实例
        JFrame frame = new JFrame("JIT- 你不需要使用命令行");
        frame.setSize(1230, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel,frame);

        // 设置界面可见
        frame.setVisible(true);
    }

    public static void placeComponents(JPanel panel,JFrame f) {
        int dep = 70;
        panel.setLayout(null);
////////////////////////////////////////////////JIT----LOAD//////////////////////////////////////////////////
        JTextField dirPath1 = new JTextField(20);
        dirPath1.setBounds(160,40,250,40);
        panel.add(dirPath1);
        JButton chooseDir1 = new JButton("选择文件夹");
        chooseDir1.setFont(new Font("黑体",Font.BOLD,16));
        chooseDir1.setBounds(20, 40, 120, 40);
        panel.add(chooseDir1);
        //chooseDir.setActionCommand("dir");
        chooseDir1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(f);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    try {
                        dirPath1.setText(file.getCanonicalPath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        final JLabel label1 = new JLabel();
        label1.setBounds(163,40,120,100);
        label1.setForeground(Color.blue);
        label1.setFont(new Font("黑体",Font.BOLD,16));
        JButton initButton1 = new JButton("load");
        initButton1.setFont(new Font("黑体",Font.BOLD,16));
        initButton1.setBounds(430, 40, 120, 40);
        initButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dirpath = dirPath1.getText();
                Utils.setWorkDir(dirpath);
                /*try {
                    JitInit.init(dirpath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }*/
                label1.setText("目录加载完成");
            }
        });
        panel.add(label1);
        panel.add(initButton1);
////////////////////////////////////////////////JIT----INIT//////////////////////////////////////////////////
        JTextField dirPath = new JTextField(20);
        dirPath.setBounds(160,40+dep,250,40);
        panel.add(dirPath);



        JButton chooseDir = new JButton("选择文件夹");
        chooseDir.setFont(new Font("黑体",Font.BOLD,16));
        chooseDir.setBounds(20, 40+dep, 120, 40);
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

        final JLabel label = new JLabel();
        label.setBounds(163,40+dep,130,100);
        label.setForeground(Color.blue);
        label.setFont(new Font("黑体",Font.BOLD,16));
        JButton initButton = new JButton("init");
        initButton.setFont(new Font("黑体",Font.BOLD,16));
        initButton.setBounds(430, 40+dep, 120, 40);
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

        ////////////////////////////////////////////////JIT----ADD//////////////////////////////////////////////////

        JTextField filePath = new JTextField(20);
        filePath.setBounds(160,110+dep,250,40);
        panel.add(filePath);
        JButton chooseFile = new JButton("选择文件");
        chooseFile.setFont(new Font("黑体",Font.BOLD,16));
        chooseFile.setBounds(20, 110+dep, 120, 40);
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



        final JLabel label2 = new JLabel();
        label2.setBounds(163,110+dep,300,100);
        label2.setForeground(Color.blue);
        label2.setFont(new Font("黑体",Font.BOLD,16));
        JButton addButton = new JButton("add");
        addButton.setFont(new Font("黑体",Font.BOLD,16));
        addButton.setBounds(430, 110+dep, 120, 40);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filepath = filePath.getText();
                String path = filepath.trim();
                String filename = path.substring(path.lastIndexOf("\\")+1);
                try {
                    JitAdd.add(new File(filepath));
                    label2.setForeground(Color.BLUE);
                    label2.setText("文件" + filename + "已添加到暂存区");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    label2.setForeground(Color.RED);
                    label2.setText("错误！文件不存在");
                }

            }
        });
        panel.add(label2);
        panel.add(addButton);

        ////////////////////////////////////////////////JIT----COMMIT//////////////////////////////////////////////////

        final JLabel labelss = new JLabel();
        labelss.setBounds(163,110+dep*2,300,100);
        labelss.setForeground(Color.blue);
        labelss.setFont(new Font("黑体",Font.BOLD,16));

        final JLabel labelMessage = new JLabel();
        labelMessage.setBounds(20,150+dep,100,100);
        labelMessage.setText("MESSAGE");
        panel.add(labelMessage);

        JTextField messageCont = new JTextField(20);
        messageCont.setBounds(100,180+dep,310,40);
        panel.add(messageCont);


        JButton commitButton = new JButton("commit");
        commitButton.setFont(new Font("黑体",Font.BOLD,16));
        //commitButton.setBounds(20, 180, 530, 40);
        commitButton.setBounds(430, 180+dep, 120, 40);
        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageCont.getText();
                JitCommit jitcommit = new JitCommit();
                try {
                    //传入提交信息字符串
                    jitcommit.commit(message);
                    labelss.setForeground(Color.BLUE);
                    labelss.setText("提交成功");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    labelss.setForeground(Color.RED);
                    labelss.setText("提交失败，请检查！");
                }
            }
        });
        panel.add(labelss);
        panel.add(commitButton);

        final JLabel label3 = new JLabel();
        label3.setBounds(650,10,100,100);
        label3.setText("KEY");
        panel.add(label3);

        JTextField key = new JTextField(20);
        key.setBounds(700,40,350,40);
        panel.add(key);

        final JLabel label4 = new JLabel();
        label4.setBounds(700,100,700,800);
        label4.setFont(new Font("黑体",Font.BOLD,18));
        label4.setVerticalAlignment(SwingConstants.TOP);
        //label4.setText("<html><body>"+"qwfqwfqwfqwf\nwqf\nwfwf"+"</body></html>");
        JButton value = new JButton("cat file");
        value.setFont(new Font("黑体",Font.BOLD,16));
        value.setBounds(1070, 40, 120, 40);
        value.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyText = key.getText();
                //System.out.println(Utils.getObjectsPath());
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
                //System.out.println(str);
                label4.setText(str);
                try {
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(label4);
        panel.add(value);

        ////////////////////////////////////////////////JIT----BRANCH//////////////////////////////////////////////////
        final JLabel labelbr = new JLabel();
        labelbr.setBounds(163,110+dep*3,300,100);
        labelbr.setForeground(Color.blue);
        labelbr.setFont(new Font("黑体",Font.BOLD,16));


        final JLabel labelMessage11 = new JLabel();
        labelMessage11.setBounds(20,150+dep*2,100,100);
        labelMessage11.setText("Branch");
        panel.add(labelMessage11);

        JTextField messageCont11 = new JTextField(20);
        messageCont11.setBounds(100,180+dep*2,310,40);
        panel.add(messageCont11);

        JButton commitButton11 = new JButton("branch");
        commitButton11.setFont(new Font("黑体",Font.BOLD,16));
        //commitButton.setBounds(20, 180, 530, 40);
        commitButton11.setBounds(430, 180+dep*2, 120, 40);
        commitButton11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageCont11.getText();
                try {
                    JitBranch.createBranchWithMaster(message);
                    labelbr.setForeground(Color.BLUE);
                    labelbr.setText("分支"+message+"创建成功");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    labelbr.setForeground(Color.RED);
                    labelbr.setText("创建分支失败，请重试");
                }
            }
        });
        panel.add(commitButton11);
        panel.add(labelbr);
        //panel.add(commitButton22);
        /////////////////////////////////////////////////////////
        JButton commitButton3 = new JButton("list");
        commitButton3.setFont(new Font("黑体",Font.BOLD,16));
        //commitButton.setBounds(20, 180, 530, 40);
        commitButton3.setBounds(20, 180+dep*3, 160, 40);
        commitButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = null;
                //JitCommit jitcommit = new JitCommit();
                try {
                    //传入提交信息字符串
                    message = JitBranch.branch();
                    message = message.replaceAll("\n","<br/>");
                    message = "<html><body>"+message+"</body></html>";
                    //System.out.println(str);
                    label4.setText(message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(commitButton3);
        /////////////////////////////////////////////////////////
        JButton commitButtondel = new JButton("delete");
        commitButtondel.setFont(new Font("黑体",Font.BOLD,16));
        //commitButton.setBounds(20, 180, 530, 40);
        commitButtondel.setBounds(200, 180+dep*3, 160, 40);
        commitButtondel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageCont11.getText();
                //JitCommit jitcommit = new JitCommit();
                try {
                    //传入提交信息字符串
                    JitBranch.deleteBranch(message);
                    labelbr.setForeground(Color.BLUE);
                    labelbr.setText("分支"+message+"删除成功");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    labelbr.setForeground(Color.RED);
                    labelbr.setText("分支删除失败，请重试");
                }
            }
        });
        panel.add(commitButtondel);
        /////////////////////////////////////////////////////////
        JButton commitButtoncheck = new JButton("checkout");
        commitButtoncheck.setFont(new Font("黑体",Font.BOLD,16));
        //commitButton.setBounds(20, 180, 530, 40);
        commitButtoncheck.setBounds(380, 180+dep*3, 170, 40);
        commitButtoncheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageCont11.getText();
                //JitCommit jitcommit = new JitCommit();

                try {
                    JitCheckout.checkout(message);
                    labelbr.setForeground(Color.BLUE);
                    labelbr.setText("已切换到"+message+"分支");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    labelbr.setForeground(Color.RED);
                    labelbr.setText("分支切换失败，请重试");
                }
            }
        });
        panel.add(commitButtoncheck);
        /////////////////////////////////////////////////////////
        JButton commitButtonindex = new JButton("index");
        commitButtonindex.setFont(new Font("黑体",Font.BOLD,16));
        //commitButton.setBounds(20, 180, 530, 40);
        commitButtonindex.setBounds(20, 180+dep*4, 160, 40);
        commitButtonindex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = null;
                try {
                    message = JitOpr.JitStage();
                    message = message.replaceAll("\n","<br/>");
                    message = "<html><body>"+message+"</body></html>";
                    //System.out.println(str);
                    label4.setText(message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(commitButtonindex);
        /////////////////////////////////////////////////////////
        JButton resbutton = new JButton("restore");
        resbutton.setFont(new Font("黑体",Font.BOLD,16));
        //commitButton.setBounds(20, 180, 530, 40);
        resbutton.setBounds(200, 180+dep*4, 160, 40);
        resbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JitRestore.restore();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(resbutton);
        /////////////////////////////////////////////////////////
        JButton logbutton = new JButton("log");
        logbutton.setFont(new Font("黑体",Font.BOLD,16));
        //commitButton.setBounds(20, 180, 530, 40);
        logbutton.setBounds(380, 180+dep*4, 170, 40);
        logbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = null;
                try {
                    message = JitLog.printLog();
                    message = message.replaceAll("\n","<br/>");
                    message = "<html><body>"+message+"</body></html>";
                    //System.out.println(str);
                    label4.setText(message);
                    //JitLog.printLog();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(logbutton);





        JButton skin = new JButton("一键换皮肤");
        skin.setFont(new Font("黑体",Font.BOLD,16));
        skin.setBounds(1070, 400+dep, 120, 40);
        skin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String dirpath = dirPath.getText();
                int random=(int)(Math.random()*4);
                String[] arr = {"javax.swing.plaf.metal.MetalLookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel","com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel",
                "com.sun.java.swing.plaf.motif.MotifLookAndFeel"};
                try {
                    System.out.println(random);
                    UIManager.setLookAndFeel(arr[random]);
                    //System.out.println(UIManager.getLookAndFeel());
                    SwingUtilities.updateComponentTreeUI(f);
                } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(skin);
    }

}