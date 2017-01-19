package com.example.four;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 * Created by taoshuang on 2017/1/19.
 */
public class MyDemo {
    public static final String TAG = "MyDemo";

    public static void main(String args[]) {

        String filePath = "F:\\box_mp3";
        //openFile(filePath);
        //openFile2(filePath);
        openFile3(filePath);

    }

    private static void openFile(String filePath) {
        File file = new File(filePath);
        System.out.println("file exists:" + file.exists());
        if (!file.exists()) return;
        Desktop desktop = Desktop.getDesktop();
        try {
            System.out.println("desktop");
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用windows系统函数
     * 注：这里/c是指定的，因为系统是装在c盘在
     */
    private static void openFile2(String filePath) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用windows系统函数
     * 注：这里/c是指定的，因为系统是装在c盘在
     */
    private static void openFile3(String filePath) {
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "", filePath});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
