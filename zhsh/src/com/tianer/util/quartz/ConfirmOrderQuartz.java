package com.tianer.util.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.tianer.util.MD5;
public class ConfirmOrderQuartz{
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	
	public static void addJob(String date, String order_id)throws Exception {
		Scheduler scheduler = schedulerFactory.getScheduler();
		//可以通过SchedulerFactory创建一个Scheduler实例		
		//设置工作详情	
		JobDetail job = newJob(ConfirmOrderJob.class).withIdentity("task_"+order_id, "group_"+order_id).requestRecovery().build(); 	
		job.getJobDataMap().put("order_id", order_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = sdf.parse(date);
		//Date转String	//设置触发器	
		SimpleTrigger trigger = (SimpleTrigger)newTrigger().withIdentity("task_"+order_id, "group_"+order_id).startAt(startDate).build();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();	
	}
	
	public static void removeJob(String order_id) throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		JobKey jobKey = JobKey.jobKey("task_"+order_id, "group_"+order_id);
		TriggerKey triggerKey = TriggerKey.triggerKey("task_"+order_id, "group_"+order_id);
		sched.pauseTrigger(triggerKey);// 停止触发器  
        sched.unscheduleJob(triggerKey);// 移除触发器  
        sched.deleteJob(jobKey);// 删除任务  
	}
	
	public static void main(String[] args) throws Exception {
		
		String s = "12345678910";
		String ss = MD5.md5(MD5.md5(s));
		System.out.println(ss+":"+ss.length());
		
}

}