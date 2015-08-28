package com.zhch.example.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Example {

	public static Gson gson = new Gson();
	public static JsonParser parser = new JsonParser();
	
	public void toJson(){
		System.out.println("序列化为字符串=================");
		People p = new People("zhangsan", 12);
		System.out.println(gson.toJson(p));  // {"name":"zhangsan","age":12}
		
		System.out.println("序列化列表=================");
		List<People> pList = new ArrayList<People>();
		pList.add(p);
		People p2 = new People("lisi", 13); 
		pList.add(p2);
		System.out.println(pList);
		System.out.println(gson.toJson(pList));  // [{"name":"zhangsan","age":12},{"name":"lisi","age":13}]
		
		System.out.println("内部嵌套=================");
		p.father = p2;
		System.out.println(gson.toJson(p));
	}
	public void fromJson(){
		System.out.println("反序列化为对象=================");
		String json = "{\"name\":\"zhangsan\",\"age\":12}";
		People p = gson.fromJson(json, People.class);
		System.out.println(p);  // name:zhangsan, age:12
	}
	public void fromJsonArray(){
		System.out.println("解析数组=================");
		String json = "[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":13}]";
		Type type = new TypeToken<List<People>>(){}.getType();
		List<People> list = gson.fromJson(json, type);
		System.out.println(list);  // name:zhangsan, age:12
	}
	
	public void parseJson(){
		System.out.println("没有类型, 直接 json 对象操作=================");
		String json = "[{\"name\":\"zhangsan\",\"age\":12},{\"name\":\"lisi\",\"age\":13}]";
		JsonElement ele = parser.parse(json);
		JsonArray obj = ele.getAsJsonArray();

		String json2 = "{\"name\":\"zhangsan\",\"age\":12}";
		String name = parser.parse(json).getAsJsonObject().get("name").getAsString();
	}
	
	public void parseDefaultValue(){

		System.out.println("反序列化时默认值=================");
		String json = "{}";
		People p = gson.fromJson(json, People.class);
		System.out.println(p);  // {name:null, age:0}
	}
	
	public static void main(String[] args) {
		Example t = new Example();
		t.toJson();
		t.fromJson();
		t.fromJsonArray();
		t.parseDefaultValue();
	}

}

class People{
	public String name;
	public int age;
	public People father;
	public People(){}
	public People(String name, int age){
		this.name = name;
		this.age = age;
	}
	public String toString(){
		return "{name:"+name + ", age:" + age + ", father:" + String.valueOf(father) + "}";
	}
}

