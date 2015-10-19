package com.zhch.example.zip;

import java.io.File;

import com.github.junrar.extract.ExtractArchive;

public class RarBasicUse {

	public void test() {
		final File rar = new File("d:\\temp\\d3\\resumedata.rar");
		final File destinationFolder = new File("d:\\temp\\d3\\rar_extract");
		ExtractArchive extractArchive = new ExtractArchive();
		extractArchive.extractArchive(rar, destinationFolder);
	}

	public static void main(String[] args) {
		RarBasicUse t = new RarBasicUse();
		t.test();
		System.out.println("over!!");
	}
}
