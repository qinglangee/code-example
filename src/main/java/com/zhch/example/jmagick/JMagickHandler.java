package com.zhch.example.jmagick;

import java.io.File;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.GifTransformer;

/**
 * 使用JMagick进行图像处理
 */
class JMagickHandler {

	private final static int DEFAULT_QUALITY = 90;

	protected JMagickHandler() {
	}

	private final static ThreadLocal<MyMagickImage> tLocal = new ThreadLocal<MyMagickImage>();
	private String lastSrc = null;

	/**
	 * 清除线程本地存储信息
	 */
	private synchronized MyMagickImage getMagickImage(String src) throws Exception {
		MyMagickImage mi = tLocal.get();
		if (mi != null && StringUtils.equals(src, lastSrc))
			return mi;
		else if (mi != null)
			tLocal.remove();
		this.lastSrc = src;
		ImageInfo info = new ImageInfo(src);
		mi = new MyMagickImage(info);
		tLocal.set(mi);
		return mi;
	}

	public void cleanup() {
		MyMagickImage mi = tLocal.get();
		if (mi != null)
			mi.destroyImages();
		tLocal.remove();
	}

	private final static boolean hasUnicode(String str) {
		return str.getBytes().length != str.length();
	}

	public void resize(String src, String dest, int width, int height, int quality) throws Exception {
		boolean u_src = hasUnicode(src);
		MyMagickImage image;
		File srctmp = null;
		if (u_src) {
			srctmp = File.createTempFile("jmagick_s_" + src.hashCode(), null);
			FileUtils.copyFile(new File(src), srctmp);
			image = this.getMagickImage(srctmp.getAbsolutePath());
		} else
			image = this.getMagickImage(src);

		image.getImageInfo().setQuality((quality > 0) ? quality : DEFAULT_QUALITY);

		MagickImage scaledimage = null;

		try {
			if (image.isAnimatedImage()) {
				GifImage gifImage = GifDecoder.decode(new File(src));
				GifImage newGif = GifTransformer.resize(gifImage, width, height, false);
				GifEncoder.encode(newGif, new File(dest));
			} else {// others
				scaledimage = image.scaleImage(width, height);
				scaledimage.setImageFormat("JPEG");
				scaledimage.profileImage("*", null);
				boolean u_dest = hasUnicode(dest);
				if (u_dest) {
					File tmp = File.createTempFile("jmagick_d_" + dest.hashCode(), null);
					scaledimage.setFileName(tmp.getAbsolutePath());
					scaledimage.writeImage(image.getImageInfo());
					FileUtils.copyFile(tmp, new File(dest));
					FileUtils.forceDelete(tmp);
				} else {
					scaledimage.setFileName(dest);
					scaledimage.writeImage(image.getImageInfo());
				}
			}
		} finally {
			if (srctmp != null)
				FileUtils.forceDelete(srctmp);
			if (scaledimage != null)
				scaledimage.destroyImages();
		}
	}

	public void rotate(String src, String dest, double degrees) throws Exception {
		boolean u_src = hasUnicode(src);
		MyMagickImage image;
		File srctmp = null;
		if (u_src) {
			srctmp = File.createTempFile("jmagick_s_" + src.hashCode(), null);
			FileUtils.copyFile(new File(src), srctmp);
			image = this.getMagickImage(srctmp.getAbsolutePath());
		} else
			image = this.getMagickImage(src);

		try {
			MagickImage rotateImg = image.rotateImage(degrees);
			rotateImg.profileImage("*", null);

			boolean u_dest = hasUnicode(dest);
			if (u_dest) {
				File tmp = File.createTempFile("jmagick_d_" + dest.hashCode(), null);
				rotateImg.setFileName(tmp.getAbsolutePath()); // convert to png
				rotateImg.writeImage(image.getImageInfo());
				rotateImg.destroyImages();
				FileUtils.copyFile(tmp, new File(dest));
				FileUtils.forceDelete(tmp);
			} else {
				rotateImg.setFileName(dest); // convert to png
				rotateImg.writeImage(image.getImageInfo());
				rotateImg.destroyImages();
			}
		} finally {
			if (srctmp != null)
				FileUtils.forceDelete(srctmp);
		}
	}

	private static class MyMagickImage extends MagickImage {
		private ImageInfo imageInfo;

		public ImageInfo getImageInfo() {
			return imageInfo;
		}

		public MyMagickImage(ImageInfo info) throws MagickException {
			super(info);
			this.imageInfo = info;
		}
	}

	public void cropImage(String src, int width, int height, int point, int point2, String dst) throws Exception {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception  {
		JMagickHandler handler = new JMagickHandler();
		String dir = "/home/lifeix/temp/d3/";
		String file = "heng9.png";
		MagickImage m = new MagickImage(new ImageInfo(dir + file));
		int width = m.getDimension().width;
		int height = m.getDimension().height;
		for (int i = 10; i < 100; i = i + 10) {
			handler.resize(dir + file, dir + i + "_" + file, width, height, i);
		}
	}

}