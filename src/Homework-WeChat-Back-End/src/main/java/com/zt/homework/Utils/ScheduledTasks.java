package com.zt.homework.Utils;

import com.zt.homework.service.MailReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private MailReceiveService mailReceiveService;

    @Scheduled(cron = "0 */10 *  * * * ")
    public void queryAllMail(){
        LOGGER.info("邮件检索开始时间：" + DateUtil.Date2String(new Date()));
        mailReceiveService.queryAllMail();
    }

}
