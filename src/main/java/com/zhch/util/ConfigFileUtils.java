package com.zhch.util;

import java.io.File;
import java.net.URL;

public class ConfigFileUtils {

    /**
     * 先查找运行目录，　再classpath,　找不到就返回　null
     * @param filename
     * @return
     */
    public static File searchFile(String filename) {
        File file = null;
        // 首先查找运行目录
        String userDir = System.getProperty("user.dir");
        file = new File(userDir, filename);
        if (file.exists()) {
            return file;
        }

        // 然后查找 classpath
        URL url = ConfigFileUtils.class.getClassLoader().getResource(filename);
        if (url == null) {
            return null;
        }

        file = new File(url.getPath());
        return file.exists() ? file : null;
    }
}
