package com.zhch.example.captcha.zhilian;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class MergeBoxFile {

	File realGifDir = new File("d:\\temp\\d3\\tesseract\\gif\\");
	File goodGifDir = new File("d:\\temp\\d3\\tesseract\\backup\\setup71gif\\gif\\");
	File realBoxFile = new File("d:\\temp\\d3\\tesseract\\zhi.zhilian.exp1.box");
	File goodBoxFile = new File("d:\\temp\\d3\\tesseract\\backup\\setup71gif\\zhi.zhilian.exp1.box");
	File fakeBoxFile = new File("d:\\temp\\d3\\tesseract\\fake.box");

	File destFile = new File("d:\\temp\\d3\\tesseract\\aazhi.zhilian.exp1.box");
	Map<Integer, Box> map = new HashMap<>();

	public void mergeBoxFile() throws IOException {
		// 读入产生的数据
		addReadBox();

		System.out.println("real 0:" + map.get(0));
		System.out.println("real 1:" + map.get(1));
		// 把以前正确的替换进来
		replaceGoodBox();
		System.out.println("good 0:" + map.get(0));
		System.out.println("good 1:" + map.get(1));

		// 为空的替换上占位符
		addFakeBox();
		System.out.println("fake 0:" + map.get(0));
		System.out.println("fake 1:" + map.get(1));

		// print resuolt
		List<Integer> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys);
		List<String> result = new ArrayList<>();
		int count = 0;
		for (Integer key : keys) {

			result.addAll(map.get(key).lines);
			if (count < 2) {
				System.out.println(result);
				count++;
			}
		}
		System.out.println("result 0:" + map.get(0));

		FileUtils.writeLines(destFile, result);

	}

	private void replaceGoodBox() throws IOException {
		List<String> goodLines = FileUtils.readLines(goodBoxFile);
		Map<Integer, Box> goodMap = addBoxes(goodLines);
		Map<Integer, String> goodIndexs = readIndexFile(goodGifDir);
		Map<String, Integer> realIndexs = readFileIndex(realGifDir);

		for (Entry<Integer, Box> e : goodMap.entrySet()) {
			String name = goodIndexs.get(e.getKey());
			Integer realIndex = realIndexs.get(name);
			e.getValue().setIndex(realIndex);
			map.put(realIndex, e.getValue());
		}

	}

	Map<String, Integer> readFileIndex(File dir) {
		File[] files = dir.listFiles();
		Map<String, Integer> map = new HashMap<>();
		int index = 0;
		for (File file : files) {
			map.put(file.getName(), index);
			index++;
		}
		return map;
	}

	Map<Integer, String> readIndexFile(File dir) {
		File[] files = dir.listFiles();
		Map<Integer, String> map = new HashMap<>();
		int index = 0;
		for (File file : files) {
			map.put(index, file.getName());
			index++;
		}
		return map;
	}

	private void addFakeBox() throws IOException {
		List<String> fakeLines = FileUtils.readLines(fakeBoxFile);
		for(String line : fakeLines){
			int index = Box.getIndex(line);
			if(index < 0){
				continue;
			}
			Box  box = map.get(index);
			if (box == null) {
				box = new Box(line, false);
				map.put(box.getIndex(), box);
			} else if (box.isReal) {
				if (box.count != 4) {
					box = new Box(line, false);
					map.put(box.getIndex(), box);
				} else {
					continue;
				}
			} else {
				box.append(line);
			}
		}
	}

	private void addReadBox() throws IOException {
		List<String> realLines = FileUtils.readLines(realBoxFile);
		this.map = addBoxes(realLines);
	}

	Map<Integer, Box> addBoxes(List<String> lines) {
		Map<Integer, Box> map = new HashMap<>();
		for (String line : lines) {
			int index = Box.getIndex(line);
			if (index < 0) {
				continue;
			}
			Box box = map.get(index);
			if (box == null) {
				box = new Box(line, true);
				map.put(box.getIndex(), box);
			} else {
				box.append(line);
			}
		}
		return map;
	}

	public static void main(String[] args) throws IOException {
		MergeBoxFile t = new MergeBoxFile();
		t.mergeBoxFile();
		System.out.println("over!!");
	}
}

/**
 * 表示 box文件中最后索引相同和一组数据
 * 
 * @author zhch
 *
 */
class Box {
	public String value;
	public List<String> lines;
	public int count;
	private int index;
	public boolean isReal;

	public Box(String content, boolean real) {
		value = "";
		lines = new ArrayList<>();
		count = 0;
		index = -1;
		isReal = real;
		append(content);
	}

	public void append(String content) {

		int index = getIndex(content);

		if (this.index == -1) {
			this.index = index;
		}

		lines.add(content);
		count = lines.size();
		value += content.charAt(0);
	}

	public static int getIndex(String content) {
		if (StringUtils.isBlank(content)) {
			return -1;
		}
		String[] parts = content.trim().split("\\s");
		if (parts == null || parts.length != 6) {
			return -1;
		}

		int index = Integer.parseInt(parts[5]);
		return index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		List<String> newLines = new ArrayList<>();
		for (String line : lines) {
			String[] strs = line.split("\\s");
			strs[strs.length - 1] = index + "";
			newLines.add(StringUtils.join(strs, ' '));
		}
		lines = newLines;
		this.index = index;
	}

	public String toString() {
		return value + " " + index + " " + lines;
	}
}
