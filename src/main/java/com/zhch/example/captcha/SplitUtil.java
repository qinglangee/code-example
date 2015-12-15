package com.zhch.example.captcha;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SplitUtil {
	/** 边框距离，可以直接清除的部分 */
	private int border;
	/** 验证码切分的块数 */
	private int partCount;

	public SplitUtil(int border, int partCount) {
		this.border = border;
		this.partCount = partCount;
	}

	/** 清除边框 */
	public BufferedImage removeBorder(BufferedImage src) {
		int w = src.getWidth();
		int h = src.getHeight();
		int imgW = w - 2 * border;
		int imgH = h - 2 * border;
		BufferedImage img = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < imgW; x++) {
			for (int y = 0; y < imgH; y++) {
				img.setRGB(x, y, src.getRGB(x + border, y + border));
			}
		}
		return img;
	}

	/** 清除四周空白 */
	public BufferedImage removeBlank(BufferedImage src) {
		int w = src.getWidth();
		int h = src.getHeight();

		int xStart = findXStart(src, w, h);
		int xEnd = findXEnd(src, w, h);
		int yStart = findYStart(src, w, h);
		int yEnd = findYEnd(src, w, h);

		System.out.println(
				"removeBlank xs:" + xStart + " xe:" + xEnd + " ys:" + yStart + " ye:" + yEnd + " w:" + w + " h:" + h);

		int imgW = xEnd - xStart + 1;
		int imgH = yEnd - yStart + 1;

		BufferedImage img = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < imgW; x++) {
			for (int y = 0; y < imgH; y++) {
				try {
					img.setRGB(x, y, src.getRGB(x + xStart, y + yStart));
				} catch (Exception e) {
					System.out
							.println("getRGB error w:" + w + " h:" + h + " x:" + (x + xStart) + " y:" + (y + yStart));
				}
			}
		}
		return img;
	}

	/** 返回第一个不是白的像素x坐标 */
	private int findXStart(BufferedImage src, int w, int h) {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (isBlank(src, x, y)) {
					return x;
				}
			}
		}
		return w;
	}

	private int findXEnd(BufferedImage src, int w, int h) {
		for (int x = w - 1; x >= 0; x--) {
			for (int y = 0; y < h; y++) {
				if (isBlank(src, x, y)) {
					return x;
				}
			}
		}
		return w;
	}

	private int findYStart(BufferedImage src, int w, int h) {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (isBlank(src, x, y)) {
					return y;
				}
			}
		}
		return w;
	}

	private int findYEnd(BufferedImage src, int w, int h) {
		for (int y = h - 1; y >= 0; y--) {
			for (int x = 0; x < w; x++) {
				if (isBlank(src, x, y)) {
					return y;
				}
			}
		}
		return w;
	}

	private boolean isBlank(BufferedImage src, int x, int y) {
		Color color = new Color(src.getRGB(x, y));
		return color.getRed() + color.getGreen() + color.getBlue() <= 100;
	}

	/** 把验证码分成各个分开 个字条 */
	public BufferedImage[] splitImage(BufferedImage src) {
		if (border > 0) {
			src = removeBorder(src);
		}
		src = removeBlank(src);

		BufferedImage[] parts = new BufferedImage[partCount];

		int w = src.getWidth();
		int h = src.getHeight();

		int x2 = w / 2;
		int x1 = x2 / 2;
		int x3 = (x2 + w) / 2;

		int[] xStarts = new int[] { 0, x1, x2, x3, w - 1 };
		for (int i = 0; i < partCount; i++) {
			BufferedImage img = getImagePart(src, xStarts[i], xStarts[i + 1], h);
			parts[i] = img;
		}
		return parts;
	}

	/** 取图片的一部分 */
	private BufferedImage getImagePart(BufferedImage src, int xStart, int xEnd, int h) {
		int imgW = xEnd - xStart + 1;
		int imgH = h;
		BufferedImage img = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < imgW; x++) {
			for (int y = 0; y < imgH; y++) {
				try {
					img.setRGB(x, y, src.getRGB(x + xStart, y));
				} catch (Exception e) {
					System.out.println("getRGB error w : " + src.getWidth() + " h:" + src.getHeight());
					System.out.println("x:" + (x + xStart) + " y:" + y + " " + e.getMessage());
				}
			}
		}
		return img;
	}

}
