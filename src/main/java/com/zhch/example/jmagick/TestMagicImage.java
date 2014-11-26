package com.zhch.example.jmagick;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;


public class TestMagicImage {

	String path;
	ImageInfo info;
	MagickImage image;
	
	public void init() throws MagickException{
		
		path = "/home/lifeix/temp/d3/";//heng.png";
		info = new ImageInfo(path + "heng9.png");
		image = new MagickImage(info);
	}
	
	public void rotate() throws MagickException {
		// 将图片旋转90 180 270 360度
		for(double d = 90;d<101;d = d + 90){
			MagickImage image90 = image.rotateImage(d);
			image90.setFileName(path + "heng"+d+".png");
			image90.writeImage(new ImageInfo()); // 这里的ImageInfo传个对象就可以了, 没什么用
		}
	}
	
	public void destory() throws MagickException{
		image.setFileName(path + "new01.png");
		image.writeImage(new ImageInfo());
		MagickImage image90 = image.rotateImage(90D);
		MagickImage imageSmall = image.scaleImage(50, 50);
		image.destroyImages();
		
		// image.destroyImages() 方法调用以后, 对image90没有影响
		image90.setFileName(path + "new90.png");
		image90.writeImage(new ImageInfo());
		image90.destroyImages();
		
		imageSmall.setFileName(path + "newSmall.png");
		imageSmall.writeImage(new ImageInfo());
		// 每一个 MagickImage 用完后都要调用destroyImages()方法
		imageSmall.destroyImages();
		
		// image.destroyImages() 方法调用以后, 就不能再使用image对象, 下面代码报错
//		image.setFileName(path + "new02.png");
//		image.writeImage(new ImageInfo());
	}
	
	public void quality() throws MagickException{
		for(int i=100;i>0;i=i-10){
			image.setFileName(path + "quality"+i+".png");
			ImageInfo info = new ImageInfo();
			info.setQuality(i); // setQuality() 是不起作用的
			image.writeImage(info);
		}
	}
	
	public void scale() throws MagickException{
		MagickImage image = new MagickImage(new ImageInfo("/tmp/heng9.png"));
		image.setFileName("/tmp/heng9_out.png");
		image.writeImage(new ImageInfo());
		System.out.println(System.getProperty("java.library.path"));
		String[] s = System.getProperty("java.library.path").split(":");
		for(String a:s){
			System.out.println(a);
		}
	}

	public static void main(String[] args) throws MagickException {
		TestMagicImage t = new TestMagicImage();
//		t.init();
		
		t.scale();
		System.out.println("over!!");
	}
}
