package com.zhch.example.java.string;

public class SomeUnicode {

    /**
     * 字符串转换unicode
     */
    public static String toUnicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    public static String toString(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    public void test() {
        // "-"后面的空格和":"后面的空格看起来一样，但unicode不一样，导致yaml解析不正确，很蛋疼
        String a = "-　title: mama";
        String uni = toUnicode(a);
        String ori = toString(uni);
        System.out.println("unicode:" + uni);
        System.out.println("string:" + ori);
    }

    public static void main(String[] args) {
        SomeUnicode t = new SomeUnicode();
        t.test();
    }


}
