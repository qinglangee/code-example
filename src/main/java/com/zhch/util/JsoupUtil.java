package com.zhch.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsoupUtil {
    
    private static Logger LOG = LoggerFactory.getLogger(JsoupUtil.class);

    /**
     * 返回目录元素的文本
     * @param ele
     * @param selector
     * @return
     */
    public static String selText(Element ele, String selector) {
        if (ele == null || selector == null) {
            return null;
        }

        Elements subEles = ele.select(selector);
        return subEles.size() > 0 ? subEles.get(0).text() : null;
    }
    
    /**
     * 返回目标元素的属性值
     * @param ele
     * @param selector
     * @return
     */
    public static String selAttr(Element ele, String selector, String attr) {
        if (ele == null || selector == null) {
            return null;
        }
        
        Elements subEles = ele.select(selector);
        return subEles.size() > 0 ? subEles.get(0).attr(attr) : null;
    }
    /**
     * 在root元素中根据　listSelector 选出一组元素，再在每个元素中根据fieldSelector生成一个map
     * @param root 根元素
     * @param listSelector　对象列表的选择器
     * @param fieldSelector　每个对象属性的选择器
     * @return
     */
    public static List<Map<String, String>> parseInfoList(Element root, String listSelector, Map<String, String> fieldSelector){
        List<Map<String, String>> infoList = new ArrayList<>();
        Elements infoEles = root.select(listSelector);
        for(Element infoEle : infoEles){
            Map<String, String> info = new HashMap<>();
            for(Map.Entry<String, String> e : fieldSelector.entrySet()){
                //  .class div lu li@[text]　形式，　@前是选择器，　@后是要取的部分, [text] 表示取text, 其它则取属性
                String[] strs = e.getValue().split("@");
                if("[text]".equals(strs[1])){
                    info.put(e.getKey(), selText(infoEle, strs[0]));
                }else{
                    info.put(e.getKey(), selAttr(infoEle, strs[0], strs[1]));
                }
            }
            infoList.add(info);
        }
        return infoList;
    }
    /**
     * 在root元素中根据　listSelector 选出一组元素，再在每个元素中根据fieldSelector生成一个对象
     * @param root
     * @param listSelector
     * @param fieldSelector
     * @param classT
     * @return
     */
    public static <T> List<T> parseObjectList(Element root, String listSelector, Map<String, String> fieldSelector, Class<T> classT){
        List<Map<String, String>> infoList = parseInfoList(root, listSelector, fieldSelector);
        List<T> tList = new ArrayList<>();
        for(Map<String, String> info : infoList){
            try {
                T t = classT.newInstance();
                Field[] fields = classT.getDeclaredFields();
                for(Field field : fields){
                    String value = info.get(field.getName());
                    if(value == null){
                        continue;
                    }
                    Method method = ClassUtil.findSetter(classT, field);
                    method.invoke(t, value);
                }
                tList.add(t);
            } catch (Exception e) {
                LOG.error("组装对象出错．", e);
            }
        }
        return tList;
    }

}

