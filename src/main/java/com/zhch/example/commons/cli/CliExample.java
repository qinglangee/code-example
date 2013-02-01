package com.zhch.example.commons.cli;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CliExample {
	public void dateApp(String[] args) throws ParseException {
		System.out.println("DateApp====================================");
		// create Options object
		Options options = new Options();

		// 选项的名称|是否需要参数|显示的说明信息
		options.addOption("t", false, "display current time");

		// 这个parser一般用来解析只有一个字母的选项, 像 -t, -n
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		// hasOption判断是否有这个参数
		if (cmd.hasOption("t")) {
			// 打印日期和时间
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			System.out.println(format.format(new Date()));
		} else {
			// 只打印日期
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(format.format(new Date()));
		}

	}

	public static void main(String[] args) throws ParseException {
		CliExample t = new CliExample();
		t.dateApp(args);
	}
}
