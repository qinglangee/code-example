package com.zhch.example.snakyaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.zhch.example.java.Classpath;
import com.zhch.util.GsonUtils;

public class LoadYaml {
    File yamlSrcFile;

    LoadYaml() {
        init();
    }

    public void init() {
        URL url = Classpath.class.getClassLoader().getResource("com/zhch/example/snakyaml/yaml_src_file.yml");
        yamlSrcFile = new File(url.getPath());
    }

    // 直接 load 成 Object,　转换成map, list等对象
    public void loadToObject() throws FileNotFoundException {
        System.out.println("loadToObject ===============================");

        Yaml yaml = new Yaml();
        Object load = yaml.load(new FileInputStream(yamlSrcFile));
        System.out.println(load.getClass());
        System.out.println(yaml.dump(load));
    }

    /** load 成指定的 class */
    public void loadByClass() {
        System.out.println("loadByClass ===============================");
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        // 设置不同的options会有不同的显示
        Yaml yaml = new Yaml(options);
//        Yaml yaml = new Yaml();

        InputStreamReader inputStream;
        try {
            inputStream = new InputStreamReader(new FileInputStream(yamlSrcFile));
            OuterConfig config = yaml.loadAs(inputStream, OuterConfig.class);
            System.out.println(GsonUtils.toJson(config));
            System.out.println(yaml.dump(config));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        LoadYaml t = new LoadYaml();
        t.loadByClass();
        t.loadToObject();
    }

}
