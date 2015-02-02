package com.zhch.example.learn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.zhch.javatool.time.TimeTool;

public class WordTime {
	public void calculateTime() throws IOException {
		String classpath = WordTime.class.getResource("/").getPath();
		File file = new File(classpath + "com/zhch/example/learn/wordtime.properties");
		List<String> lines = FileUtils.readLines(file);
		List<Record> records = new ArrayList<Record>();
		for(String line : lines){
			if(StringUtils.isEmpty(line) || !line.contains("|") || line.startsWith("#")){
				continue;
			}
			String[] fields = line.split("\\|");
			Record r = new Record(fields[0],fields[1],fields[2]);
			records.add(r);
		}
		
		Map<String, List<Record>> map = new HashMap<String, List<Record>>();
		
		int[] reviewTime = new int[]{1,2,4,7,15,20}; // 复习的间隔时间
		
		for(Record r : records){
			Date date = TimeTool.parseDate(r.time, "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			
			for(int day : reviewTime){
				c.add(Calendar.DAY_OF_YEAR, day);
				String reviewDay = TimeTool.formatDate(c.getTime(), "yyyy-MM-dd");
				
				if(map.get(reviewDay) != null){
					map.get(reviewDay).add(r);
				}else{
					List<Record> tasks = new ArrayList<Record>();
					tasks.add(r);
					map.put(reviewDay, tasks);
				}
			}
			
		}
		
		printResult(map);
	}

	/**
	 * 打印结果 
	 * @param map
	 */
	private void printResult(Map<String, List<Record>> map) {
		List<String> keys =new ArrayList<String>(map.keySet()); 
		Collections.sort(keys); // 日期排序
		
		// 计算所有的天数, 打印
		Date first = TimeTool.parseDate(keys.get(0), "yyyy-MM-dd");
		Date last = TimeTool.parseDate(keys.get(keys.size() - 1), "yyyy-MM-dd");
		long days = (last.getTime() - first.getTime())/1000/60/60/24;
		
		Calendar c = Calendar.getInstance();
		c.setTime(first);
		c.add(Calendar.DAY_OF_YEAR, -1);
		
		for(int i=0;i<=days;i++){
			c.add(Calendar.DAY_OF_YEAR, 1);
			
			// 周一就打印个窄
			if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
				System.out.println();
			}
			
			String key = TimeTool.formatDate(c.getTime(), "yyyy-MM-dd");
			StringBuilder sb = new StringBuilder();
			sb.append(key).append("  ");
			
			List<Record> records = map.get(key);
			if(records != null){
				Collections.sort(records);
				for(Record r : records){
					sb.append(r.name).append("(").append(r.comment).append(")   ");
				}
			}
			System.out.println(sb);
		}
	}

	public static void main(String[] args) throws IOException {
		WordTime t = new WordTime();
		t.calculateTime();
	}
}

class Record implements Comparable<Record>{
	public String time;
	public String name;
	public String comment;
	public Record(String t, String n, String c){
		time = t;
		name = n;
		comment = c ;
	}
	@Override
	public int compareTo(Record o) {
		return this.name.compareTo(o.name);
	}
	@Override
	public String toString() {
		return "Record [time=" + time + ", name=" + name + ", comment=" + comment + "]";
	}
}