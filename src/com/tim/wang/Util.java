package com.tim.wang;

import java.io.File;

/**
 * Created by Malcolm Wang on 2016/7/16.
 * Copyright 2016 Malcolm Inc.
 * ALL RIGHTS RESERVED.
 */
public class Util {
    public static String getFileName(File file){
        return file.getName().substring(0, file.getName().indexOf("."));
    }

    public static boolean isPicture(File photo){
        String suffex = photo.getName().substring(photo.getName().indexOf(".")+1);
        if(suffex.equalsIgnoreCase("jpg")||suffex.equalsIgnoreCase("png")||suffex.equalsIgnoreCase("jpeg")||suffex.equalsIgnoreCase("bmp")){
            return true;
        }else{
            return false;
        }
    }
}
