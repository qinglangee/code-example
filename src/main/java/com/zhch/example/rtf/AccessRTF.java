package com.zhch.example.rtf;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.io.FileUtils;

public class AccessRTF {
	String text;
	DefaultStyledDocument dsd;
	RTFEditorKit rtf;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub  

		AccessRTF readRTF = new AccessRTF();
		//		readRTF.readRtf(new File("d:\\temp\\d3\\luyang.rtf"));
		//		readRTF.writeRtf(new File("d:\\temp\\d3\\luyang2.rtf"));

		//		readRTF.readContent();
		readRTF.readContent();

		System.out.println("over!!");
	}

	public void readContent() {
		File file = new File("d:\\temp\\d3\\luyang.rtf");
		try {
			byte[] rtfBytes = FileUtils.readFileToByteArray(file);
			RTFEditorKit rtfParser = new RTFEditorKit();
			Document document = rtfParser.createDefaultDocument();
			rtfParser.read(new ByteArrayInputStream(rtfBytes), document, 0);
			String text = document.getText(0, document.getLength());
			System.out.println(text);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readRtf(File in) {  
        rtf=new RTFEditorKit();  
        dsd=new DefaultStyledDocument();  
        try {  
            rtf.read(new FileInputStream(in), dsd, 0);  
			System.out.println("length:" + dsd.getLength());
			text = dsd.getText(0, dsd.getLength());
            System.out.println(text);  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (BadLocationException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    }

	public void writeRtf(File out) {
		try {
			rtf.write(new FileOutputStream(out), dsd, 0, dsd.getLength());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
	}
}
