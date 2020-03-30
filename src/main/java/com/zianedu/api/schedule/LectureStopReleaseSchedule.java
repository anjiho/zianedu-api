package com.zianedu.api.schedule;

import com.zianedu.api.config.ConfigHolder;
import com.zianedu.api.service.ScheduleService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 강좌 일시정지 해제 스케쥴링(해제 조건은 본사 유지보수 인력 협의)
 */
public class LectureStopReleaseSchedule extends QuartzJobBean {

    private ApplicationContext context;

    @Override
    protected void executeInternal(JobExecutionContext ex) throws JobExecutionException {
        context = (ApplicationContext) ex.getJobDetail().getJobDataMap().get("applicationContext");
        try {
            executeJob(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeJob(JobExecutionContext ex) throws Exception {
        boolean isSchedule = ConfigHolder.isSchedule();
        if (isSchedule) {
            ScheduleService scheduleService = (ScheduleService)context.getBean("scheduleService");
            scheduleService.stopReleaseAtStopTermDeadLine();
        }
    }
}
