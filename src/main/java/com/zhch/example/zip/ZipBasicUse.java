package com.zhch.example.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Example from http://www.open-open.com/bbs/view/1320568080343 压缩文件的例子
 * 
 * @author zhch
 *
 */
public class ZipBasicUse {

	/**
	 * 压缩单个 文件
	 * 
	 * @throws IOException
	 */
	public void zipFile() throws IOException {
		//		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:\\temp\\d3\\testzip.zip"),
		//				Charset.forName("GBK"));
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:\\temp\\d3\\testzip.zip"),
				Charset.forName("utf-8"));
		//实例化一个名称为ab.txt的ZipEntry对象
		ZipEntry entry = new ZipEntry("ab.txt");
		//设置注释
		zos.setComment("zip测试for单个文件");
		//把生成的ZipEntry对象加入到压缩文件中，而之后往压缩文件中写入的内容都会放在这个ZipEntry对象里面
		zos.putNextEntry(entry);
		InputStream is = new FileInputStream("D:\\temp\\d3\\ab.txt");
		int len = 0;
		while ((len = is.read()) != -1)
			zos.write(len);
		is.close();
		zos.close();
	}

	/**
	 * 压缩目录
	 * 
	 * @throws IOException
	 */
	public void zipDir() throws IOException {
		File inFile = new File("D:\\temp\\d3\\test");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("D:\\temp\\d3\\testdir.zip"),
				Charset.forName("GBK"));
		zos.setComment("多文件处理");
		zipFile(inFile, zos, "");
		zos.close();
	}

	private void zipFile(File inFile, ZipOutputStream zos, String dir) throws IOException {
		if (inFile.isDirectory()) {
			File[] files = inFile.listFiles();
			for (File file : files)
				zipFile(file, zos, dir + "\\" + inFile.getName());
		} else {
			String entryName = null;
			if (!"".equals(dir))
				entryName = dir + "\\" + inFile.getName();
			else
				entryName = inFile.getName();
			ZipEntry entry = new ZipEntry(entryName);
			zos.putNextEntry(entry);
			InputStream is = new FileInputStream(inFile);
			int len = 0;
			while ((len = is.read()) != -1)
				zos.write(len);
			is.close();
		}

	}

	/**
	 * 解压文件
	 * 
	 * @throws IOException
	 */
	public void unzip() throws IOException {

		File file = new File("D:\\temp\\d3\\testdir.zip");//压缩文件
		ZipFile zipFile = new ZipFile(file);//实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
		//实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file), Charset.forName("GBK"));
		ZipEntry zipEntry = null;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			File temp = new File("D:\\temp\\d3\\unzip\\" + fileName);
			if (!temp.getParentFile().exists())
				temp.getParentFile().mkdirs();
			OutputStream os = new FileOutputStream(temp);
			//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
			InputStream is = zipFile.getInputStream(zipEntry);
			int len = 0;
			if (is == null) {
				os.close();
				continue;
			} else {
				while ((len = is.read()) != -1)
					os.write(len);
				os.close();
				is.close();
			}
		}
		zipInputStream.close();

	}

	public static void main(String args[]) throws IOException {
		ZipBasicUse t = new ZipBasicUse();
		// 测试压缩
		//		t.zipFile();
		//		t.zipDir();
		// 测试解压
		t.unzip();
		System.out.println("over!!");
	}

}
