package com.example.first;

public class MyClass {

    public static final void main(String args[]) {

        System.out.println("--------Start---------");

        System.out.println("Android Studio练习Java! 中文乱码");
        System.out.println(
                "\n //在新版gradle中，需要在build中添加" +
                        "\n tasks.withType(JavaCompile){ " +
                        "\n\toptions.encoding = \"UTF-8\"" +
                        "\n}" +
                        "\n //在旧版gradle中，需要在build中添加" +
                        "\n tasks.withType(Compile){ " +
                        "\n\toptions.encoding = \"UTF-8\"" +
                        "\n}");

        System.out.println("The Second Java Demo:写两个Demo直接新建Edit...新建一个。");


        String str = "aaaBBBccc";
        System.out.println("大小写转换：" + str.toLowerCase() + "\t" + str.toUpperCase());

        System.out.println("-------END--------");

    }
}
