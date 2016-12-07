package com.zhch.example.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import  org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 从配置文件中组建任务( classpath 中的 quartz.properties )
 * 
 * @author zhch
 *
 */
public class ExampleReadFromConfig {
	public static void main(String[] args) {

		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();

			// job 触发器， 触发 job 执行， 每隔 1 秒执行一次
			Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow()
					.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(18, 29)).build();

			// 告诉 quartz 利用 trigger 来安排 job
			scheduler.scheduleJob(job, trigger);
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//			scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}
