package com.zhch.example.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class MyApp1 {
	final static Logger logger = LoggerFactory.getLogger(MyApp1.class);

	public static void main(String[] args) {
		// assume SLF4J is bound to logback in the current environment
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		// print logback's internal status
		StatusPrinter.print(lc);

		logger.info("Entering application.");

		Foo foo = new Foo();
		for (int i = 0; i < 10; i++) {
			foo.doIt();
			sleep(3000);
		}
		logger.info("Exiting application.");
	}

	static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
