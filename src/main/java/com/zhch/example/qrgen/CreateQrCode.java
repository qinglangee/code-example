package com.zhch.example.qrgen;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class CreateQrCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void createQrCode() {
		ByteArrayOutputStream out = QRCode.from("Hello World").to(ImageType.PNG).stream();

		try {
			FileOutputStream fout = new FileOutputStream(new File("C:QR_Code.JPG"));

			fout.write(out.toByteArray());

			fout.flush();
			fout.close();

		} catch (FileNotFoundException e) {
			// Do Logging
		} catch (IOException e) {
			// Do Logging
		}
	}

	/**
	 * servlet 中生成 qrcode
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 在有servlet的环境下运行

		String qrtext = request.getParameter("qrtext");

		ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).stream();

		response.setContentType("image/png");
		response.setContentLength(out.size());
		OutputStream outStream = response.getOutputStream();

		outStream.write(out.toByteArray());

		outStream.flush();
		outStream.close();
	}

	public static void main(String[] args) {
		CreateQrCode t = new CreateQrCode();
		t.createQrCode();
	}
}
