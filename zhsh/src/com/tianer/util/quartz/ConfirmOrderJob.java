package com.tianer.util.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.tianer.util.DateUtil;
import com.tianer.util.PageData;

public class ConfirmOrderJob implements Job {
	public ConfirmOrderJob() {
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		TriggerKey triggerKey = context.getTrigger().getKey();
		JobKey jobKey = context.getJobDetail().getKey();
		try { 
			PageData pd = new PageData();
			pd.put("status", "4");
			pd.put("ordertime", DateUtil.getTime());
			pd.put("confirmtype", "2");
			pd.put("order_id", data.getString("order_id"));
		} catch (Exception e) {
		}finally{
			try {
				removeJob(jobKey,triggerKey);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void removeJob(JobKey jobKey, TriggerKey tiKey) throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		sched.pauseTrigger(tiKey);
		// 停止触发器
		sched.unscheduleJob(tiKey);
		// 删除任务
		sched.deleteJob(jobKey);
	}
	
}