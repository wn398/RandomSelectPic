package com.tim.wang;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Malcolm Wang on 2016/7/13.
 * Copyright 2016 Malcolm Inc.
 * ALL RIGHTS RESERVED.
 */
public class PhotoLabel extends JLabel {
    private File file;
    private final ImageIcon icon;
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

            int labelWidth = this.getWidth();
            int iconHeight = icon.getIconHeight();
            int iconWidth =icon.getIconWidth();
            int transferWidth = this.getSize().height*iconWidth/iconHeight;
            g.drawImage(icon.getImage(), (labelWidth-transferWidth)/2, 0, transferWidth, this.getSize().height, this);
        }
    }

    public void setFile(File file) {
        this.file = file;
    }
}

