package com.tim.wang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Created by Malcolm on 2016/7/13.
 */
public class Scoller {
    public static final String RANDOM = "随机";
    public static final String STOP = "停止";
    public static final String PHOTONAME = "图片名字";
    private  File dirFile;
    private  List<Group> groups = new ArrayList<Group>();
    private  Group selectedGroup;
    private  Group allPersonGroup = new Group("全部成员");
    private  ImageIcon initIcon;

    private  JPanel downPanel ;
    private  JLabel picLable;
    private  JPanel panel;
    private  JFrame jframe;
    private  JComboBox<Group> jComboBox;
    private  JButton jButton;
    private  JLabel nameLabel;
    private  Boolean flag = true;
    private  JButton resetButton;

    private  JScrollPane rightPanel;
    private  JTextArea jTextArea;

    public  void initComponent(){

        downPanel = new JPanel();
        initIcon = new ImageIcon(this.getClass().getResource("init.jpg"));

        picLable = new PhotoLabel(initIcon);

        jframe = new JFrame("图片随机抽取器");
        jComboBox = new JComboBox<Group>();
        jButton = new JButton(RANDOM);
        nameLabel = new JLabel(PHOTONAME);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setForeground(Color.BLUE);
        resetButton = new JButton("重置");
        resetButton.setEnabled(false);

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("文件");
        final JMenuItem item1 = new JMenuItem("选择文件夹");
        jframe.setJMenuBar(mb);
        mb.add(m1);
        m1.add(item1);



        downPanel.setLayout(new GridLayout(1,4));
        downPanel.add(jComboBox);
        downPanel.add(nameLabel);
        downPanel.add(jButton);
        downPanel.add(resetButton);

        rightPanel = new JScrollPane();

        jTextArea = new JTextArea();

        jTextArea.setForeground(Color.blue);
        jTextArea.setEditable(false);
        rightPanel.setViewportView(jTextArea);
        rightPanel.setBorder(BorderFactory.createTitledBorder("已选图片名称"));

        jframe.setLayout(new BorderLayout());
        jframe.getContentPane().add(picLable,BorderLayout.CENTER);

        jframe.getContentPane().add(downPanel,BorderLayout.SOUTH);
        jframe.getContentPane().add(rightPanel,BorderLayout.EAST);


        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(new Dimension(500,600));
        jframe.setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(jButton.getText().equals(STOP)){
                    jButton.setText(RANDOM);
                    resetButton.setEnabled(true);
                    flag = false;
                    return;
                }else if(jButton.getText().equals(RANDOM)){
                    if(dirFile==null){
                        JOptionPane.showMessageDialog(picLable,"没有文件，请选择文件夹");
                        return;
                    }
                    jButton.setText(STOP);
                    resetButton.setEnabled(false);
                    flag = true;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(selectedGroup==null) {
                            selectedGroup = allPersonGroup;
                        }
                        Person selectedPerson=null;
                        while(flag){
                            if(selectedGroup.getPersons().size()>1){
                                int index = new Random().nextInt(selectedGroup.getPersons().size());
                                selectedPerson = selectedGroup.getPersons().get(index);
                                ((PhotoLabel) picLable).setFile(selectedPerson.getPhoto());
                                picLable.validate();
                                picLable.repaint();
                                nameLabel.setText(selectedPerson.getName());
                                nameLabel.repaint();

                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }else if (selectedGroup.getPersons().size()==1){
                                selectedPerson = selectedGroup.getPersons().get(0);
                                ((PhotoLabel) picLable).setFile(selectedPerson.getPhoto());
                                picLable.validate();
                                picLable.repaint();
                                nameLabel.setText(selectedPerson.getName());
                                nameLabel.repaint();
                                JOptionPane.showMessageDialog(picLable,"组内只有1个人了");
                                flag = false;
                            }else
                                {
                                ((PhotoLabel) picLable).setIcon(initIcon);
                                picLable.validate();
                                picLable.repaint();
                                nameLabel.setText("相片名字");
                                nameLabel.repaint();
                                JOptionPane.showMessageDialog(picLable,"组内已经没有人了,请增加");
                                    jButton.setText(RANDOM);
                                    resetButton.setEnabled(true);
                                flag = false;
                            };
                        }

                        if(selectedGroup.getPersons().size()>0) {
                            jTextArea.setText(jTextArea.getText()+" \n "+selectedPerson.getName());
                            jTextArea.validate();
                            jTextArea.repaint();
                            picLable.validate();
                            picLable.repaint();
                            selectedGroup.getPersons().remove(selectedPerson);
                        }

                    }
                }).start();
            }
        });

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(item1);
                dirFile = chooser.getSelectedFile();
                loadData(dirFile);
            }
        });

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGroup =(Group)jComboBox.getSelectedItem();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.setText(null);

                nameLabel.setText(PHOTONAME);
                ((PhotoLabel)picLable).setFile(null);
                ((PhotoLabel)picLable).setIcon(initIcon);
                loadData(dirFile);
                selectedGroup = (Group) jComboBox.getSelectedItem();

            }
        });
    }
    public static void main (String[] args){
        Scoller scoller = new Scoller();
        scoller.initComponent();
    }

    public  void loadData(File dirFile){
        if(null == dirFile){
            return;
        }
        File[] groupFile = dirFile.listFiles();
        groups.clear();
        allPersonGroup.getPersons().clear();
        groups.add(allPersonGroup);
        for(File file:groupFile){
            if(file.isDirectory()){
                Group group = new Group(file.getName());
                File[] photos = file.listFiles();
                for(File photo:photos){
                    if(Util.isPicture(photo)) {
                        Person person = new Person(Util.getFileName(photo), photo);
                        group.getPersons().add(person);
                        allPersonGroup.getPersons().add(person);
                    }
                }
                if(group.getPersons().size()>0) {
                    groups.add(group);
                }
            }else {
                if(Util.isPicture(file)){
                    Person person = new Person(Util.getFileName(file),file);
                    allPersonGroup.getPersons().add(person);
                }
            }
        }
        jComboBox.removeAllItems();
        for(Group group:groups){
            jComboBox.addItem(group);
//            System.out.println("目标名："+group.getName());
//            for(Person person:group.getPersons()){
//                System.out.println("************"+person.getName());
//            }
        }
    }
}
