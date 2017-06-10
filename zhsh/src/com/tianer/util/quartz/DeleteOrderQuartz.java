package com.tianer.util.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.tianer.util.DateUtil;

public class DeleteOrderQuartz {
	public static void addJob() {
		JobDetail jobDetail = newJob(DeleteOrderJob.class).withIdentity(
				"AutoDeleteOrder", "AutoDeleteOrder").requestRecovery().build();
		// 每天0点执行
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(
				"AutoDeleteOrder", "AutoDeleteOrder").withSchedule(
				cronSchedule("0 0 0 * * ?")).build();
		SchedulerFactory sFactory = new StdSchedulerFactory();
		Scheduler scheduler;
		try {
			scheduler = sFactory.getScheduler();
			scheduler.scheduleJob(jobDetail, cronTrigger);
			scheduler.start();
			System.out.println("自动删除订单任务启动---------------" + DateUtil.getTime());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}