package com.spring.admin.job;


import com.spring.admin.modules.sys.core.service.FollowUpRecordService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MarkPendingVisitRecordsJob implements Job {
    @Resource
    private FollowUpRecordService followUpRecordService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            log.info("执行MarkPendingVisitRecordsJob任务");
            followUpRecordService.markPendingVisitRecords();
            log.info("MarkPendingVisitRecordsJob任务执行完毕");
        } catch (Exception e) {
            log.error("执行MarkPendingVisitRecordsJob任务时出错", e);
            throw new JobExecutionException(e);
        }
    }
}
