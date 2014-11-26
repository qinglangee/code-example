package com.zhch.example.im4java;

import java.io.File;
import java.io.IOException;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import org.gm4java.engine.GMException;
import org.gm4java.engine.GMService;
import org.gm4java.engine.GMServiceException;
import org.gm4java.engine.support.GMConnectionPoolConfig;
import org.gm4java.engine.support.PooledGMService;
import org.gm4java.im4java.GMBatchCommand;
import org.im4java.core.GraphicsMagickCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

/**
 * ImageMagick vs GraphicsMagick
 * 
 * @author lifeix
 * 
 */
public class ImVsGm {
	String picdir = "/home/lifeix/temp/imagetest/pic/";
	String outdir = "/home/lifeix/temp/imagetest/";

	public void test() throws MagickException, IOException, InterruptedException, IM4JavaException, GMException, GMServiceException {
		File dir = new File(picdir);
		File[] files = dir.listFiles();
		String[] filenames = new String[100];
		int i = 0;
		for (File file : files) {
			if (file.isFile()) {
				filenames[i] = file.getName();
				i++;
			}
			if (i >= filenames.length) {
				break;
			}
		}

		long t1 = System.currentTimeMillis();

		for (String filename : filenames) {
			MagickImage mi = new MagickImage(new ImageInfo(picdir + filename));
			MagickImage scale = mi.scaleImage(200, 200);
			scale.setFileName(outdir + "im_out/" + filename);
			scale.writeImage(new ImageInfo());
		}
		long t2 = System.currentTimeMillis();
		System.out.println("im time : " + (t2 - t1));
		resizeImages(filenames);
		resizeImagesWithGm4java(filenames);
	}

	public void resizeImages(String[] pImageNames) throws IOException, InterruptedException, IM4JavaException {
		long t1 = System.currentTimeMillis();
		// create command
		// ConvertCmd cmd = new ConvertCmd(true);
		GraphicsMagickCmd cmd = new GraphicsMagickCmd("convert");

		// create the operation, add images and operators/options
		IMOperation op = new IMOperation();
		op.addImage();
		op.resize(200, 200);
		op.addImage();

		for (String srcImage : pImageNames) {
			cmd.run(op, picdir + srcImage, outdir + "gm_out/" + srcImage);
		}

		long t2 = System.currentTimeMillis();
		System.out.println("gm time : " + (t2 - t1));
	}

	public void resizeImagesWithGm4java(String[] pImageNames) throws IOException, InterruptedException,
		IM4JavaException, GMException, GMServiceException {
		GMConnectionPoolConfig config = new GMConnectionPoolConfig();
		GMService service = new PooledGMService(config);
		// create command
		GMBatchCommand command = new GMBatchCommand(service, "convert");
		long t1 = System.currentTimeMillis();

		// create the operation, add images and operators/options
		IMOperation op = new IMOperation();
		op.addImage();
		op.resize(200, 200);
		op.addImage();

		// execute the operation
		for (String srcImage : pImageNames) {
//			command.run(op, picdir + srcImage, outdir + "gm_out/" + srcImage);
			service.execute("convert", "-size", "200x200", picdir + srcImage, "-resize", "200x200", "+profile", "*", outdir + "gm_out/" + srcImage);
		}
		

		long t2 = System.currentTimeMillis();
		System.out.println("gm time : " + (t2 - t1));

	}

	public static void main(String[] args) throws MagickException, IOException, InterruptedException, IM4JavaException, GMException, GMServiceException {
		ImVsGm t = new ImVsGm();
		t.test();

		System.out.println("over!!");
	}
}
