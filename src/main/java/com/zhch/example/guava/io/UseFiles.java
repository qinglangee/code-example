package com.zhch.example.guava.io;

import java.io.File;
import java.io.IOException;

import com.google.common.collect.TreeTraverser;
import com.google.common.io.Files;

/**
 * Files utility methods demo <br>
 * copy from http://ifeve.com/google-guava-io/
 * 
 * @author zhch
 *
 */
public class UseFiles {

	public void testUtilMethod() throws IOException {
		
		// 必要时为文件创建父目录
		Files.createParentDirs(new File("d:\\temp")) ;	
		// 返回给定路径所表示文件的扩展名
		String ext = Files.getFileExtension("d:\\temp\\aa.txt");
		System.out.println("ext : " + ext);
				
		// 返回去除了扩展名的文件名
		String filename = Files.getNameWithoutExtension("d:\\temp\\aa.txt");
		System.out.println("filename:" + filename);
		// 规范文件路径，并不总是与文件系统一致，请仔细测试
		String path = Files.simplifyPath("d:\\temp\\aa.txt");
		System.out.println("path : " + path);

		// 返回TreeTraverser用于遍历文件树
		TreeTraverser<File> fileTreeTraverser = Files.fileTreeTraverser();
	}

	public static void main(String[] args) throws IOException {
		UseFiles t = new UseFiles();
		t.testUtilMethod();
	}

}
