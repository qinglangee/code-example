package com.zhch.example.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class CommonsCompress {

	public static void unzip(String filePath, String destPath) throws ArchiveException, IOException {
		final InputStream is = new BufferedInputStream(new FileInputStream(filePath));
		ArchiveStreamFactory archiveStreamFactory = new ArchiveStreamFactory("gbk");
		ArchiveInputStream in = archiveStreamFactory.createArchiveInputStream("zip", is);
		ZipArchiveInputStream zip;
		ZipArchiveEntry entry = (ZipArchiveEntry) in.getNextEntry();
		while (entry != null) {
			String fileName = entry.getName();
			File temp = new File(destPath, fileName);
			if (entry.isDirectory()) {
				temp.mkdirs();
				entry = (ZipArchiveEntry) in.getNextEntry();
				continue;
			}

			OutputStream out = new FileOutputStream(new File(destPath, entry.getName()));
			IOUtils.copy(in, out);
			out.close();
			entry = (ZipArchiveEntry) in.getNextEntry();
		}
		in.close();
	}

}
