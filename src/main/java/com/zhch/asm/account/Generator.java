package com.zhch.asm.account;

import java.io.File;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class Generator{ 
	 public static void main(String[] args) throws Exception { 
		 ClassReader cr = new ClassReader("com.zhch.asm.account.Account"); 
		 ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS); 
		 ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw); 
		 cr.accept(classAdapter, ClassReader.SKIP_DEBUG); 
		 byte[] data = cw.toByteArray(); 
		 File file = new File("target/classes/com/zhch/asm/account/Account.class"); 
		 FileOutputStream fout = new FileOutputStream(file); 
		 fout.write(data); 
		 fout.close(); 
		 System.out.println("over!!");
	 } 
} 
