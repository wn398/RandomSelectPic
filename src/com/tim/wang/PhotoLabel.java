package com.tim.wang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created by Malcolm on 2016/7/13.
 */
public class PhotoLabel extends JLabel {
    private File file;
    private ImageIcon icon;
    public PhotoLabel(File file){
        this.file = file;
    }
    public PhotoLabel(ImageIcon icon){
        this.icon = icon;
    }

    public void paintComponent(Graphics g) {
        if (file == null&&null == icon) {
            super.paintComponent(g);
        } else {
            ImageIcon icon;
            if(null == file){
                icon = this.icon;
            }else {
                icon = new ImageIcon(file.getAbsolutePath());
            }
            // 图片随窗体大小而变化
            //g.drawImage(icon.getImage(), 0, 0, this.getParent().getSize().width, this.getParent().getSize().height, this.getParent());
            int labelHeight = this.getHeight();
            int labelWidth = this.getWidth();
            int iconHeight = icon.getIconHeight();
            int iconWidth =icon.getIconWidth();
            int iconRatio = iconHeight/iconWidth;
            int tranferWidth = this.getSize().height*iconWidth/iconHeight;
            g.drawImage(icon.getImage(), (labelWidth-tranferWidth)/2, 0, tranferWidth, this.getSize().height, this);
        }
    }

    public static void main(String[] args){
        final JFrame frame = new JFrame("PicuterS");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        PhotoLabel photoLabel = new PhotoLabel(new File("C:\\Users\\Malcolm\\Desktop\\112.png"));

        frame.getContentPane().add(photoLabel);
        frame.getContentPane().setSize(500,600);
        frame.pack();
        frame.setVisible(true);

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
};

