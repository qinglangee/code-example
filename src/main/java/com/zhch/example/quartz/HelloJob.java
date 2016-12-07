package com.zhch.example.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job{
	Logger LOG = LoggerFactory.getLogger(HelloJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOG.info("Hello World!!");
	}

}
