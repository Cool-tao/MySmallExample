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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by taoshuang on 2017/1/18.
 */
public class TestSingleThreadExecutor {
    public static void main(String[] args) {


        //六年级上
        //https://ra.namibox.com/tina/static/lesson/milesson394/audio/box_0000000415.mp3
        //五年级上
        //https://ra.namibox.com/tina/static/lesson/milesson356/audio/box_0000002373.mp3
        //五年级下
        //https://ra.namibox.com/tina/static/lesson/milesson143/audio/box_0000003621.mp3
        //四年级上 人教版
        //https://ra.namibox.com/tina/static/lesson/milesson350/audio/box_0000000452.mp3
        // 四年级下 人教版
        //https://ra.namibox.com/tina/static/lesson/milesson144/audio/box_0000001802.mp3
        //四年级上 苏教版
        //https://ra.namibox.com/tina/static/lesson/milesson357/audio/box_0000000586.mp3
        // 四年级下 苏教版
        //https://ra.namibox.com/tina/static/lesson/milesson357/audio/box_0000000586.mp3

        final String grade = "milesson144";//年级
        final String savePath = "f:\\box_" + grade + "_mp3";

        //创建一个可重用固定线程数的线程池
//        ExecutorService pool = Executors.newSingleThreadExecutor();
//        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

//        //创建一个可重用固定线程数的线程池
//        ExecutorService pool = Executors.newCachedThreadPool();
        //创建实现了Runnable接口对象
        Thread tt1 = getThread1(grade, savePath);
        Thread tt2 = getThread2(grade, savePath);
        Thread tt3 = getThread3(grade, savePath);
        Thread tt4 = getThread4(grade, savePath);
        Thread tt5 = getThread5(grade, savePath);
        //将线程放入池中并执行
        pool.execute(tt1);
        pool.execute(tt2);
        pool.execute(tt3);
        pool.execute(tt4);
        pool.execute(tt5);

        //关闭
        pool.shutdown();
    }

    private static Thread getThread5(final String grade, final String savePath) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                String url;
                for (int i = 2; i < 3; i++) {
                    for (int j = 0; j < 5; j++) {
                        for (int k = 0; k < 10; k++) {
                            for (int l = 0; l < 10; l++) {
                                for (int m = 0; m < 10; m++) {
                                    url = "https://ra.namibox.com/tina/static/lesson/" + grade + "/audio/box_00000"
                                            + i + ""
                                            + j + ""
                                            + k + ""
                                            + l + ""
                                            + m + ""
                                            + ".mp3";
                                    boolean b = downLoadFromUrl(url, "box_00000" + i + "" + j + "" + k + "" + l + "" + m + "" + ".mp3", savePath);
                                    System.out.println(i + "" + j + "" + k + "" + l + "" + m + ", url:" + url + ", result:" + b);
                                }

                            }
                        }
                    }
                }
            }
        });
    }

    private static Thread getThread4(final String grade, final String savePath) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                String url;
                for (int i = 1; i < 2; i++) {
                    for (int j = 5; j < 10; j++) {
                        for (int k = 0; k < 10; k++) {
                            for (int l = 0; l < 10; l++) {
                                for (int m = 0; m < 10; m++) {
                                    url = "https://ra.namibox.com/tina/static/lesson/" + grade + "/audio/box_00000"
                                            + i + ""
                                            + j + ""
                                            + k + ""
                                            + l + ""
                                            + m + ""
                                            + ".mp3";
                                    boolean b = downLoadFromUrl(url, "box_00000" + i + "" + j + "" + k + "" + l + "" + m + "" + ".mp3", savePath);
                                    System.out.println(i + "" + j + "" + k + "" + l + "" + m + ", url:" + url + ", result:" + b);
                                }

                            }


                        }
                    }
                }
            }
        });
    }

    private static Thread getThread3(final String grade, final String savePath) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                String url;
                for (int i = 1; i < 2; i++) {
                    for (int j = 0; j < 5; j++) {
                        for (int k = 0; k < 10; k++) {
                            for (int l = 0; l < 10; l++) {
                                for (int m = 0; m < 10; m++) {
                                    url = "https://ra.namibox.com/tina/static/lesson/" + grade + "/audio/box_00000"
                                            + i + ""
                                            + j + ""
                                            + k + ""
                                            + l + ""
                                            + m + ""
                                            + ".mp3";
                                    boolean b = downLoadFromUrl(url, "box_00000" + i + "" + j + "" + k + "" + l + "" + m + "" + ".mp3", savePath);
                                    System.out.println(i + "" + j + "" + k + "" + l + "" + m + ", url:" + url + ", result:" + b);
                                }

                            }
                        }
                    }
                }
            }
        });
    }

    private static Thread getThread2(final String grade, final String savePath) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                String url;
                for (int i = 0; i < 1; i++) {
                    for (int j = 5; j < 10; j++) {
                        for (int k = 0; k < 10; k++) {
                            for (int l = 0; l < 10; l++) {
                                for (int m = 0; m < 10; m++) {
                                    url = "https://ra.namibox.com/tina/static/lesson/" + grade + "/audio/box_00000"
                                            + i + ""
                                            + j + ""
                                            + k + ""
                                            + l + ""
                                            + m + ""
                                            + ".mp3";
                                    boolean b = downLoadFromUrl(url, "box_00000" + i + "" + j + "" + k + "" + l + "" + m + "" + ".mp3", savePath);
                                    System.out.println(i + "" + j + "" + k + "" + l + "" + m + ", url:" + url + ", result:" + b);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private static Thread getThread1(final String grade, final String savePath) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                String url;
                for (int i = 0; i < 1; i++) {
                    for (int j = 0; j < 5; j++) {
                        for (int k = 0; k < 10; k++) {
                            for (int l = 0; l < 10; l++) {
                                for (int m = 0; m < 10; m++) {
                                    url = "https://ra.namibox.com/tina/static/lesson/" + grade + "/audio/box_00000"
                                            + i + ""
                                            + j + ""
                                            + k + ""
                                            + l + ""
                                            + m + ""
                                            + ".mp3";
                                    boolean b = downLoadFromUrl(url, "box_00000" + i + "" + j + "" + k + "" + l + "" + m + "" + ".mp3", savePath);
                                    System.out.println(i + "" + j + "" + k + "" + l + "" + m + ", url:" + url + ", result:" + b);
                                }
                            }
                        }
                    }
                }
            }
        });
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

}

