package com.hzu.homework.app.test;

import java.util.Map;

import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzu.homework.sysback.homework.vo.HomeworkModel;
import com.hzu.homework.sysback.user.service.UserService;

@Controller  
@RequestMapping("/test")  
public class TestController {  

    @Autowired  
    private TaskExecutor executor;  

    @Autowired  
    UserService testService;  

    @ResponseBody  
    @RequestMapping("/order")  
    public Map<String, Object> test(HttpServletRequest request){  

        //线程池+同步块  
        executor.execute(new Runnable() {  
            @Override  
            public void run() {  
               // synchronized (testService) {  
                  //  testService.insert(order);  
               // }  
            }  
        });  
        return null;  
    }
    
    public String UpdateHomework(final Message[] messages,String uuid) {
    	final HomeworkModel homework = new HomeworkModel(); 	
    	 //线程池+同步块  
        executor.execute(new Runnable() {  
            @Override  
            public void run() {  
                //synchronized (testService) {  
            	queryEmail(messages,homework);
               // }  
            }  
        });  
        return null;  
    }
    
    
    
    public  String queryEmail(Message[] messages,HomeworkModel homweork) {
    	return "";
    }
}
