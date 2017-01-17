package com.example;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoshuang on 2017/1/17.
 */
public class HttpDemo {

    public static final String TAG = "HttpDemo";

    public static void main(String[] args) {
        final List list = new ArrayList();

//        String url;
//        for (int i = 0; i < 7; i++) {
//            for (int j = 0; j < 10; j++) {
//                url = "https://ra.namibox.com/tina/static/lesson/milesson352/image/namisj1a_00" + i + "" + j + ".png";
//                boolean b = downLoadFromUrl(url, "namisj1a_00" + i + "" + j + ".png", "f:\\a");
//                System.out.println("url:" + url + ", result:" + b);
//                String s = "url:" + url + ", result:" + b;
//                list.add(s);
//            }
//        }
//        downLoadNaMiShox(list);


        //https://ra.namibox.com/tina/static/lesson/milesson352/image/namisj1a_0001.png
        String urlBefore = "https://ra.namibox.com/tina/static/lesson/milesson352/image/namisj1a_0";
        String savePath = "f:\\namisj1a";
        String fileName = "namisj1a_0";
        //https://ra.namibox.com/tina/static/lesson/milesson352/image/nami_sj3a0055.png
        String urlBefore1 = "https://ra.namibox.com/tina/static/lesson/milesson352/image/nami_sj3a0";
        String savePath1 = "f:\\nami_sj3a";
        String fileName1 = "nami_sj3a0";
        //https://ra.namibox.com/tina/static/lesson/milesson352/image/namishox0014.png
        String urlBefore2 = "https://ra.namibox.com/tina/static/lesson/milesson352/image/namishox0";
        String savePath2 = "f:\\namishox";
        String fileName2 = "namishox0";
//        downLoadPng(urlBefore,savePath,fileName,list);


        //https://ra.namibox.com/tina/static/lesson/milesson352/audio/box_0000007205.mp3


        new Thread(new Runnable() {
            @Override
            public void run() {
                String url;

                for (int i = 1; i < 2; i++) {
                    for (int j = 3; j < 5; j++) {
                        for (int k = 0; k < 10; k++) {
                            for (int l = 0; l < 10; l++) {
                                for (int m = 0; m < 10; m++) {
                                    url = "https://ra.namibox.com/tina/static/lesson/milesson352/audio/box_00000"
                                            + i + ""
                                            + j + ""
                                            + k + ""
                                            + l + ""
                                            + m + ""
                                            + ".mp3";
                                    boolean b = downLoadFromUrl(url, "box_00000" + i + "" + j + "" + k + "" + l + "" + m + "" + ".mp3", "f:\\box_mp3");
                                    String s = "url:" + url + ", result:" + b;
                                    list.add(s);
                                    System.out.println(i + "" + j + "" + k + "" + l + "" + m + "" );
                                }

                            }


                        }
                    }
                    System.out.println("i:" + i);
                }
                saveLogToTxt(list, "f:\\box_mp3");
            }
        }).start();
    }


    private static void downLoadPng(final String urlBefore, final String savePath, final String fileName, final List list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url;

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        for (int k = 0; k < 10; k++) {
                            url = urlBefore + i + "" + j + "" + k + ".png";
                            boolean b = downLoadFromUrl(url, fileName + i + "" + j + "" + k + ".png", savePath);
                            String s = "url:" + url + ", result:" + b;
                            list.add(s);
                        }
                    }
                    System.out.println("i:" + i);
                }
                saveLogToTxt(list, savePath);
            }
        }).start();
    }

    private static boolean saveLogToTxt(List list, String savePath) {
        if (list == null || list.size() <= 0) {
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i)).append("\n");
        }
        FileWriter fw;
        File f = new File(savePath + "\\namishox_" + list.size() + ".txt");
        try {
            if (f.exists()) {
                f.delete();
                f.createNewFile();
            } else {
                f.createNewFile();
            }
            fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(stringBuilder.toString(), 0, stringBuilder.length());
            out.newLine();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end");
        return true;
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static boolean downLoadFromUrl(String urlStr, String fileName, String savePath) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return false;
            }
            InputStream inputStream = conn.getInputStream();

            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
//            System.out.println("info:" + url + " download success");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
